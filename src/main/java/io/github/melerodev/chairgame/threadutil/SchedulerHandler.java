package io.github.melerodev.chairgame.threadutil;

import io.github.melerodev.chairgame.ChairGame;
import io.github.melerodev.chairgame.Reloadable;
import io.github.milkdrinkers.threadutil.PlatformBukkit;
import io.github.milkdrinkers.threadutil.Scheduler;

import java.time.Duration;

/**
 * A wrapper handler class for handling thread-util lifecycle.
 */
public class SchedulerHandler implements Reloadable {
    @Override
    public void onLoad(ChairGame plugin) {
        Scheduler.init(new PlatformBukkit(plugin)); // Initialize thread-util
    }

    @Override
    public void onEnable(ChairGame plugin) {

    }

    @Override
    public void onDisable(ChairGame plugin) {
        Scheduler.shutdown(Duration.ofSeconds(60));
    }
}
