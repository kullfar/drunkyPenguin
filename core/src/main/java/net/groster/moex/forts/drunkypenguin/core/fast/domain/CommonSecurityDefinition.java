package net.groster.moex.forts.drunkypenguin.core.fast.domain;

import java.math.BigDecimal;
import java.util.List;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.enums.MarketID;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.enums.MarketSegmentID;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.enums.TradingSessionID;
import org.joda.time.DateTime;

public interface CommonSecurityDefinition {

    int getMsgSeqNum();

    String getMessageType();

    String getSenderCompID();

    DateTime getSendingTime();

    String getApplVerID();

    long getSecurityID();

    int getSecurityIDSource();

    int getTotNumReports();

    String getSymbol();

    String getSecurityDesc();

    String getCfiCode();

    BigDecimal getContractMultiplier();

    BigDecimal getMinPriceIncrement();

    BigDecimal getMinPriceIncrementAmount();

    String getCurrency();

    BigDecimal getLowLimitPx();

    BigDecimal getHighLimitPx();

    MarketSegmentID getMarketSegmentID();

    MarketID getMarketID();

    TradingSessionID getTradingSessionID();

    Integer getExchangeTradingSessionID();

    BigDecimal getInitialMarginOnBuy();

    BigDecimal getInitialMarginOnSell();

    Integer getSecurityTradingStatus();

    List<MDFeedType> getMdFeedTypes();

    List<Event> getEvntGrp();
}
