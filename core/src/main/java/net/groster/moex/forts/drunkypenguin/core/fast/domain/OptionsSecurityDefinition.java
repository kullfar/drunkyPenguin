package net.groster.moex.forts.drunkypenguin.core.fast.domain;

import java.math.BigDecimal;
import java.util.List;

public interface OptionsSecurityDefinition {

    BigDecimal getStrikePrice();

    BigDecimal getVolatility();

    BigDecimal getTheorPrice();

    BigDecimal getTheorPriceLimit();

    BigDecimal getInitialMarginSyntetic();

    List<Underlying> getUnderlyings();

}
