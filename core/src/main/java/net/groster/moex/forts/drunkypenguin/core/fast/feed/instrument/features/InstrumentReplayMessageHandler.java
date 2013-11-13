package net.groster.moex.forts.drunkypenguin.core.fast.feed.instrument.features;

import net.groster.moex.forts.drunkypenguin.core.fast.MessageType;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.SecurityDefinition;
import org.openfast.Context;
import org.openfast.Message;
import org.openfast.MessageHandler;
import org.openfast.codec.Coder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InstrumentReplayMessageHandler implements MessageHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(InstrumentReplayMessageHandler.class);

    @Override
    public void handleMessage(final Message message, final Context context, final Coder coder) {
        final MessageType messageType = MessageType.getById(message.getInt("templateId"));
        switch (messageType) {
            case RESET:
                context.reset();
                break;
            case SECURITY_DEFINITION:
                final SecurityDefinition sd = (SecurityDefinition) messageType.parseFASTMessage(message);
                LOGGER.info("got another one SecurityDefinition");
                break;
            default:
                MessageType.logUnknownFASTMessage(message);
                break;
        }
    }

}
