package net.groster.moex.forts.drunkypenguin.core.fast;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum MessageType {

    DEFAULT_INCREMENTAL_REFRESH_MESSAGE("DefaultIncrementalRefreshMessage"),
    DEFAULT_SNAPSHOT_MESSAGE("DefaultSnapshotMessage"),
    SECURITY_DEFINITION("SecurityDefinition"),
    SECURITY_DEFINITION_UPDATE_REPORT("SecurityDefinitionUpdateReport"),
    SECURITY_STATUS("SecurityStatus"),
    HEARTBEAT("Heartbeat"),
    SEQUENCE_RESET("SequenceReset"),
    RESET("Reset"),
    TRADING_SESSION_STATUS("TradingSessionStatus"),
    NEWS("News"),
    LOGON("Logon"),
    LOGOUT("Logout");
    private int id;
    private final String name;
    private static final Map<String, MessageType> NAME_2_MESSAGE_TYPE;

    static {
        final Map<String, MessageType> tmpName2MessageType = new HashMap<>(MessageType.values().length);
        for (final MessageType messageType : MessageType.values()) {
            tmpName2MessageType.put(messageType.getName(), messageType);
        }
        NAME_2_MESSAGE_TYPE = Collections.unmodifiableMap(tmpName2MessageType);
    }

    private MessageType(final String name) {
        this.name = name;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public static MessageType getByName(final String name) {
        final MessageType messageType = NAME_2_MESSAGE_TYPE.get(name);
        if (messageType == null) {
            throw new IllegalArgumentException();
        }
        return messageType;
    }

    public String getName() {
        return name;
    }
}
