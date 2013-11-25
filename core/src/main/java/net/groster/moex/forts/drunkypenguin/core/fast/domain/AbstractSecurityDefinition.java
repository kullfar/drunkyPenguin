package net.groster.moex.forts.drunkypenguin.core.fast.domain;

import java.math.BigDecimal;
import java.util.List;

public class AbstractSecurityDefinition extends AbstractFASTMessage {

    private long securityID;
    private int securityIDSource;
    private int totNumReports;
    private String symbol;
    private String securityDesc;
    private String cfiCode;
    private BigDecimal minPriceIncrement;
    private String currency;
    private BigDecimal lowLimitPx;
    private BigDecimal highLimitPx;
    private String marketSegmentID;
    private String marketID;
    private Integer securityTradingStatus;
    private List<MDFeedType> mdFeedTypes;

    public void setSecurityID(final long securityID) {
        this.securityID = securityID;
    }

    public void setSecurityIDSource(final int securityIDSource) {
        this.securityIDSource = securityIDSource;
    }

    public void setTotNumReports(final int totNumReports) {
        this.totNumReports = totNumReports;
    }

    public void setSymbol(final String symbol) {
        this.symbol = symbol;
    }

    public void setSecurityDesc(final String securityDesc) {
        this.securityDesc = securityDesc;
    }

    public void setCfiCode(final String cfiCode) {
        this.cfiCode = cfiCode;
    }

    public void setMinPriceIncrement(final BigDecimal minPriceIncrement) {
        this.minPriceIncrement = minPriceIncrement;
    }

    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    public void setLowLimitPx(final BigDecimal lowLimitPx) {
        this.lowLimitPx = lowLimitPx;
    }

    public void setHighLimitPx(final BigDecimal highLimitPx) {
        this.highLimitPx = highLimitPx;
    }

    public void setMarketSegmentID(final String marketSegmentID) {
        this.marketSegmentID = marketSegmentID;
    }

    public void setMarketID(final String marketID) {
        this.marketID = marketID;
    }

    public void setSecurityTradingStatus(final Integer securityTradingStatus) {
        this.securityTradingStatus = securityTradingStatus;
    }

    public void setMdFeedTypes(final List<MDFeedType> mdFeedTypes) {
        this.mdFeedTypes = mdFeedTypes;
    }
}
