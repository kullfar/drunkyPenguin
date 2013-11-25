package net.groster.moex.forts.drunkypenguin.core.fast.domain;

import org.joda.time.DateTime;

public class AbstractFASTMessage {

    private int msgSeqNum;
    private String messageType;
    private String senderCompID;
    private DateTime sendingTime;
    private String applVerID;

    public void setMsgSeqNum(final int msgSeqNum) {
        this.msgSeqNum = msgSeqNum;
    }

    public void setMessageType(final String messageType) {
        this.messageType = messageType;
    }

    public void setSenderCompID(final String senderCompID) {
        this.senderCompID = senderCompID;
    }

    public void setSendingTime(final DateTime sendingTime) {
        this.sendingTime = sendingTime;
    }

    public void setApplVerID(final String applVerID) {
        this.applVerID = applVerID;
    }

}
