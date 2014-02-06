package net.groster.moex.forts.drunkypenguin.core.fast.domain;

import org.openfast.Message;

public class Heartbeat extends AbstractFASTMessage {

    public Heartbeat(final Message fastMessage) {
        super(fastMessage);
    }
}
