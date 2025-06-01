package io.github.melerodev.chairgame;

import io.github.melerodev.chairgame.arena.ArenaHandler;
import io.github.melerodev.chairgame.command.CommandHandler;
import io.github.melerodev.chairgame.config.ConfigHandler;
import io.github.melerodev.chairgame.database.handler.DatabaseHandler;
import io.github.melerodev.chairgame.database.handler.DatabaseHandlerBuilder;
import io.github.melerodev.chairgame.hook.HookManager;
import io.github.melerodev.chairgame.listener.ListenerHandler;
import io.github.melerodev.chairgame.threadutil.SchedulerHandler;
import io.github.melerodev.chairgame.translation.TranslationHandler;
import io.github.melerodev.chairgame.updatechecker.UpdateHandler;
import io.github.melerodev.chairgame.utility.DB;
import io.github.melerodev.chairgame.utility.Logger;
import io.github.milkdrinkers.colorparser.ColorParser;
import io.github.milkdrinkers.wordweaver.Translation;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Main class.
 */
@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class ChairGame extends JavaPlugin {
    private static ChairGame instance;

    // Handlers/Managers
    private ConfigHandler configHandler;
    private TranslationHandler translationHandler;
    private DatabaseHandler databaseHandler;
    private HookManager hookManager;
    private CommandHandler commandHandler;
    private ListenerHandler listenerHandler;
    private UpdateHandler updateHandler;
    private SchedulerHandler schedulerHandler;
    private ArenaHandler arenaHandler;

    // Handlers list (defines order of load/enable/disable)
    private List<? extends Reloadable> handlers;

    /**
     * Gets plugin instance.
     *
     * @return the plugin instance
     */
    public static ChairGame getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        instance = this;
        configHandler = new ConfigHandler(this);
        translationHandler = new TranslationHandler(configHandler);
        databaseHandler = new DatabaseHandlerBuilder()
            .withConfigHandler(configHandler)
            .withLogger(getComponentLogger())
            .build();
        hookManager = new HookManager(this);
        commandHandler = new CommandHandler(this);
        listenerHandler = new ListenerHandler(this);
        updateHandler = new UpdateHandler(this);
        schedulerHandler = new SchedulerHandler();
        arenaHandler = new ArenaHandler(this);

        handlers = List.of(
            configHandler,
            translationHandler,
            databaseHandler,
            hookManager,
            commandHandler,
            listenerHandler,
            updateHandler,
            schedulerHandler,
            arenaHandler
        );

        DB.init(databaseHandler);
        for (Reloadable handler : handlers)
            handler.onLoad(instance);
    }

    @Override
    public void onEnable() {
        for (Reloadable handler : handlers)
            handler.onEnable(instance);

        if (!DB.isReady()) {
            Logger.get().warn(ColorParser.of("<yellow>DatabaseHolder handler failed to start. Database support has been disabled.").build());
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        for (Reloadable handler : handlers.reversed()) // If reverse doesn't work implement a new List with your desired disable order
            handler.onDisable(instance);
    }

    /**
     * Use to reload the entire plugin.
     */
    public void onReload() {
        onDisable();
        onLoad();
        onEnable();
    }

    public void reloadConfig() {
        configHandler.onLoad(instance);
        Translation.reload();
    }

    /**
     * Gets config handler.
     *
     * @return the config handler
     */
    @NotNull
    public ConfigHandler getConfigHandler() {
        return configHandler;
    }

    /**
     * Gets config handler.
     *
     * @return the config handler
     */
    @NotNull
    public ArenaHandler getArenaManager() {
        return arenaHandler;
    }

    /**
     * Gets hook manager.
     *
     * @return the hook manager
     */
    @NotNull
    public HookManager getHookManager() {
        return hookManager;
    }

    /**
     * Gets update handler.
     *
     * @return the update handler
     */
    @NotNull
    public UpdateHandler getUpdateHandler() {
        return updateHandler;
    }
}
