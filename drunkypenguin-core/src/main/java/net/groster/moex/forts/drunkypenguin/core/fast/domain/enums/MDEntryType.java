package net.groster.moex.forts.drunkypenguin.core.fast.domain.enums;

import java.util.HashMap;
import java.util.Map;

public enum MDEntryType {

    BID("0", "Bid"),
    OFFER("1", "Offer"),
    TRADE("2", "Trade"),
    OPENING_PRICE("4", "Opening Price"),
    CLOSING_PRICE("5", "Closing Price"),
    SETTLEMENT_PRICE("6", "Settlement Price"),
    TRADING_SESSION_HIGH_PRICE("7", "Trading Session High Price"),
    TRADING_SESSION_LOW_PRICE("8", "Trading Session Low Price"),
    TRADING_SESSION_VWAP_PRICE("9", "Trading Session VWAP Price"),
    CUMULATIVE_TRADE_VOLUME("B", "Cumulative Trade Volume"),
    OPEN_INTEREST("C", "Open Interest"),
    EMPTY_BOOK("J", "Empty Book"),
    FCSM_MARKET_PRICE("m", "FCSM Market price"),
    OFFICIAL_PRICE("p", "Official Price"),
    MARKET_CAPITALIZATION("s", "Market capitalization"),
    TOTAL_BID_VOLUME("v", "Total bid volume"),
    TOTAL_OFFER_VOLUME("w", "Total offer volume");

    private final String code;

    private final String description;

    private MDEntryType(final String code, final String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public static final Map<String, MDEntryType> CODE_TO_VALUE_MAP;

    static {
        final MDEntryType[] mdEntryTypes = MDEntryType.values();
        CODE_TO_VALUE_MAP = new HashMap<>(mdEntryTypes.length);
        for (final MDEntryType mdEntryType : mdEntryTypes) {
            CODE_TO_VALUE_MAP.put(mdEntryType.getCode(), mdEntryType);
        }
    }

    public static MDEntryType valueOfByCode(final String code) {
        final MDEntryType ret = CODE_TO_VALUE_MAP.get(code);
        if (ret == null) {
            throw new IllegalArgumentException();
        }
        return ret;
    }
}
