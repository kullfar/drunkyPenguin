package net.groster.moex.forts.drunkypenguin.core.fast.domain.msg;

import java.math.BigDecimal;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.AbstractFASTMessage;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.SecurityPK;
import org.openfast.Message;
import org.openfast.ScalarValue;

public class SecurityDefinitionUpdateReport extends AbstractFASTMessage {

    private final SecurityPK securityPK;
    private BigDecimal volatility;
    private BigDecimal theorPrice;
    private BigDecimal theorPriceLimit;

    public SecurityDefinitionUpdateReport(final Message fastMessage) {
        super(fastMessage);
        securityPK = new SecurityPK(fastMessage.getLong("SecurityID"), fastMessage.getInt("SecurityIDSource"));
        final ScalarValue volatilityScalarValue = fastMessage.getScalar("Volatility");
        volatility = volatilityScalarValue == null ? null : volatilityScalarValue.toBigDecimal();

        final ScalarValue theorPriceScalarValue = fastMessage.getScalar("TheorPrice");
        theorPrice = theorPriceScalarValue == null ? null : theorPriceScalarValue.toBigDecimal();

        final ScalarValue theorPriceLimitScalarValue = fastMessage.getScalar("TheorPriceLimit");
        theorPriceLimit = theorPriceLimitScalarValue == null ? null : theorPriceLimitScalarValue.toBigDecimal();
    }

    public SecurityPK getSecurityPK() {
        return securityPK;
    }

    public BigDecimal getVolatility() {
        return volatility;
    }

    public BigDecimal getTheorPrice() {
        return theorPrice;
    }

    public BigDecimal getTheorPriceLimit() {
        return theorPriceLimit;
    }

    public void setVolatility(final BigDecimal volatility) {
        this.volatility = volatility;
    }

    public void setTheorPrice(final BigDecimal theorPrice) {
        this.theorPrice = theorPrice;
    }

    public void setTheorPriceLimit(final BigDecimal theorPriceLimit) {
        this.theorPriceLimit = theorPriceLimit;
    }
}
