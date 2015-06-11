package net.groster.moex.forts.drunkypenguin.core.fast.domain.msg;

import net.groster.moex.forts.drunkypenguin.core.fast.domain.AbstractFASTMessage;
import org.openfast.Message;

public class SequenceReset extends AbstractFASTMessage {

    private final int newSeqNo;

    public SequenceReset(final Message fastMessage) {
        super(fastMessage);
        newSeqNo = fastMessage.getInt("NewSeqNo");
    }
}
