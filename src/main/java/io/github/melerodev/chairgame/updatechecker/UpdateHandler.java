package io.github.melerodev.chairgame.updatechecker;

import io.github.melerodev.chairgame.ChairGame;
import io.github.melerodev.chairgame.Reloadable;
import io.github.melerodev.chairgame.utility.Cfg;
import io.github.melerodev.chairgame.utility.Logger;
import io.github.milkdrinkers.colorparser.ColorParser;
import io.github.milkdrinkers.javasemver.Version;
import io.github.milkdrinkers.javasemver.exception.VersionParseException;
import io.github.milkdrinkers.versionwatch.Platform;
import io.github.milkdrinkers.versionwatch.VersionWatcher;
import io.github.milkdrinkers.wordweaver.Translation;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Run update checks against your release platform.
 */
public class UpdateHandler implements Reloadable {
    private final static String GITHUB_USER = "melerodev"; // The GitHub user/organization name
    private final static String GITHUB_REPO = "Char-Game"; // The GitHub repository

    private final VersionWatcher watcher;

    public UpdateHandler(ChairGame plugin) {
        this.watcher = VersionWatcher.builder()
            .withPlatform(Platform.GitHub)
            .withVersion(getCurrentVersion(plugin))
            .withResourceOwner(GITHUB_USER)
            .withResourceSlug(GITHUB_REPO)
            .withAgent(plugin.getName() + getCurrentVersion(plugin))
            .build();
    }

    /**
     * On plugin load.
     */
    @Override
    public void onLoad(ChairGame plugin) {
    }

    /**
     * On plugin enable.
     */
    @Override
    public void onEnable(ChairGame plugin) {
        final boolean shouldLog = Cfg.get().getOrDefault("update-checker.enable", true) && Cfg.get().getOrDefault("update-checker.console", true);

        // Fetch the latest version and send message to console
        watcher.fetchLatestAsync().thenAccept(version -> {
            if (version == null)
                return;

            if (!shouldLog)
                return;

            if (watcher.isLatest()) {
                Logger.get().info(
                    ColorParser.of(Translation.of("plugin.update-checker.running-latest"))
                        .parseMinimessagePlaceholder("plugin_name", plugin.getName())
                        .build()
                );
            } else {
                Logger.get().info(
                    ColorParser.of(Translation.of("plugin.update-checker.update-found-console"))
                        .parseMinimessagePlaceholder("plugin_name", plugin.getName())
                        .parseMinimessagePlaceholder("version_current", watcher.getCurrentVersion().getVersionFull())
                        .parseMinimessagePlaceholder("version_latest", version.getVersionFull())
                        .parseMinimessagePlaceholder("download_link", watcher.getDownloadURL())
                        .build()
                );
            }
        }).exceptionally(throwable -> {
            if (shouldLog)
                Logger.get().warn(ColorParser.of(Translation.of("plugin.update-checker.update-failed")).parseMinimessagePlaceholder("error", throwable.getMessage()).build());
            return null;
        });

        // Register version check message listener for opped player joins
        plugin.getServer().getPluginManager().registerEvents(new Listener() {
            @EventHandler
            @SuppressWarnings("unused")
            public void onPlayerJoin(PlayerJoinEvent e) {
                final Player p = e.getPlayer();

                if (watcher.isLatest())
                    return;

                if (!Cfg.get().getOrDefault("update-checker.enable", true) || !Cfg.get().getOrDefault("update-checker.op", true))
                    return;

                if (!p.isOp())
                    return;

                if (watcher.getLatestVersion() == null)
                    return;

                p.sendMessage(
                    ColorParser.of(Translation.of("plugin.update-checker.update-found-player"))
                        .parseMinimessagePlaceholder("plugin_name", plugin.getName())
                        .parseMinimessagePlaceholder("version_current", watcher.getCurrentVersion().getVersionFull())
                        .parseMinimessagePlaceholder("version_latest", watcher.getLatestVersion().getVersionFull())
                        .parseMinimessagePlaceholder("download_link", watcher.getDownloadURL())
                        .build()
                );
            }
        }, plugin);
    }

    /**
     * On plugin disable.
     */
    @Override
    public void onDisable(ChairGame plugin) {
    }

    /**
     * Get the current version of the plugin or 0.0.1 if it can't be found.
     *
     * @param plugin the plugin instance
     * @return the current version of the plugin
     */
    @SuppressWarnings("UnstableApiUsage")
    private Version getCurrentVersion(ChairGame plugin) {
        try {
            return Version.of(plugin.getPluginMeta().getVersion());
        } catch (VersionParseException e) {
            return Version.of(0, 0, 1, "", "");
        }
    }
}
