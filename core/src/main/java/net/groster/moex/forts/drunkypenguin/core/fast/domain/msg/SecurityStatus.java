package net.groster.moex.forts.drunkypenguin.core.fast.domain.msg;

import java.math.BigDecimal;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.AbstractFASTMessage;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.SecurityPK;
import org.openfast.Message;
import org.openfast.ScalarValue;

public class SecurityStatus extends AbstractFASTMessage {
//TODO:use seqNum

    private final String symbol;
    private final SecurityPK securityPK;
    private final Integer securityTradingStatus;
    private final BigDecimal lowLimitPx;
    private final BigDecimal highLimitPx;
    private final BigDecimal initialMarginOnBuy;
    private final BigDecimal initialMarginOnSell;
    private final BigDecimal initialMarginSyntetic;

    public SecurityStatus(final Message fastMessage) {
        super(fastMessage);
        symbol = fastMessage.getString("Symbol");
        securityPK = new SecurityPK(fastMessage.getLong("SecurityID"), fastMessage.getInt("SecurityIDSource"));

        final ScalarValue securityTradingStatusScalarValue = fastMessage.getScalar("SecurityTradingStatus");
        securityTradingStatus = securityTradingStatusScalarValue == null ? null : securityTradingStatusScalarValue.
                toInt();

        final ScalarValue lowLimitPxScalarValue = fastMessage.getScalar("LowLimitPx");
        lowLimitPx = lowLimitPxScalarValue == null ? null : lowLimitPxScalarValue.toBigDecimal();

        final ScalarValue highLimitPxScalarValue = fastMessage.getScalar("HighLimitPx");
        highLimitPx = highLimitPxScalarValue == null ? null : highLimitPxScalarValue.toBigDecimal();

        final ScalarValue initialMarginOnBuyScalarValue = fastMessage.getScalar("InitialMarginOnBuy");
        initialMarginOnBuy = initialMarginOnBuyScalarValue == null ? null : initialMarginOnBuyScalarValue.toBigDecimal();

        final ScalarValue initialMarginOnSellScalarValue = fastMessage.getScalar("InitialMarginOnSell");
        initialMarginOnSell = initialMarginOnSellScalarValue == null ? null : initialMarginOnSellScalarValue.
                toBigDecimal();

        final ScalarValue initialMarginSynteticScalarValue = fastMessage.getScalar("InitialMarginSyntetic");
        initialMarginSyntetic = initialMarginSynteticScalarValue == null ? null : initialMarginSynteticScalarValue.
                toBigDecimal();
    }

    public SecurityPK getSecurityPK() {
        return securityPK;
    }

    public Integer getSecurityTradingStatus() {
        return securityTradingStatus;
    }

    public BigDecimal getLowLimitPx() {
        return lowLimitPx;
    }

    public BigDecimal getHighLimitPx() {
        return highLimitPx;
    }

    public BigDecimal getInitialMarginOnBuy() {
        return initialMarginOnBuy;
    }

    public BigDecimal getInitialMarginOnSell() {
        return initialMarginOnSell;
    }

    public BigDecimal getInitialMarginSyntetic() {
        return initialMarginSyntetic;
    }

    public String getSymbol() {
        return symbol;
    }

}
