package io.github.melerodev.chairgame.command;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.melerodev.chairgame.ChairGame;
import io.github.melerodev.chairgame.permission.Permissions;
import io.github.melerodev.chairgame.utility.Cfg;
import io.github.milkdrinkers.crate.Config;
import io.github.milkdrinkers.wordweaver.Translation;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand {
    protected CommandAPICommand command() {
        return new CommandAPICommand("spawn")
            .withFullDescription("Set things")
            .withShortDescription("Set things")
            .withPermission(Permissions.SET_SPAWN_PERMISSION.getNode())
            .withSubcommands(
                commandLobby(),
                commandSpawnPlayer()
            )
            .executes(this::executorSet);
    }

    private void executorSet(CommandSender sender, CommandArguments args) {
        ChairGame.getInstance().reloadConfig();
        sender.sendMessage(Translation.as("chairgame.help"));
    }

    private CommandAPICommand commandLobby() {
        return new CommandAPICommand("lobby")
            .withFullDescription("Set the spawn point for the lobby of game while the game is loading or players are waiting.")
            .withShortDescription("Set the spawn point for the lobby of game while the game is loading or players are waiting.")
            .withPermission(Permissions.SET_SPAWN_LOBBY_PERMISSION.getNode())
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

            sender.sendMessage(Translation.as("chairgame.spawn-lobby"));
        } else {
            sender.sendMessage(Translation.as("chairgame.errors.not-from-console"));
        }
    }

    private CommandAPICommand commandSpawnPlayer() {
        return new CommandAPICommand("player")
            .withFullDescription("Set the spawn point for the game.")
            .withShortDescription("Set the spawn point for the game.")
            .withPermission(Permissions.SET_SPAWN_PLAYER_PERMISSION.getNode())
            .executes(this::executorSetSpawnPlayer);
    }

    private void executorSetSpawnPlayer(CommandSender sender, CommandArguments args) {
        if (sender instanceof Player) {
            sender.sendMessage(Translation.as("chairgame.spawn-player"));
        } else {
            sender.sendMessage(Translation.as("chairgame.errors.not-from-console"));
        }
    }
}
