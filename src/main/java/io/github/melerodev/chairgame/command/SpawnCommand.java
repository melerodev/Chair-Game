package io.github.melerodev.chairgame.command;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.melerodev.chairgame.ChairGame;
import io.github.melerodev.chairgame.command.spawn.CommandSetSpawnLobby;
import io.github.melerodev.chairgame.command.spawn.CommandSetSpawnPlayer;
import io.github.melerodev.chairgame.permission.Permissions;
import io.github.milkdrinkers.wordweaver.Translation;
import org.bukkit.command.CommandSender;

public class SpawnCommand {
    private final ChairGame plugin;

    public SpawnCommand() {
        this.plugin = ChairGame.getInstance();
    }

    protected CommandAPICommand command() {
        return new CommandAPICommand("spawn")
            .withFullDescription("Set things")
            .withShortDescription("Set things")
            .withPermission(Permissions.SET_SPAWN_PERMISSION.getNode())
            .withSubcommands(
                new CommandSetSpawnLobby().command(),
                new CommandSetSpawnPlayer().command()
            )
            .executes(this::executorSet);
    }

    private void executorSet(CommandSender sender, CommandArguments args) {
        ChairGame.getInstance().reloadConfig();
        sender.sendMessage(Translation.as("chairgame.help"));
    }
}