package net.groster.moex.forts.drunkypenguin.core.fast.feed.instrument;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import net.groster.moex.forts.drunkypenguin.core.fast.ConnectionThread;
import net.groster.moex.forts.drunkypenguin.core.fast.FASTService;
import net.groster.moex.forts.drunkypenguin.core.fast.config.xml.Connection;
import net.groster.moex.forts.drunkypenguin.core.fast.config.xml.FeedType;
import net.groster.moex.forts.drunkypenguin.core.fast.config.xml.MarketDataGroup;
import net.groster.moex.forts.drunkypenguin.core.fast.config.xml.MarketID;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.SecurityPK;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.msg.SecurityDefinition;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.msg.SecurityStatus;
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

    private final Map<SecurityPK, SecurityStatus> securityPK2SecurityStatusMap = new HashMap<>();
    private final Map<SecurityPK, SecurityDefinition> securityPK2SecurityDefinitionMap = new HashMap<>();

    public void start() {
        final MarketID marketID = getMarketID();
        final MarketDataGroup marketDataGroup = fastService.getMarketDataGroup(FeedType.RTS_INSTR, marketID);
        for (final Connection connection : marketDataGroup.getConnections()) {
            final String feedConnectionName = FeedType.RTS_INSTR.name() + ':' + marketID + '>' + connection.getType() + ':'
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

    public void onSecurityStatus(final SecurityStatus securityStatus) {
        final SecurityPK securityPK = securityStatus.getSecurityPK();
        synchronized (securityPK2SecurityDefinitionMap) {
            final SecurityStatus prevSS = securityPK2SecurityStatusMap.get(securityPK);
            if (prevSS == null || prevSS.getSendingTime().isBefore(securityStatus.getSendingTime())) {
                final SecurityDefinition securityDefinition = securityPK2SecurityDefinitionMap.get(securityPK);

                if (securityDefinition == null || updateSS(securityDefinition, securityStatus)) {
                    securityPK2SecurityStatusMap.put(securityPK, securityStatus);
                }
            }
        }
    }

    protected boolean updateSS(final SecurityDefinition securityDefinition, final SecurityStatus securityStatus) {
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
            return true;
        } else {
            return false;
        }
    }

    public Map<SecurityPK, SecurityDefinition> getSecurityPK2SecurityDefinitionMap() {
        return securityPK2SecurityDefinitionMap;
    }

    public Map<SecurityPK, SecurityStatus> getSecurityPK2SecurityStatusMap() {
        return securityPK2SecurityStatusMap;
    }

}
