package net.groster.moex.forts.drunkypenguin.core.fast.feed.instrument.features;

import javax.inject.Inject;
import javax.inject.Named;
import net.groster.moex.forts.drunkypenguin.core.fast.MessageType;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.msg.SecurityStatus;
import org.openfast.Context;
import org.openfast.Message;
import org.openfast.MessageHandler;
import org.openfast.codec.Coder;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;

@Named
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class InstrumentFuturesIncrementalMessageHandler implements MessageHandler {

    @Inject
    private InstrumentFuturesFastFeed instrumentFuturesFastFeed;

    @Override
    public void handleMessage(final Message message, final Context context, final Coder coder) {
        final MessageType messageType = MessageType.getById(message.getInt("templateId"));
        switch (messageType) {
            case RESET:
                context.reset();
                break;
            case HEARTBEAT:
                break;
            case SECURITY_STATUS:
                instrumentFuturesFastFeed.onSecurityStatus((SecurityStatus) messageType.parseFASTMessage(message));
                break;
            default:
                MessageType.logUnknownFASTMessage(message);
                break;
        }
    }

}
