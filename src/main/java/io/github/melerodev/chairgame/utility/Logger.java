package io.github.melerodev.chairgame.utility;


import io.github.melerodev.chairgame.ChairGame;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.jetbrains.annotations.NotNull;

/**
 * A class that provides shorthand access to {@link ChairGame#getComponentLogger}.
 */
public class Logger {
    /**
     * Get component logger. Shorthand for:
     *
     * @return the component logger {@link ChairGame#getComponentLogger}.
     */
    @NotNull
    public static ComponentLogger get() {
        return ChairGame.getInstance().getComponentLogger();
    }
}
