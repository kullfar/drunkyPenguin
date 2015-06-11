package net.groster.moex.forts.drunkypenguin.core.fast.domain;

import java.math.BigDecimal;

public class InstrumentLeg {

    private String legSymbol;
    private long legSecurityID;
    private BigDecimal legRatioQty;

    public void setLegSymbol(final String legSymbol) {
        this.legSymbol = legSymbol;
    }

    public void setLegSecurityID(final long legSecurityID) {
        this.legSecurityID = legSecurityID;
    }

    public void setLegRatioQty(final BigDecimal legRatioQty) {
        this.legRatioQty = legRatioQty;
    }

}
