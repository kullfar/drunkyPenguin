package net.groster.moex.forts.drunkypenguin.core.fast.domain;

import java.math.BigDecimal;
import java.util.List;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.enums.SecurityType;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

public interface FuturesSecurityDefinition extends CommonSecurityDefinition {

    String getSecurityAltID();

    String getSecurityAltIDSource();

    SecurityType getSecurityType();

    BigDecimal getStrikePrice();

    BigDecimal getContractMultiplier();

    Integer getTradingSessionID();

    Integer getExchangeTradingSessionID();

    BigDecimal getVolatility();

    List<Underlying> getUnderlyings();

    BigDecimal getMinPriceIncrementAmount();

    BigDecimal getInitialMarginOnBuy();

    BigDecimal getInitialMarginOnSell();

    BigDecimal getInitialMarginSyntetic();

    String getQuotationList();

    BigDecimal getTheorPrice();

    BigDecimal getTheorPriceLimit();

    List<InstrumentLeg> getInstrumentLegs();

    List<InstrumentAttribute> getInstrumentAttributes();

    BigDecimal getUnderlyingQty();

    String getUnderlyingCurrency();

    List<Event> getEvntGrp();

    LocalDate getMaturityDate();

    LocalTime getMaturityTime();

}
