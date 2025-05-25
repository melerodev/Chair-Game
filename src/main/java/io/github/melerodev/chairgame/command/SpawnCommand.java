package io.github.melerodev.chairgame.command;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.melerodev.chairgame.ChairGame;
import io.github.melerodev.chairgame.utility.Cfg;
import io.github.milkdrinkers.crate.Config;
import io.github.milkdrinkers.wordweaver.Translation;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpawnCommand {
    private static final String BASE_PERM = "chairgame.command";

    protected CommandAPICommand command() {

        return new CommandAPICommand("spawn")
            .withFullDescription("Set the spawn point things for plugin.")
            .withShortDescription("Set the spawn point things for plugin.")
            .withPermission(BASE_PERM + ".spawn")
            .withSubcommands(
                commandLobby()
            )
            .executes(this::executeSpawn);
    }

    private void executeSpawn(CommandSender sender, CommandArguments args) {
        ChairGame.getInstance().reloadConfig();
        sender.sendMessage(Translation.as("commands.chairgame.errors.no-arguments"));
    }

    private CommandAPICommand commandLobby() {
        return new CommandAPICommand("lobby")
            .withFullDescription("Set the spawn point for the lobby of game while the game is loading or players are waiting.")
            .withShortDescription("Set the spawn point for the lobby of game while the game is loading or players are waiting.")
            .withPermission(BASE_PERM + ".lobby")
            .executes(this::executorSetSpawnLobby);
    }

    private void executorSetSpawnLobby(CommandSender sender, CommandArguments args) {
        if (sender instanceof Player) {
            Config config = Cfg.get();

            config.set("lobby-spawn-location.world", ((Player) sender).getWorld().getName());
            config.set("lobby-spawn-location.x", ((Player) sender).getLocation().getX());
            config.set("lobby-spawn-location.y", ((Player) sender).getLocation().getY());
            config.set("lobby-spawn-location.z", ((Player) sender).getLocation().getZ());
            config.set("lobby-spawn-location.yaw", ((Player) sender).getLocation().getYaw());
            config.set("lobby-spawn-location.pitch", ((Player) sender).getLocation().getPitch());

            sender.sendMessage(Translation.as("commands.chairgame.spawn-lobby"));
        } else {
            sender.sendMessage(Translation.as("commands.chairgame.errors.not-from-console"));
        }
    }
}
