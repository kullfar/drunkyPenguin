package net.groster.moex.forts.drunkypenguin.core.fast.feed.instrument.features;

import org.openfast.Context;
import org.openfast.Message;
import org.openfast.MessageHandler;
import org.openfast.codec.Coder;

public class InstrumentIncrementalMessageHandler implements MessageHandler {

    @Override
    public void handleMessage(final Message readMessage, final Context context, final Coder coder) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
