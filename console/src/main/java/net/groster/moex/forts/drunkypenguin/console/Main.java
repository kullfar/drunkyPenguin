package net.groster.moex.forts.drunkypenguin.console;

import net.groster.moex.forts.drunkypenguin.core.config.Updater;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public abstract class Main {

    public static void main(final String[] args) {
        final ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("spring-core.xml");
        context.registerShutdownHook();
        context.getBean(Updater.class).start();
    }
}
