package io.github.melerodev.chairgame.command;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.melerodev.chairgame.permission.Permissions;
import io.github.milkdrinkers.wordweaver.Translation;
import org.bukkit.command.CommandSender;

public class SetCommand {

    protected CommandAPICommand command() {
        return new CommandAPICommand("set")
            .withFullDescription("Set things")
            .withShortDescription("Set things")
            .withPermission(Permissions.SET_PERMISSION.getNode())
            .withSubcommands(
                new SpawnCommand().command()
            )
            .executes(this::executorSet);
    }

    private void executorSet(CommandSender sender, CommandArguments args) {
        sender.sendMessage(Translation.as("chairgame.help"));
    }
}