package net.groster.moex.forts.drunkypenguin.core.fast.domain;

import net.groster.moex.forts.drunkypenguin.core.Constants;
import net.groster.moex.forts.drunkypenguin.core.fast.MessageType;
import org.joda.time.DateTime;
import org.openfast.Message;

public abstract class AbstractFASTMessage {

    private final int msgSeqNum;
    private final String messageType;
    private final String senderCompID;
    private final DateTime sendingTime;
    private final String applVerID;

    public AbstractFASTMessage(final Message fastMessage) {
        msgSeqNum = fastMessage.getInt("MsgSeqNum");
        messageType = fastMessage.getString("MessageType");
        senderCompID = fastMessage.getString("SenderCompID");
        sendingTime = MessageType.FAST_DATETIME_UTC_FORMATTER.parseDateTime(Long.toString(fastMessage.getLong(
                "SendingTime"))).withZone(Constants.MOEX_TIME_ZONE);
        applVerID = fastMessage.getString("ApplVerID");
    }
}
