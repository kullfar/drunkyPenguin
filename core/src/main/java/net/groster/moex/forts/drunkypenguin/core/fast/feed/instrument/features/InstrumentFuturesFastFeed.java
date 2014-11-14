package net.groster.moex.forts.drunkypenguin.core.fast.feed.instrument.features;

import java.util.Map;
import javax.inject.Named;
import javax.inject.Singleton;
import net.groster.moex.forts.drunkypenguin.core.fast.ConnectionThread;
import net.groster.moex.forts.drunkypenguin.core.fast.config.xml.MarketID;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.SecurityPK;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.msg.SecurityDefinition;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.msg.SecurityStatus;
import net.groster.moex.forts.drunkypenguin.core.fast.feed.instrument.AbstractInstrumentFastFeed;
import org.openfast.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//<MarketDataGroup feedType="RTS-INSTR" marketID="F" label="All Futures defintion">
@Named
@Singleton
public class InstrumentFuturesFastFeed extends AbstractInstrumentFastFeed {

    private static final Logger LOGGER = LoggerFactory.getLogger(InstrumentFuturesFastFeed.class);
    private boolean needInitialSnapshot = true;

    @Override
    public MarketID getMarketID() {
        return MarketID.FUTURES;
    }

    @Override
    public MessageHandler createInstrumentIncrementalMessageHandler() {
        return getBeanFactory().getBean(InstrumentFuturesIncrementalMessageHandler.class);
    }

    @Override
    public MessageHandler createInstrumentReplayMessageHandler() {
        return getBeanFactory().getBean(InstrumentFuturesReplayMessageHandler.class);
    }

    void onNewReplaySecurityDefinition(final SecurityDefinition sd) {
        final SecurityPK securityPK = sd.getSecurityPK();
        final Map<SecurityPK, SecurityDefinition> securityPK2SecurityDefinitionMap = getSecurityPK2SecurityDefinitionMap();
        synchronized (securityPK2SecurityDefinitionMap) {
            if (needInitialSnapshot) {
                final SecurityDefinition currentSD = securityPK2SecurityDefinitionMap.get(securityPK);
                if (currentSD == null || sd.getSendingTime().isAfter(currentSD.getSendingTime())) {
                    securityPK2SecurityDefinitionMap.put(securityPK, sd);

                    final SecurityStatus ss = getSecurityPK2SecurityStatusMap().get(securityPK);
                    if (ss != null && !updateSS(sd, ss)) {
                        getSecurityPK2SecurityStatusMap().remove(securityPK);
                    }

                    if (sd.getTotNumReports() == securityPK2SecurityDefinitionMap.size()) {
                        LOGGER.info("GOT IT! FUTURES DEFINITIONS SET");
                        //TODO: show it via REST
                        needInitialSnapshot = false;
                        for (final ConnectionThread connectionThread : getReplayConnectionThreads()) {
                            connectionThread.stopConnection();
                        }
                    } else if (sd.getTotNumReports() < securityPK2SecurityDefinitionMap.size()) {
                        securityPK2SecurityDefinitionMap.clear();
                    }
                }
            }
        }
    }
}
