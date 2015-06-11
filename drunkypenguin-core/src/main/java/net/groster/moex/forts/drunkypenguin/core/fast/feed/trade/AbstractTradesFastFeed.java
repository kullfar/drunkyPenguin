package net.groster.moex.forts.drunkypenguin.core.fast.feed.trade;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import net.groster.moex.forts.drunkypenguin.core.fast.ConnectionThread;
import net.groster.moex.forts.drunkypenguin.core.fast.FASTService;
import net.groster.moex.forts.drunkypenguin.core.fast.config.xml.Connection;
import net.groster.moex.forts.drunkypenguin.core.fast.config.xml.FeedType;
import net.groster.moex.forts.drunkypenguin.core.fast.config.xml.MarketDataGroup;
import net.groster.moex.forts.drunkypenguin.core.fast.config.xml.MarketID;
import org.openfast.MessageHandler;
import org.openfast.session.multicast.MulticastClientEndpoint;
import org.springframework.beans.factory.BeanFactory;

public abstract class AbstractTradesFastFeed {

    @Inject
    private FASTService fastService;
    @Inject
    private BeanFactory beanFactory;

    private final Set<ConnectionThread> incConnectionThreads = new HashSet<>();

    public void start() {
        final MarketID marketID = getMarketID();
        final MarketDataGroup marketDataGroup = fastService.getMarketDataGroup(FeedType.RTS_TRADES, marketID);
        for (final Connection connection : marketDataGroup.getConnections()) {
            final String feedConnectionName = FeedType.RTS_TRADES.name() + ':' + marketID + '>' + connection.getType() + ':'
                    + connection.getFeed();
            final ConnectionThread connectionThread = beanFactory.getBean(ConnectionThread.class);
            switch (connection.getType()) {
                case INCREMENTAL:
                    connectionThread.init(feedConnectionName, new MulticastClientEndpoint(connection.getPort(), connection.getIp()),
                            createTradesIncrementalMessageHandler());
                    connectionThread.start();
                    incConnectionThreads.add(connectionThread);
                    break;
//TODO:we have two more channels here
//SNAPSHOT:A
//SNAPSHOT:B
//HISTORICAL_REPLAY:A

            }
        }
        //TODO:maybe start some channels (SNAPSHOTs) some time after
        /*        for (final ConnectionThread connectionThread : replayConnectionThreads) {
         connectionThread.start();
         }*/
    }

    public abstract MarketID getMarketID();

    public abstract MessageHandler createTradesIncrementalMessageHandler();

    @PreDestroy
    public void preDestroy() {

        for (final ConnectionThread connectionThread : incConnectionThreads) {
            connectionThread.stopConnection();
        }
    }
}
