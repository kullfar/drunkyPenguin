package net.groster.moex.forts.drunkypenguin.core.fast.feed.trade.options;

import net.groster.moex.forts.drunkypenguin.core.fast.MessageType;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.MDEntry;
import net.groster.moex.forts.drunkypenguin.core.fast.domain.msg.DefaultIncrementalRefreshMessage;
import org.openfast.Context;
import org.openfast.Message;
import org.openfast.MessageHandler;
import org.openfast.codec.Coder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TradesOptionsIncrementalMessageHandler implements MessageHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(TradesOptionsIncrementalMessageHandler.class);

    @Override
    public void handleMessage(final Message message, final Context context, final Coder coder) {
        final MessageType messageType = MessageType.getById(message.getInt("templateId"));
        switch (messageType) {
            case RESET:
                context.reset();
                break;
            case DEFAULT_INCREMENTAL_REFRESH_MESSAGE:
                final DefaultIncrementalRefreshMessage m = (DefaultIncrementalRefreshMessage) messageType.parseFASTMessage(message);
                for (final MDEntry mdEntry : m.getMdEntries()) {
                    //TODO: process all types
                    //LOGGER.info(mdEntry.getMdEntryType().toString());
                }
                //TODO: parse whole msg and stop logging it
                //MessageType.logUnknownFASTMessage(message);
                break;
            default:
                MessageType.logUnknownFASTMessage(message);
                break;
        }
    }

}
