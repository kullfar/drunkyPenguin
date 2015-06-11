package net.groster.moex.forts.drunkypenguin.core.fast.domain.enums;

public enum MarketID {

    RTSX("Moscow Exchange"),
    UKEX("Ukrainian Exchange"),
    ETSC("ETS Eurasian Trading System Commodity Exchange");
    private final String description;

    private MarketID(final String description) {
        this.description = description;
    }

}
