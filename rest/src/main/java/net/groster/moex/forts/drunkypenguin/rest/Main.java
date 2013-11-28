package net.groster.moex.forts.drunkypenguin.rest;

import java.io.IOException;
import java.net.URI;
import net.groster.moex.forts.drunkypenguin.core.fast.FASTService;
import net.groster.moex.forts.drunkypenguin.core.fast.config.FASTConfigsUpdatesChecker;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

public abstract class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(final String[] args) throws IOException {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        try {
            final ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("spring-rest.xml");
            context.registerShutdownHook();
            final String restServiceBaseUri = (String) context.getBean("restServiceBaseUri");

            final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(restServiceBaseUri),
                    new ResourceConfig().register(StatusResource.class));

            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    LOGGER.info("Grizzly server with Jersey app is stopping");
                    server.stop();
                    LOGGER.info("Grizzly server with Jersey app was stopped");
                }
            });
            LOGGER.info("Jersey app started with WADL available at " + restServiceBaseUri + "application.wadl");

            context.getBean(FASTConfigsUpdatesChecker.class).start();
            context.getBean(FASTService.class).start();

        } catch (Throwable t) {
            LOGGER.error("All is bad", t);
            throw t;
        }
    }
}
