package net.groster.moex.forts.drunkypenguin.core.fast.feed.instrument.options;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Named;
import javax.inject.Singleton;
import net.groster.moex.forts.drunkypenguin.core.fast.ConnectionThread;
import net.groster.moex.forts.drunkypenguin.core.fast.config.xml.MarketID;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.SecurityPK;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.msg.SecurityDefinition;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.msg.SecurityDefinitionUpdateReport;
import net.groster.moex.forts.drunkypenguin.core.fast.feed.instrument.AbstractInstrumentFastFeed;
import org.openfast.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//<MarketDataGroup feedType="RTS-INSTR" marketID="O" label="All Options defintion">
@Named
@Singleton
public class InstrumentOptionsFastFeed extends AbstractInstrumentFastFeed {

    private static final Logger LOGGER = LoggerFactory.getLogger(InstrumentOptionsFastFeed.class);

    private final Map<SecurityPK, SecurityDefinition> symbol2SecurityDefinitionMap = new HashMap<>();
    private final Map<SecurityPK, SecurityDefinitionUpdateReport> symbol2SecurityDefinitionUpdateReportMap = new HashMap<>();
    private boolean needInitialSnapshot = true;

    @Override
    public MarketID getMarketID() {
        return MarketID.OPTIONS;
    }

    @Override
    public MessageHandler createInstrumentIncrementalMessageHandler() {
        return getBeanFactory().getBean(InstrumentOptionsIncrementalMessageHandler.class);
    }

    @Override
    public MessageHandler createInstrumentReplayMessageHandler() {
        return getBeanFactory().getBean(InstrumentOptionsReplayMessageHandler.class);
    }

    //TODO:move to abstract parent?
    void onNewReplaySecurityDefinition(final SecurityDefinition sd) {
        final SecurityPK securityPK = sd.getSecurityPK();
        synchronized (symbol2SecurityDefinitionMap) {
            if (needInitialSnapshot) {
                symbol2SecurityDefinitionMap.put(securityPK, sd);
                final SecurityDefinitionUpdateReport sdur = symbol2SecurityDefinitionUpdateReportMap.remove(securityPK);
                if (sdur != null) {
                    updateSD(sd, sdur);
                }

                if (sd.getTotNumReports() == symbol2SecurityDefinitionMap.size()) {
                    LOGGER.info("GOT IT! OPTIONS DEFINITIONS SET");
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

    void onSecurityDefinitionUpdateReport(final SecurityDefinitionUpdateReport securityDefinitionUpdateReport) {
        final SecurityPK securityPK = securityDefinitionUpdateReport.getSecurityPK();
        synchronized (symbol2SecurityDefinitionMap) {
            final SecurityDefinition securityDefinition = symbol2SecurityDefinitionMap.get(securityPK);
            if (securityDefinition == null) {
                final SecurityDefinitionUpdateReport prevSDUR = symbol2SecurityDefinitionUpdateReportMap.get(securityPK);
                if (prevSDUR == null || prevSDUR.getSendingTime().isBefore(securityDefinitionUpdateReport.getSendingTime())) {
                    symbol2SecurityDefinitionUpdateReportMap.put(securityPK, securityDefinitionUpdateReport);
                }
            } else {
                updateSD(securityDefinition, securityDefinitionUpdateReport);
            }
        }
    }

    private static void updateSD(final SecurityDefinition sd, final SecurityDefinitionUpdateReport sdur) {
        if (sdur.getSendingTime().isAfter(sd.getSendingTime())) {

            final BigDecimal volatility = sdur.getVolatility();
            if (volatility != null) {
                sd.setVolatility(volatility);
            }
            final BigDecimal theorPrice = sdur.getTheorPrice();
            if (theorPrice != null) {
                sd.setTheorPrice(theorPrice);
            }
            final BigDecimal theorPriceLimit = sdur.getTheorPriceLimit();
            if (theorPriceLimit != null) {
                sd.setTheorPriceLimit(theorPriceLimit);
            }
        }

    }
}
