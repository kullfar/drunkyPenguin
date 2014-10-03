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
import net.groster.moex.forts.drunkypenguin.core.fast.domain.msg.SecurityStatus;
import net.groster.moex.forts.drunkypenguin.core.fast.feed.instrument.AbstractInstrumentFastFeed;
import org.openfast.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//<MarketDataGroup feedType="RTS-INSTR" marketID="O" label="All Options defintion">
@Named
@Singleton
public class InstrumentOptionsFastFeed extends AbstractInstrumentFastFeed {

    private static final Logger LOGGER = LoggerFactory.getLogger(InstrumentOptionsFastFeed.class);

    private final Map<SecurityPK, SecurityDefinition> securityPK2SecurityDefinitionMap = new HashMap<>();
    private final Map<SecurityPK, SecurityDefinitionUpdateReport> securityPK2SecurityDefinitionUpdateReportMap = new HashMap<>();
    private final Map<SecurityPK, SecurityStatus> securityPK2SecurityStatusMap = new HashMap<>();
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
        synchronized (securityPK2SecurityDefinitionMap) {
            if (needInitialSnapshot) {
                securityPK2SecurityDefinitionMap.put(securityPK, sd);
                final SecurityDefinitionUpdateReport sdur = securityPK2SecurityDefinitionUpdateReportMap.remove(securityPK);
                if (sdur != null) {
                    updateSD(sd, sdur);
                }

                final SecurityStatus ss = securityPK2SecurityStatusMap.remove(securityPK);
                if (ss != null) {
                    updateSS(sd, ss);
                }

                if (sd.getTotNumReports() == securityPK2SecurityDefinitionMap.size()) {
                    LOGGER.info("GOT IT! OPTIONS DEFINITIONS SET");
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

    void onSecurityStatus(final SecurityStatus securityStatus) {
        final SecurityPK securityPK = securityStatus.getSecurityPK();
        synchronized (securityPK2SecurityDefinitionMap) {
            final SecurityDefinition securityDefinition = securityPK2SecurityDefinitionMap.get(securityPK);
            if (securityDefinition == null) {
                final SecurityStatus prevSS = securityPK2SecurityStatusMap.get(securityPK);
                if (prevSS == null || prevSS.getSendingTime().isBefore(securityStatus.getSendingTime())) {
                    securityPK2SecurityStatusMap.put(securityPK, securityStatus);
                }
            } else {
                updateSS(securityDefinition, securityStatus);
            }
        }
    }

    void onSecurityDefinitionUpdateReport(final SecurityDefinitionUpdateReport securityDefinitionUpdateReport) {
        final SecurityPK securityPK = securityDefinitionUpdateReport.getSecurityPK();
        synchronized (securityPK2SecurityDefinitionMap) {
            final SecurityDefinition securityDefinition = securityPK2SecurityDefinitionMap.get(securityPK);
            if (securityDefinition == null) {
                final SecurityDefinitionUpdateReport prevSDUR = securityPK2SecurityDefinitionUpdateReportMap.get(securityPK);
                if (prevSDUR == null || prevSDUR.getSendingTime().isBefore(securityDefinitionUpdateReport.getSendingTime())) {
                    securityPK2SecurityDefinitionUpdateReportMap.put(securityPK, securityDefinitionUpdateReport);
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

    private void updateSS(final SecurityDefinition securityDefinition, final SecurityStatus securityStatus) {
        if (securityStatus.getSendingTime().isAfter(securityDefinition.getSendingTime())) {

            final String symbol = securityStatus.getSymbol();
            if (symbol != null) {
                securityDefinition.setSymbol(symbol);
            }

            final Integer securityTradingStatus = securityStatus.getSecurityTradingStatus();
            if (securityTradingStatus != null) {
                securityDefinition.setSecurityTradingStatus(securityTradingStatus);
            }

            final BigDecimal lowLimitPx = securityStatus.getLowLimitPx();
            if (lowLimitPx != null) {
                securityDefinition.setLowLimitPx(lowLimitPx);
            }

            final BigDecimal highLimitPx = securityStatus.getHighLimitPx();
            if (highLimitPx != null) {
                securityDefinition.setHighLimitPx(highLimitPx);
            }

            final BigDecimal initialMarginOnBuy = securityStatus.getInitialMarginOnBuy();
            if (initialMarginOnBuy != null) {
                securityDefinition.setInitialMarginOnBuy(initialMarginOnBuy);
            }

            final BigDecimal initialMarginOnSell = securityStatus.getInitialMarginOnSell();
            if (initialMarginOnSell != null) {
                securityDefinition.setInitialMarginOnSell(initialMarginOnSell);
            }

            final BigDecimal initialMarginSyntetic = securityStatus.getInitialMarginSyntetic();
            if (initialMarginSyntetic != null) {
                securityDefinition.setInitialMarginSyntetic(initialMarginSyntetic);
            }
        }
    }
}
