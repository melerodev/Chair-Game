package io.github.melerodev.chairgame.listener;

import io.github.melerodev.chairgame.ChairGame;
import io.github.melerodev.chairgame.Reloadable;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to handle registration of event listeners.
 */
public class ListenerHandler implements Reloadable {
    private final ChairGame plugin;
    private final List<Listener> listeners = new ArrayList<>();

    /**
     * Instantiates a the Listener handler.
     *
     * @param plugin the plugin instance
     */
    public ListenerHandler(ChairGame plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onLoad(ChairGame plugin) {
    }

    @Override
    public void onEnable(ChairGame plugin) {
        listeners.clear(); // Clear the list to avoid duplicate listeners when reloading the plugin
//        listeners.add(new ExampleListener());
        listeners.add(new ListenerSignInteract());
        listeners.add(new ListenerSignChange());

        // Register listeners here
        for (Listener listener : listeners) {
            plugin.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }

    @Override
    public void onDisable(ChairGame plugin) {
    }
}
