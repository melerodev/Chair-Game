package io.github.melerodev.chairgame.utility;

import io.github.melerodev.chairgame.ChairGame;
import io.github.melerodev.chairgame.config.ConfigHandler;
import io.github.milkdrinkers.crate.Config;
import org.jetbrains.annotations.NotNull;

/**
 * Convenience class for accessing {@link ConfigHandler#getConfig}
 */
public abstract class Cfg {
    /**
     * Convenience method for {@link ConfigHandler#getConfig} to getConnection {@link Config}
     *
     * @return the config
     */
    @NotNull
    public static Config get() {
        return ChairGame.getInstance().getConfigHandler().getConfig();
    }
}
