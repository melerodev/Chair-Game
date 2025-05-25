package io.github.melerodev.chairgame.config;

import io.github.melerodev.chairgame.ChairGame;
import io.github.melerodev.chairgame.Reloadable;
import io.github.milkdrinkers.crate.Config;

/**
 * A class that generates/loads {@literal &} provides access to a configuration file.
 */
public class ConfigHandler implements Reloadable {
    private final ChairGame plugin;
    private Config cfg;
    private Config databaseCfg;

    /**
     * Instantiates a new Config handler.
     *
     * @param plugin the plugin instance
     */
    public ConfigHandler(ChairGame plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onLoad(ChairGame plugin) {
        cfg = new Config("config", plugin.getDataFolder().getPath(), plugin.getResource("config.yml")); // Create a config file from the template in our resources folder
        databaseCfg = new Config("database", plugin.getDataFolder().getPath(), plugin.getResource("database.yml"));
    }

    @Override
    public void onEnable(ChairGame plugin) {
    }

    @Override
    public void onDisable(ChairGame plugin) {
    }

    /**
     * Gets main config object.
     *
     * @return the config object
     */
    public Config getConfig() {
        return cfg;
    }

    /**
     * Gets database config object.
     *
     * @return the config object
     */
    public Config getDatabaseConfig() {
        return databaseCfg;
    }
}
