package net.groster.moex.forts.drunkypenguin.core.fast.domain.enums;

import java.util.HashMap;
import java.util.Map;

public enum MDEntryTradeType {

    QUOTE_BASED_TRANSACTION('Q', "quote-based transaction"),
    TWO_SIDED_TRANSACTION('B', "two-sided transaction"),
    AUCTION_BASED_TRANSACTION('A', "auction-based transaction"),
    IPO_TRANSACTION('I', "IPO transaction");

    private final char code;

    private final String description;

    private MDEntryTradeType(final char code, final String description) {
        this.code = code;
        this.description = description;
    }

    public char getCode() {
        return code;
    }

    public static final Map<Character, MDEntryTradeType> CODE_TO_VALUE_MAP;

    static {
        final MDEntryTradeType[] mdEntryTradeTypes = MDEntryTradeType.values();
        CODE_TO_VALUE_MAP = new HashMap<>(mdEntryTradeTypes.length);
        for (final MDEntryTradeType mdEntryTradeType : mdEntryTradeTypes) {
            CODE_TO_VALUE_MAP.put(mdEntryTradeType.getCode(), mdEntryTradeType);
        }
    }

    public static MDEntryTradeType valueOf(final Character code) {
        final MDEntryTradeType ret = CODE_TO_VALUE_MAP.get(code);
        if (ret == null) {
            throw new IllegalArgumentException();
        }
        return ret;
    }

    public static enum TradeStatus {

        ONE_FIVE("1-5"),
        ELEVEN("11"),
        TWELVE("12"),
        EIGHTEEN("18"),
        NINETEEN("19"),
        TWENTYONE_TWENTYEIGHT("21-28");
        private final String code;

        private TradeStatus(final String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public static final Map<String, TradeStatus> CODE_TO_VALUE_MAP;

        static {
            final TradeStatus[] tradeStatuses = TradeStatus.values();
            CODE_TO_VALUE_MAP = new HashMap<>(tradeStatuses.length);
            for (final TradeStatus tradeStatus : tradeStatuses) {
                CODE_TO_VALUE_MAP.put(tradeStatus.getCode(), tradeStatus);
            }
        }

        public static TradeStatus valueOfByCode(final String code) {
            final TradeStatus ret = CODE_TO_VALUE_MAP.get(code);
            if (ret == null) {
                throw new IllegalArgumentException();
            }
            return ret;
        }

    }
}
