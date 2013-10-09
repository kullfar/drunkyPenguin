package net.groster.moex.forts.drunkypenguin.console;

import net.groster.moex.forts.drunkypenguin.core.config.Updater;

public abstract class Main {

    public static void main(final String[] args) {
        new Thread(new Updater()).start();
    }
}
