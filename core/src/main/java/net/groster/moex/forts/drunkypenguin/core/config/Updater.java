package net.groster.moex.forts.drunkypenguin.core.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Updater implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Updater.class);
    private boolean continueWorking = true;

    @Override
    public void run() {
        while (continueWorking) {
            LOGGER.info("I was here");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException iE) {
                continueWorking = false;
            }
        }
    }
}
