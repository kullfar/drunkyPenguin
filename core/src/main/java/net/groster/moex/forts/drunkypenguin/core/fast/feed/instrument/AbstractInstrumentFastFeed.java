package net.groster.moex.forts.drunkypenguin.core.fast.feed.instrument;

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

public abstract class AbstractInstrumentFastFeed {

    @Inject
    private FASTService fastService;
    @Inject
    private BeanFactory beanFactory;
    private final Set<ConnectionThread> incConnectionThreads = new HashSet<>();
    private final Set<ConnectionThread> replayConnectionThreads = new HashSet<>();

    public void start() {
        final MarketDataGroup marketDataGroup = fastService.getMarketDataGroup(FeedType.RTS_INSTR, getMarketID());
        for (final Connection connection : marketDataGroup.getConnections()) {
            final String feedConnectionName = FeedType.RTS_INSTR.name() + ':' + getMarketID() + '>' + connection.getType() + ':'
                    + connection.getFeed();
            final ConnectionThread connectionThread = beanFactory.getBean(ConnectionThread.class);
            switch (connection.getType()) {
                case INSTRUMENT_INCREMENTAL:
                    connectionThread.init(feedConnectionName, new MulticastClientEndpoint(connection.getPort(), connection.getIp()),
                            createInstrumentIncrementalMessageHandler());
                    connectionThread.start();
                    incConnectionThreads.add(connectionThread);
                    break;
                case INSTRUMENT_REPLAY:
                    connectionThread.init(feedConnectionName, new MulticastClientEndpoint(connection.getPort(), connection.getIp()),
                            createInstrumentReplayMessageHandler());
                    replayConnectionThreads.add(connectionThread);
                    break;
            }
        }
        for (final ConnectionThread connectionThread : replayConnectionThreads) {
            connectionThread.start();
        }
    }

    public abstract MarketID getMarketID();

    public abstract MessageHandler createInstrumentIncrementalMessageHandler();

    public abstract MessageHandler createInstrumentReplayMessageHandler();

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    @PreDestroy
    public void preDestroy() {
        for (final ConnectionThread connectionThread : replayConnectionThreads) {
            connectionThread.stopConnection();
        }
        for (final ConnectionThread connectionThread : incConnectionThreads) {
            connectionThread.stopConnection();
        }
    }

    public Set<ConnectionThread> getReplayConnectionThreads() {
        return replayConnectionThreads;
    }

}
