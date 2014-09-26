package net.groster.moex.forts.drunkypenguin.core.fast.feed.instrument.features;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Named;
import javax.inject.Singleton;
import net.groster.moex.forts.drunkypenguin.core.fast.ConnectionThread;
import net.groster.moex.forts.drunkypenguin.core.fast.config.xml.MarketID;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.SecurityPK;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.msg.SecurityDefinition;
import net.groster.moex.forts.drunkypenguin.core.fast.feed.instrument.AbstractInstrumentFastFeed;
import org.openfast.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//<MarketDataGroup feedType="RTS-INSTR" marketID="F" label="All Futures defintion">
@Named
@Singleton
public class InstrumentFuturesFastFeed extends AbstractInstrumentFastFeed {

    private static final Logger LOGGER = LoggerFactory.getLogger(InstrumentFuturesReplayMessageHandler.class);

    private final Map<SecurityPK, SecurityDefinition> symbol2SecurityDefinitionMap = new HashMap<>();
    private boolean needInitialSnapshot = true;

    @Override
    public MarketID getMarketID() {
        return MarketID.FUTURES;
    }

    @Override
    public MessageHandler createInstrumentIncrementalMessageHandler() {
        return new InstrumentFuturesIncrementalMessageHandler();
    }

    @Override
    public MessageHandler createInstrumentReplayMessageHandler() {
        return getBeanFactory().getBean(InstrumentFuturesReplayMessageHandler.class);
    }

    //TODO:move to abstract parent?
    void onNewReplaySecurityDefinition(final SecurityDefinition sd) {
        synchronized (symbol2SecurityDefinitionMap) {
            if (needInitialSnapshot) {
                symbol2SecurityDefinitionMap.put(sd.getSecurityPK(), sd);
                if (sd.getTotNumReports() == symbol2SecurityDefinitionMap.size()) {
                    LOGGER.info("GOT IT! FUTURES DEFINITIONS SET");
                    //TODO: show it via REST
                    needInitialSnapshot = false;
                    for (final ConnectionThread connectionThread : getReplayConnectionThreads()) {
                        connectionThread.stopConnection();
                    }
                } else if (sd.getTotNumReports() < symbol2SecurityDefinitionMap.size()) {
                    symbol2SecurityDefinitionMap.clear();
                }
            }
        }
    }
}
