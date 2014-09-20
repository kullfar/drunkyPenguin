package net.groster.moex.forts.drunkypenguin.core.fast.feed.instrument.features;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import net.groster.moex.forts.drunkypenguin.core.fast.ConnectionThread;
import net.groster.moex.forts.drunkypenguin.core.fast.FASTService;
import net.groster.moex.forts.drunkypenguin.core.fast.config.xml.Connection;
import net.groster.moex.forts.drunkypenguin.core.fast.config.xml.FeedType;
import net.groster.moex.forts.drunkypenguin.core.fast.config.xml.MarketDataGroup;
import net.groster.moex.forts.drunkypenguin.core.fast.config.xml.MarketID;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.SecurityDefinition;
import org.openfast.session.multicast.MulticastClientEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;

//<MarketDataGroup feedType="RTS-INSTR" marketID="F" label="All Futures defintion">
@Named
@Singleton
public class InstrumentFuturesFastFeed {

    private static final Logger LOGGER = LoggerFactory.getLogger(InstrumentReplayMessageHandler.class);

    @Inject
    private FASTService fastService;
    @Inject
    private BeanFactory beanFactory;
    private final Set<ConnectionThread> incConnectionThreads = new HashSet<>();
    private final Set<ConnectionThread> replayConnectionThreads = new HashSet<>();

    private final Map<String, SecurityDefinition> symbol2SecurityDefinitionMap = new HashMap<>();
    private boolean needInitialSnapshot = true;

    public void start() {
        final MarketDataGroup marketDataGroup = fastService.getMarketDataGroup(FeedType.RTS_INSTR, MarketID.FUTURES);
        for (final Connection connection : marketDataGroup.getConnections()) {
            final String feedConnectionName = FeedType.RTS_INSTR.name() + ':' + MarketID.FUTURES.name() + '>' + connection.getType() + ':'
                    + connection.getFeed();
            final ConnectionThread connectionThread = beanFactory.getBean(ConnectionThread.class);
            switch (connection.getType()) {
                case INSTRUMENT_INCREMENTAL:
                    connectionThread.init(feedConnectionName, new MulticastClientEndpoint(connection.getPort(), connection.getIp()),
                            new InstrumentIncrementalMessageHandler());
                    connectionThread.start();
                    incConnectionThreads.add(connectionThread);
                    break;
                case INSTRUMENT_REPLAY:
                    connectionThread.init(feedConnectionName, new MulticastClientEndpoint(connection.getPort(), connection.getIp()), beanFactory.
                            getBean(InstrumentReplayMessageHandler.class));
                    replayConnectionThreads.add(connectionThread);
                    break;
            }
        }
        for (final ConnectionThread connectionThread : replayConnectionThreads) {
            connectionThread.start();
        }
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

    void onNewReplaySecurityDefinition(final SecurityDefinition sd) {
        synchronized (symbol2SecurityDefinitionMap) {
            if (needInitialSnapshot) {
                symbol2SecurityDefinitionMap.put(sd.getSymbol(), sd);
                if (sd.getTotNumReports() == symbol2SecurityDefinitionMap.size()) {
                    LOGGER.info("GOT IT! FIRST DEFINITIONS SET");
                    needInitialSnapshot = false;
                    for (final ConnectionThread connectionThread : replayConnectionThreads) {
                        connectionThread.stopConnection();
                    }
                } else if (sd.getTotNumReports() < symbol2SecurityDefinitionMap.size()) {
                    symbol2SecurityDefinitionMap.clear();
                }
            }
        }
    }
}
