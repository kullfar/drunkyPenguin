package net.groster.moex.forts.drunkypenguin.core.fast.feed.instrument.options;

import javax.inject.Inject;
import javax.inject.Named;
import net.groster.moex.forts.drunkypenguin.core.fast.MessageType;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.msg.SecurityDefinitionUpdateReport;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.msg.SecurityStatus;
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
public class InstrumentOptionsIncrementalMessageHandler implements MessageHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(InstrumentOptionsIncrementalMessageHandler.class);
    @Inject
    private InstrumentOptionsFastFeed instrumentOptionsFastFeed;

    @Override
    public void handleMessage(final Message message, final Context context, final Coder coder) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(MessageType.buildHumanReadableStringForLoggingFASTMessage(message));
        }
        final MessageType messageType = MessageType.getById(message.getInt("templateId"));
        switch (messageType) {
            case RESET:
                context.reset();
                break;
            case SECURITY_DEFINITION_UPDATE_REPORT:
                instrumentOptionsFastFeed.onSecurityDefinitionUpdateReport((SecurityDefinitionUpdateReport) messageType.parseFASTMessage(message));
                break;
            case HEARTBEAT:
                break;
            case SECURITY_STATUS:
                instrumentOptionsFastFeed.onSecurityStatus((SecurityStatus) messageType.parseFASTMessage(message));
                break;
            default:
                LOGGER.warn(MessageType.buildHumanReadableStringForLoggingFASTMessage(message));
                break;
        }
    }

}
