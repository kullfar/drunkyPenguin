package net.groster.moex.forts.drunkypenguin.core.fast.feed.instrument.features;

import javax.inject.Inject;
import javax.inject.Named;
import net.groster.moex.forts.drunkypenguin.core.fast.MessageType;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.SecurityDefinition;
import org.openfast.Context;
import org.openfast.Message;
import org.openfast.MessageHandler;
import org.openfast.codec.Coder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;

@Named
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class InstrumentReplayMessageHandler implements MessageHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(InstrumentReplayMessageHandler.class);

    @Inject
    private InstrumentFuturesFastFeed instrumentFuturesFastFeed;

    @Override
    public void handleMessage(final Message message, final Context context, final Coder coder) {
        final MessageType messageType = MessageType.getById(message.getInt("templateId"));
        switch (messageType) {
            case RESET:
                context.reset();
                break;
            case SECURITY_DEFINITION:
                instrumentFuturesFastFeed.onNewReplaySecurityDefinition((SecurityDefinition) messageType.parseFASTMessage(message));
                break;
            case SEQUENCE_RESET:
                break;
            default:
                MessageType.logUnknownFASTMessage(message);
                break;
        }
    }

}
