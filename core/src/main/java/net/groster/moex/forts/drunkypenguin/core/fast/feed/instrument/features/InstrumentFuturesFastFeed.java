package net.groster.moex.forts.drunkypenguin.core.fast.feed.instrument.features;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import net.groster.moex.forts.drunkypenguin.core.fast.ConnectionThread;
import net.groster.moex.forts.drunkypenguin.core.fast.FastService;
import net.groster.moex.forts.drunkypenguin.core.fast.config.xml.Connection;
import net.groster.moex.forts.drunkypenguin.core.fast.config.xml.FeedType;
import net.groster.moex.forts.drunkypenguin.core.fast.config.xml.MarketDataGroup;
import net.groster.moex.forts.drunkypenguin.core.fast.config.xml.MarketID;
import org.openfast.MessageHandler;
import org.openfast.session.multicast.MulticastClientEndpoint;
import org.springframework.beans.factory.BeanFactory;

//<MarketDataGroup feedType="RTS-INSTR" marketID="F" label="All Futures/Stocks defintion">
@Named
@Singleton
public class InstrumentFuturesFastFeed {

    @Inject
    private FastService fastService;
    @Inject
    private BeanFactory beanFactory;
    private final Set<ConnectionThread> connectionThreads = new HashSet<>();

    public void start() {
        final MarketDataGroup marketDataGroup = fastService.getMarketDataGroup(FeedType.RTS_INSTR, MarketID.FUTURES);
        for (final Connection connection : marketDataGroup.getConnections()) {
            final String feedConnectionName = FeedType.RTS_INSTR.name() + ':' + MarketID.FUTURES.name() + '>' + connection.getType() + ':'
                    + connection.getFeed();
            final MessageHandler messageHandler;
            switch (connection.getType()) {
                case INSTRUMENT_INCREMENTAL:
                    messageHandler = new InstrumentIncrementalMessageHandler();
                    break;
                case INSTRUMENT_REPLAY:
                    messageHandler = new InstrumentReplayMessageHandler();
                    break;
                default:
                    continue;
            }
            final ConnectionThread connectionThread = beanFactory.getBean(ConnectionThread.class);
            connectionThread.init(feedConnectionName, new MulticastClientEndpoint(connection.getPort(), connection.getIp()), messageHandler);
            connectionThread.start();
            connectionThreads.add(connectionThread);
        }
    }

    @PreDestroy
    public void preDestroy() {
        for (final ConnectionThread connectionThread : connectionThreads) {
            connectionThread.stopConnection();
        }
    }
}
