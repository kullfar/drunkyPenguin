package net.groster.moex.forts.drunkypenguin.core.fast.domain;

import net.groster.moex.forts.drunkypenguin.core.Constants;
import net.groster.moex.forts.drunkypenguin.core.fast.MessageType;
import org.joda.time.DateTime;
import org.openfast.Message;

public class AbstractFASTMessage {

    private int msgSeqNum;
    private String messageType;
    private String senderCompID;
    private DateTime sendingTime;
    private String applVerID;

    public void init(final Message fastMessage) {
        msgSeqNum = fastMessage.getInt("MsgSeqNum");
        messageType = fastMessage.getString("MessageType");
        senderCompID = fastMessage.getString("SenderCompID");
        sendingTime = MessageType.FAST_DATETIME_UTC_FORMATTER.parseDateTime(Long.toString(fastMessage.getLong(
                "SendingTime"))).withZone(Constants.MOEX_TIME_ZONE);
        applVerID = fastMessage.getString("ApplVerID");
    }
}
