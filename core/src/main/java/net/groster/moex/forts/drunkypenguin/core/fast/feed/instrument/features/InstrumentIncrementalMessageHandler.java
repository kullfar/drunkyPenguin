package net.groster.moex.forts.drunkypenguin.core.fast.feed.instrument.features;

import net.groster.moex.forts.drunkypenguin.core.fast.MessageType;
import org.openfast.Context;
import org.openfast.Message;
import org.openfast.MessageHandler;
import org.openfast.codec.Coder;

public class InstrumentIncrementalMessageHandler implements MessageHandler {

    @Override
    public void handleMessage(final Message message, final Context context, final Coder coder) {
        switch (MessageType.getById(message.getInt("templateId"))) {
            default:
                MessageType.logUnknownFASTMessage(message);
                break;
        }
    }

}
