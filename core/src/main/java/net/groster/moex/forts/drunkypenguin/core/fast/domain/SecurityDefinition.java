package net.groster.moex.forts.drunkypenguin.core.fast.domain;

import java.math.BigDecimal;
import java.util.List;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

public class SecurityDefinition extends AbstractSecurityDefinition {

    private String securityAltID;
    private String securityAltIDSource;
    private SecurityType securityType;
    private BigDecimal strikePrice;
    private BigDecimal contractMultiplier;
    private Integer tradingSessionID;
    private Integer exchangeTradingSessionID;
    private BigDecimal volatility;
    private List<Underlying> underlyings;
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

    public void setSecurityAltID(final String securityAltID) {
        this.securityAltID = securityAltID;
    }

    public void setSecurityAltIDSource(final String securityAltIDSource) {
        this.securityAltIDSource = securityAltIDSource;
    }

    public void setSecurityType(final SecurityType securityType) {
        this.securityType = securityType;
    }

    public void setStrikePrice(final BigDecimal strikePrice) {
        this.strikePrice = strikePrice;
    }

    public void setContractMultiplier(final BigDecimal contractMultiplier) {
        this.contractMultiplier = contractMultiplier;
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

    public void setUnderlying(final List<Underlying> underlying) {
        this.underlyings = underlying;
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
