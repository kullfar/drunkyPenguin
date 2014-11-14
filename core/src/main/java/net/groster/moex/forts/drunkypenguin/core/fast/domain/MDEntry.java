package net.groster.moex.forts.drunkypenguin.core.fast.domain;

import java.math.BigDecimal;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.enums.MDEntryTradeType;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.enums.MDEntryType;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.enums.MDUpdateAction;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.enums.TrdType;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

public class MDEntry {

    private MDUpdateAction mdUpdateAction;
    private MDEntryType mdEntryType;
    private SecurityPK securityPK;
    private String symbol;
    private String securityGroup;
    private int exchangeTradingSessionID;
    private int rptSeq;
    private int marketDepth;
    private int mdPriceLevel;
    private long mdEntryID;
    private BigDecimal mdEntryPx;
    private long mdEntrySize;
    private LocalDate mdEntryDate;
    private LocalTime mdEntryTime;
    private int mumberOfOrders;
    private MDEntryTradeType mdEntryTradeType;
    private MDEntryTradeType.TradeStatus mdEntryTradeTypeTradeStatus;
    private TrdType trdType;
    private BigDecimal lastPx;
    private int mdFlags;
    private String currency;

    public void setMdUpdateAction(final MDUpdateAction mdUpdateAction) {
        this.mdUpdateAction = mdUpdateAction;
    }

    public void setMdEntryType(final MDEntryType mdEntryType) {
        this.mdEntryType = mdEntryType;
    }

    public void setSecurityPK(final SecurityPK securityPK) {
        this.securityPK = securityPK;
    }

    public void setSymbol(final String symbol) {
        this.symbol = symbol;
    }

    public void setSecurityGroup(final String securityGroup) {
        this.securityGroup = securityGroup;
    }

    public void setExchangeTradingSessionID(final int exchangeTradingSessionID) {
        this.exchangeTradingSessionID = exchangeTradingSessionID;
    }

    public void setRptSeq(final int rptSeq) {
        this.rptSeq = rptSeq;
    }

    public void setMarketDepth(final int marketDepth) {
        this.marketDepth = marketDepth;
    }

    public void setMdPriceLevel(final int mdPriceLevel) {
        this.mdPriceLevel = mdPriceLevel;
    }

    public void setMdEntryID(final long mdEntryID) {
        this.mdEntryID = mdEntryID;
    }

    public void setMdEntryPx(final BigDecimal mdEntryPx) {
        this.mdEntryPx = mdEntryPx;
    }

    public void setMdEntrySize(final long mdEntrySize) {
        this.mdEntrySize = mdEntrySize;
    }

    public void setMdEntryDate(final LocalDate mdEntryDate) {
        this.mdEntryDate = mdEntryDate;
    }

    public void setMdEntryTime(final LocalTime mdEntryTime) {
        this.mdEntryTime = mdEntryTime;
    }

    public void setMumberOfOrders(final int mumberOfOrders) {
        this.mumberOfOrders = mumberOfOrders;
    }

    public void setMdEntryTradeType(final MDEntryTradeType mdEntryTradeType) {
        this.mdEntryTradeType = mdEntryTradeType;
    }

    public void setMdEntryTradeTypeTradeStatus(final MDEntryTradeType.TradeStatus mdEntryTradeTypeTradeStatus) {
        this.mdEntryTradeTypeTradeStatus = mdEntryTradeTypeTradeStatus;
    }

    public void setTrdType(final TrdType trdType) {
        this.trdType = trdType;
    }

    public void setLastPx(final BigDecimal lastPx) {
        this.lastPx = lastPx;
    }

    public void setMdFlags(final int mdFlags) {
        this.mdFlags = mdFlags;
    }

    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    public MDEntryType getMdEntryType() {
        return mdEntryType;
    }

}
