package io.github.melerodev.chairgame;

/**
 * Implemented in classes that should support being reloaded IE executing the methods during runtime after startup.
 */
public interface Reloadable {
    /**
     * On plugin load.
     */
    void onLoad(ChairGame plugin);

    /**
     * On plugin enable.
     */
    void onEnable(ChairGame plugin);

    /**
     * On plugin disable.
     */
    void onDisable(ChairGame plugin);
}
