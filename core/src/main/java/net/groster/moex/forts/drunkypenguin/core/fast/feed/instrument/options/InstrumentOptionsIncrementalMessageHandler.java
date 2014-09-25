package net.groster.moex.forts.drunkypenguin.core.fast.feed.instrument.options;

import net.groster.moex.forts.drunkypenguin.core.fast.MessageType;
import org.openfast.Context;
import org.openfast.Message;
import org.openfast.MessageHandler;
import org.openfast.codec.Coder;

public class InstrumentOptionsIncrementalMessageHandler implements MessageHandler {

    @Override
    public void handleMessage(final Message message, final Context context, final Coder coder) {
        switch (MessageType.getById(message.getInt("templateId"))) {
            case RESET:
                context.reset();
                break;
            case HEARTBEAT:
                break;
            default:
                MessageType.logUnknownFASTMessage(message);
                break;
        }
    }

}
