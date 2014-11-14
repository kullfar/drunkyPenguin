package net.groster.moex.forts.drunkypenguin.core.fast;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.AbstractFASTMessage;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.msg.DefaultIncrementalRefreshMessage;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.msg.Heartbeat;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.msg.SecurityDefinition;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.msg.SecurityDefinitionUpdateReport;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.msg.SecurityStatus;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.msg.SequenceReset;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.openfast.GroupValue;
import org.openfast.Message;
import org.openfast.ScalarValue;
import org.openfast.SequenceValue;
import org.openfast.template.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum MessageType {

    DEFAULT_INCREMENTAL_REFRESH_MESSAGE("DefaultIncrementalRefreshMessage") {
                @Override
                public DefaultIncrementalRefreshMessage parseFASTMessage(final Message fastMessage) {
                    return new DefaultIncrementalRefreshMessage(fastMessage);
                }
            },
    DEFAULT_SNAPSHOT_MESSAGE("DefaultSnapshotMessage"),
    SECURITY_DEFINITION("SecurityDefinition") {
                @Override
                public SecurityDefinition parseFASTMessage(final Message fastMessage) {
                    return new SecurityDefinition(fastMessage);
                }
            },
    SECURITY_DEFINITION_UPDATE_REPORT("SecurityDefinitionUpdateReport") {
                @Override
                public SecurityDefinitionUpdateReport parseFASTMessage(final Message fastMessage) {
                    return new SecurityDefinitionUpdateReport(fastMessage);
                }
            },
    SECURITY_STATUS("SecurityStatus") {
                @Override
                public SecurityStatus parseFASTMessage(final Message fastMessage) {
                    return new SecurityStatus(fastMessage);
                }
            },
    HEARTBEAT("Heartbeat") {
                @Override
                public Heartbeat parseFASTMessage(final Message fastMessage) {
                    return new Heartbeat(fastMessage);
                }
            },
    SEQUENCE_RESET("SequenceReset") {
                @Override
                public SequenceReset parseFASTMessage(final Message fastMessage) {
                    return new SequenceReset(fastMessage);
                }
            },
    RESET("Reset"),
    TRADING_SESSION_STATUS("TradingSessionStatus"),
    NEWS("News"),
    LOGON("Logon"),
    LOGOUT("Logout");
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageType.class);
    public static final DateTimeFormatter FAST_DATETIME_UTC_FORMATTER = DateTimeFormat.forPattern("yyyyMMddHHmmssSSS").
            withZone(DateTimeZone.UTC);
    public static final DateTimeFormatter FAST_DATE_UTC_FORMATTER = DateTimeFormat.forPattern("yyyyMMdd").withZone(
            DateTimeZone.UTC);
    public static final DateTimeFormatter FAST_TIME_UTC_FORMATTER = DateTimeFormat.forPattern("HHmmssSSS").withZone(
            DateTimeZone.UTC);
    private final String name;
    private static final Map<String, MessageType> NAME_2_MESSAGE_TYPE;
    private static final Map<Integer, MessageType> ID_2_MESSAGE_TYPE = new HashMap<>(MessageType.values().length);

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
        ID_2_MESSAGE_TYPE.put(id, this);
    }

    public AbstractFASTMessage parseFASTMessage(final Message fastMessage) {
        return null;
    }

    public static MessageType getByName(final String name) {
        final MessageType messageType = NAME_2_MESSAGE_TYPE.get(name);
        if (messageType == null) {
            throw new IllegalArgumentException();
        }
        return messageType;
    }

    public static MessageType getById(final Integer id) {
        final MessageType messageType = ID_2_MESSAGE_TYPE.get(id);
        if (messageType == null) {
            throw new IllegalArgumentException();
        }
        return messageType;
    }

    public String getName() {
        return name;
    }

    public static void logUnknownFASTMessage(final Message message) {
        final StringBuilder details = new StringBuilder("Have received unknown FAST message\n")
                .append("'rawMessage' = '").append(message).append("'.\n")
                .append("Full details are:\n");
        for (final Field f : message.getTemplate().getFieldDefinitions()) {
            details.append("\t'").append(f.getName()).append("'='");
            if (f.getValueType() == ScalarValue.class) {
                final ScalarValue scalarValue = message.getScalar(f.getName());
                if (scalarValue == null) {
                    details.append("null");
                } else {
                    details.append('(').append(scalarValue.getClass()).append(')').append(scalarValue);
                }
            } else {
                final SequenceValue sequenceValue = message.getSequence(f.getName());
                if (sequenceValue == null) {
                    details.append("[null]");
                } else {
                    for (final GroupValue groupValue : sequenceValue.getValues()) {
                        details.append("[\n");
                        for (final Field groupField : groupValue.getGroup().getFieldDefinitions()) {
                            details.append("\t\t'").append(groupField.getName()).append("'='");
                            if (groupValue.getValue(groupField.getName()) == null) {
                                details.append("null");
                            } else {
                                final ScalarValue scalarValue = groupValue.getScalar(groupField.getName());
                                if (scalarValue == null) {
                                    details.append("null");
                                } else {
                                    details.append('(').append(scalarValue.getClass()).append(')').append(
                                            scalarValue);
                                }
                            }
                            details.append("\'\n");
                        }
                        details.append("\t]");
                    }
                }
            }
            details.append("\'\n");
        }
        LOGGER.warn(details.toString());
    }

}
