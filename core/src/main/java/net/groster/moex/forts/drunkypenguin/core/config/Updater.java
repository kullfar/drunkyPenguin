package net.groster.moex.forts.drunkypenguin.core.config;

import java.io.File;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.inject.Named;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@Singleton
public class Updater extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(Updater.class);
    private volatile boolean continueWorking = true;
    @Resource
    private String fastConfPath;
    @Resource
    private Updater updater;

    @Override
    public void run() {
        while (continueWorking) {
            LOGGER.info("I was here and {'fastConfPath'='" + fastConfPath + "'}.");
            LOGGER.info("isFile=" + new File("configuration.xml").isFile());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException iE) {
            }
        }
    }

    @PreDestroy
    public void preDestroy() {
        continueWorking = false;
        updater.interrupt();
    }
}
