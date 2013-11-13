package net.groster.moex.forts.drunkypenguin.core.fast.domain;

import java.math.BigDecimal;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

public class SecurityDefinition {

    private String applVerID;
    private String messageType;
    private String senderCompID;
    private int msgSeqNum;
    private DateTime sendingTime;
    private int totNumReports;
    private String symbol;
    private String securityDesc;
    private long securityID;
    private int securityIDSource;
    private String securityAltID;
    private String securityAltIDSource;
    private String securityType;
    private String cfiCode;
    private BigDecimal strikePrice;
    private BigDecimal contractMultiplier;
    private Integer securityTradingStatus;
    private String currency;
    private String marketID;
    private String marketSegmentID;
    private Integer tradingSessionID;
    private Integer exchangeTradingSessionID;
    private BigDecimal volatility;
    private List<MDFeedType> mdFeedTypes;
    private List<Underlying> underlyings;
    private BigDecimal highLimitPx;
    private BigDecimal lowLimitPx;
    private BigDecimal minPriceIncrement;
    private BigDecimal minPriceIncrementAmount;
    private BigDecimal initialMarginOnBuy;
    private BigDecimal initialMarginOnSell;
    private BigDecimal initialMarginSyntetic;
    private String quotationList;
    private BigDecimal theorPrice;
    private BigDecimal theorPriceLimit;
    private List<InstrumentLeg> instrumentLegs;
    private List<InstrumentAttribute> instrumentAttributes;
    private BigDecimal underlyingQty;
    private String underlyingCurrency;
    private List<Event> evntGrp;
    private LocalDate maturityDate;
    private LocalTime maturityTime;

    public void setApplVerID(final String applVerID) {
        this.applVerID = applVerID;
    }

    public void setMessageType(final String messageType) {
        this.messageType = messageType;
    }

    public void setSenderCompID(final String senderCompID) {
        this.senderCompID = senderCompID;
    }

    public void setMsgSeqNum(final int msgSeqNum) {
        this.msgSeqNum = msgSeqNum;
    }

    public void setSendingTime(final DateTime sendingTime) {
        this.sendingTime = sendingTime;
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

    public void setSecurityID(final long securityID) {
        this.securityID = securityID;
    }

    public void setSecurityIDSource(final int securityIDSource) {
        this.securityIDSource = securityIDSource;
    }

    public void setSecurityAltID(final String securityAltID) {
        this.securityAltID = securityAltID;
    }

    public void setSecurityAltIDSource(final String securityAltIDSource) {
        this.securityAltIDSource = securityAltIDSource;
    }

    public void setSecurityType(final String securityType) {
        this.securityType = securityType;
    }

    public void setCfiCode(final String cfiCode) {
        this.cfiCode = cfiCode;
    }

    public void setStrikePrice(final BigDecimal strikePrice) {
        this.strikePrice = strikePrice;
    }

    public void setContractMultiplier(final BigDecimal contractMultiplier) {
        this.contractMultiplier = contractMultiplier;
    }

    public void setSecurityTradingStatus(final Integer securityTradingStatus) {
        this.securityTradingStatus = securityTradingStatus;
    }

    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    public void setMarketID(final String marketID) {
        this.marketID = marketID;
    }

    public void setMarketSegmentID(final String marketSegmentID) {
        this.marketSegmentID = marketSegmentID;
    }

    public void setTradingSessionID(final Integer tradingSessionID) {
        this.tradingSessionID = tradingSessionID;
    }

    public void setExchangeTradingSessionID(final Integer exchangeTradingSessionID) {
        this.exchangeTradingSessionID = exchangeTradingSessionID;
    }

    public void setVolatility(final BigDecimal volatility) {
        this.volatility = volatility;
    }

    public void setMdFeedTypes(final List<MDFeedType> mdFeedTypes) {
        this.mdFeedTypes = mdFeedTypes;
    }

    public void setUnderlying(final List<Underlying> underlying) {
        this.underlyings = underlying;
    }

    public void setHighLimitPx(final BigDecimal highLimitPx) {
        this.highLimitPx = highLimitPx;
    }

    public void setLowLimitPx(final BigDecimal lowLimitPx) {
        this.lowLimitPx = lowLimitPx;
    }

    public void setMinPriceIncrement(final BigDecimal minPriceIncrement) {
        this.minPriceIncrement = minPriceIncrement;
    }

    public void setMinPriceIncrementAmount(final BigDecimal minPriceIncrementAmount) {
        this.minPriceIncrementAmount = minPriceIncrementAmount;
    }

    public void setInitialMarginOnBuy(final BigDecimal initialMarginOnBuy) {
        this.initialMarginOnBuy = initialMarginOnBuy;
    }

    public void setInitialMarginOnSell(final BigDecimal initialMarginOnSell) {
        this.initialMarginOnSell = initialMarginOnSell;
    }

    public void setInitialMarginSyntetic(final BigDecimal initialMarginSyntetic) {
        this.initialMarginSyntetic = initialMarginSyntetic;
    }

    public void setQuotationList(final String quotationList) {
        this.quotationList = quotationList;
    }

    public void setTheorPrice(final BigDecimal theorPrice) {
        this.theorPrice = theorPrice;
    }

    public void setTheorPriceLimit(final BigDecimal theorPriceLimit) {
        this.theorPriceLimit = theorPriceLimit;
    }

    public void setInstrumentLegs(final List<InstrumentLeg> instrumentLegs) {
        this.instrumentLegs = instrumentLegs;
    }

    public void setInstrumentAttributes(final List<InstrumentAttribute> instrumentAttributes) {
        this.instrumentAttributes = instrumentAttributes;
    }

    public void setUnderlyingQty(final BigDecimal underlyingQty) {
        this.underlyingQty = underlyingQty;
    }

    public void setUnderlyingCurrency(final String underlyingCurrency) {
        this.underlyingCurrency = underlyingCurrency;
    }

    public void setEvntGrp(final List<Event> evntGrp) {
        this.evntGrp = evntGrp;
    }

    public void setMaturityDate(final LocalDate maturityDate) {
        this.maturityDate = maturityDate;
    }

    public void setMaturityTime(final LocalTime maturityTime) {
        this.maturityTime = maturityTime;
    }
}
