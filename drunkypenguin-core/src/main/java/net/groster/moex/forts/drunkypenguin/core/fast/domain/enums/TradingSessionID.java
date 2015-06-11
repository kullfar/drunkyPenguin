package net.groster.moex.forts.drunkypenguin.core.fast.domain.enums;

import java.util.HashMap;
import java.util.Map;

public enum TradingSessionID {

    DAY(1),
    MORNING(3),
    EVENING(5);
    private final int id;

    private TradingSessionID(final int id) {
        this.id = id;
    }
    public static final Map<Integer, TradingSessionID> ID_TO_VALUE_MAP;

    static {
        final TradingSessionID[] tradingSessionIDs = TradingSessionID.values();
        ID_TO_VALUE_MAP = new HashMap<>(tradingSessionIDs.length);
        for (final TradingSessionID tradingSessionID : tradingSessionIDs) {
            ID_TO_VALUE_MAP.put(tradingSessionID.getId(), tradingSessionID);
        }
    }

    public int getId() {
        return id;
    }

    public static TradingSessionID valueOf(final Integer id) {
        final TradingSessionID ret = ID_TO_VALUE_MAP.get(id);
        if (ret == null) {
            throw new IllegalArgumentException();
        }
        return ret;
    }

}
