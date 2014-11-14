package net.groster.moex.forts.drunkypenguin.rest;

import java.io.IOException;
import java.net.URI;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import net.groster.moex.forts.drunkypenguin.core.fast.FASTService;
import net.groster.moex.forts.drunkypenguin.core.fast.config.FASTConfigsUpdatesChecker;
import net.groster.moex.forts.drunkypenguin.rest.status.StatusResource;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpContainer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.context.ConfigurableApplicationContext;

@Named
@Singleton
public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    @Resource
    private String restServiceBaseUri;
    private static String staticRestServiceBaseUri;
    @Inject
    private ConfigurableApplicationContext springContext;
    private static ConfigurableApplicationContext staticSpringContext;

    @PostConstruct
    public void postConstruct() {
        staticRestServiceBaseUri = restServiceBaseUri;
        staticSpringContext = springContext;
    }

    public static void main(final String... args) throws IOException {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        try {
            final ResourceConfig resourceConfig = new ResourceConfig().register(StatusResource.class).property(
                    "contextConfigLocation", "spring-rest.xml");
            final GrizzlyHttpContainer grizzlyHttpContainer = ContainerFactory.createContainer(
                    GrizzlyHttpContainer.class, resourceConfig);
            staticSpringContext.registerShutdownHook();

            final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(staticRestServiceBaseUri),
                    grizzlyHttpContainer, false, null, true);

            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    LOGGER.info("Grizzly server with Jersey app is stopping");
                    server.shutdownNow();
                    LOGGER.info("Grizzly server with Jersey app was stopped");
                }
            });
            LOGGER.info("Jersey app started with WADL available at " + staticRestServiceBaseUri + "application.wadl");

            LOGGER.info("Starting FAST threads");
            staticSpringContext.getBean(FASTConfigsUpdatesChecker.class).start();
            staticSpringContext.getBean(FASTService.class).start();
            LOGGER.info("Started FAST threads");

        } catch (Throwable t) {
            LOGGER.error("All is bad", t);
            throw t;
        }
    }
}
