package io.github.melerodev.chairgame.command;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.melerodev.chairgame.ChairGame;
import io.github.melerodev.chairgame.Reloadable;
import io.github.melerodev.chairgame.arena.Arena;
import io.github.melerodev.chairgame.permission.Permissions;
import io.github.milkdrinkers.crate.Config;
import io.github.milkdrinkers.wordweaver.Translation;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Optional;

/**
 * A class to handle registration of commands.
 */
public class CommandHandler implements Reloadable {
    private final ChairGame plugin;

    /**
     * Instantiates the Command handler.
     *
     * @param plugin the plugin
     */
    public CommandHandler(ChairGame plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onLoad(ChairGame plugin) {
        CommandAPI.onLoad(
            new CommandAPIBukkitConfig(plugin)
                .shouldHookPaperReload(true)
                .silentLogs(true)
                .usePluginNamespace()
                .beLenientForMinorVersions(true)
        );
    }

    @Override
    public void onEnable(ChairGame plugin) {
        CommandAPI.onEnable();

        // Register commands here
        new ExampleCommand();
        new ChairGameCommand();
    }

    @Override
    public void onDisable(ChairGame plugin) {
        CommandAPI.getRegisteredCommands().forEach(registeredCommand -> CommandAPI.unregister(registeredCommand.namespace() + ':' + registeredCommand.commandName(), true));
        CommandAPI.onDisable();
    }
}