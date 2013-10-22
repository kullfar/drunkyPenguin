package net.groster.moex.forts.drunkypenguin.console;

import net.groster.moex.forts.drunkypenguin.core.fast.config.Checker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public abstract class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(final String[] args) {
        try {
            final ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("spring-core.xml");
            context.registerShutdownHook();
            context.getBean(Checker.class).start();
        } catch (Throwable t) {
            LOGGER.error("All is bad", t);
            throw t;
        }
    }
}
