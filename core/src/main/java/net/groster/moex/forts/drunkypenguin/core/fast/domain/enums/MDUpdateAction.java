package net.groster.moex.forts.drunkypenguin.core.fast.domain.enums;

import java.util.HashMap;
import java.util.Map;

public enum MDUpdateAction {

    NEW(0),
    UPDATE(1),
    DELETE(2);

    private final int id;

    private MDUpdateAction(final int id) {
        this.id = id;
    }

    public static final Map<Integer, MDUpdateAction> ID_TO_VALUE_MAP;

    static {
        final MDUpdateAction[] mdUpdateActions = MDUpdateAction.values();
        ID_TO_VALUE_MAP = new HashMap<>(mdUpdateActions.length);
        for (final MDUpdateAction mdUpdateAction : mdUpdateActions) {
            ID_TO_VALUE_MAP.put(mdUpdateAction.getId(), mdUpdateAction);
        }
    }

    public int getId() {
        return id;
    }

    public static MDUpdateAction valueOf(final Integer id) {
        final MDUpdateAction ret = ID_TO_VALUE_MAP.get(id);
        if (ret == null) {
            throw new IllegalArgumentException();
        }
        return ret;
    }
}
