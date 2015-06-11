package net.groster.moex.forts.drunkypenguin.core.fast.domain.enums;

import java.util.HashMap;
import java.util.Map;

public enum TrdType {

    REGULAR_TRADE(0, "Regular Trade"),
    PRIVATELY_NEGOTIATED_TRADES(22, "Privately Negotiated Trades"),
    OPTION_EXERCISE(45, "Option exercise"),
    FUTURE_EXERCISE(1000, "Future exercise"),
    RTS_STANDARD_EXERCISE(1001, "RTS-standard exercise"),
    RESTATEMENT_TRADE(1002, "Restatement trade"),
    OPTION_EXPIRATION(1003, "Option expiration");

    private final int id;
    private final String description;

    private TrdType(final int id, final String description) {
        this.id = id;
        this.description = description;
    }

    public static final Map<Integer, TrdType> ID_TO_VALUE_MAP;

    static {
        final TrdType[] trdTypes = TrdType.values();
        ID_TO_VALUE_MAP = new HashMap<>(trdTypes.length);
        for (final TrdType trdType : trdTypes) {
            ID_TO_VALUE_MAP.put(trdType.getId(), trdType);
        }
    }

    public int getId() {
        return id;
    }

    public static TrdType valueOf(final Integer id) {
        final TrdType ret = ID_TO_VALUE_MAP.get(id);
        if (ret == null) {
            throw new IllegalArgumentException();
        }
        return ret;
    }

}
