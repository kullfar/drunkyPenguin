package net.groster.moex.forts.drunkypenguin.core.fast.domain;

import java.math.BigDecimal;
import java.util.List;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.enums.MarketID;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.enums.MarketSegmentID;
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

    BigDecimal getMinPriceIncrement();

    String getCurrency();

    BigDecimal getLowLimitPx();

    BigDecimal getHighLimitPx();

    MarketSegmentID getMarketSegmentID();

    MarketID getMarketID();

    Integer getSecurityTradingStatus();

    List<MDFeedType> getMdFeedTypes();

}
