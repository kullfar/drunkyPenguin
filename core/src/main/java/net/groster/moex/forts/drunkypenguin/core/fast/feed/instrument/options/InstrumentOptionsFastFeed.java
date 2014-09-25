package net.groster.moex.forts.drunkypenguin.core.fast.feed.instrument.options;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Named;
import javax.inject.Singleton;
import net.groster.moex.forts.drunkypenguin.core.fast.ConnectionThread;
import net.groster.moex.forts.drunkypenguin.core.fast.config.xml.MarketID;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.msg.SecurityDefinition;
import net.groster.moex.forts.drunkypenguin.core.fast.feed.instrument.AbstractInstrumentFastFeed;
import org.openfast.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//<MarketDataGroup feedType="RTS-INSTR" marketID="O" label="All Options defintion">
@Named
@Singleton
public class InstrumentOptionsFastFeed extends AbstractInstrumentFastFeed {

    private static final Logger LOGGER = LoggerFactory.getLogger(InstrumentOptionsFastFeed.class);

    private final Map<String, SecurityDefinition> symbol2SecurityDefinitionMap = new HashMap<>();
    private boolean needInitialSnapshot = true;

    @Override
    public MarketID getMarketID() {
        return MarketID.OPTIONS;
    }

    @Override
    public MessageHandler createInstrumentIncrementalMessageHandler() {
        return new InstrumentOptionsIncrementalMessageHandler();
    }

    @Override
    public MessageHandler createInstrumentReplayMessageHandler() {
        return getBeanFactory().getBean(InstrumentOptionsReplayMessageHandler.class);
    }

    //TODO:move to abstract parent?
    void onNewReplaySecurityDefinition(final SecurityDefinition sd) {
        synchronized (symbol2SecurityDefinitionMap) {
            if (needInitialSnapshot) {
                symbol2SecurityDefinitionMap.put(sd.getSymbol(), sd);
                if (sd.getTotNumReports() == symbol2SecurityDefinitionMap.size()) {
                    LOGGER.info("GOT IT! FIRST DEFINITIONS SET");
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
