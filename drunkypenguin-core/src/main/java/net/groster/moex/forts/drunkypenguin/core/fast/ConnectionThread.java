package net.groster.moex.forts.drunkypenguin.core.fast;

import java.io.IOException;
import java.net.SocketTimeoutException;
import javax.inject.Inject;
import javax.inject.Named;
import org.openfast.MessageHandler;
import org.openfast.MessageInputStream;
import org.openfast.session.Connection;
import org.openfast.session.FastConnectionException;
import org.openfast.session.multicast.MulticastClientEndpoint;
import org.openfast.template.MessageTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;

@Named
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ConnectionThread extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionThread.class);
    private volatile boolean work = true;
    private MulticastClientEndpoint multicastClientEndpoint;
    private MessageHandler messageHandler;
    @Inject
    private FASTService fastService;

    public void init(final String name, final MulticastClientEndpoint multicastClientEndpoint,
            final MessageHandler messageHandler) {
        setName(name);
        this.multicastClientEndpoint = multicastClientEndpoint;
        this.messageHandler = messageHandler;
    }

    @Override
    public void run() {
        LOGGER.info("starting.");
        while (work) {
            boolean wasConnected = false;
            MessageInputStream messageIn = null;
            Connection fastConnection = null;
            try {
                LOGGER.info("connecting.");
                fastConnection = multicastClientEndpoint.connect();
                messageIn = new MessageInputStream(fastConnection.getInputStream());
                for (final MessageTemplate template : fastService.getTemplates()) {
                    messageIn.registerTemplate(Integer.parseInt(template.getId()), template);
                }
                messageIn.addMessageHandler(messageHandler);
                messageIn.readMessage();
                LOGGER.info("connected.");
                wasConnected = true;
                while (work) {
                    messageIn.readMessage();
                }
            } catch (RuntimeException rE) {
                if (rE.getCause() instanceof SocketTimeoutException) {
                    LOGGER.warn("socket timeout exception. Will try to reconnect");
                } else {
                    LOGGER.error("Error, while working", rE);
                    work = false;
                }
            } catch (FastConnectionException | IOException ex) {
                LOGGER.error("Error connecting", ex);
            } finally {
                if (messageIn != null) {
                    try {
                        messageIn.close();
                    } catch (Exception e) {
                    }
                }
                if (fastConnection != null) {
                    try {
                        fastConnection.close();
                    } catch (Exception e) {
                    }
                }
            }
            if (wasConnected) {
                if (work) {
                    LOGGER.warn("disconnected");
                } else {
                    LOGGER.info("disconnected");
                }
            }
        }
        LOGGER.info("stopped");
    }

    public void stopConnection() {
        work = false;
        interrupt();
    }
}
