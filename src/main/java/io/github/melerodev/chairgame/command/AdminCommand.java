package io.github.melerodev.chairgame.command;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.melerodev.chairgame.permission.Permissions;
import io.github.milkdrinkers.wordweaver.Translation;
import org.bukkit.command.CommandSender;

public class AdminCommand {
    protected CommandAPICommand command() {
        return new CommandAPICommand("chairgame")
            .withFullDescription("ChairGame command.")
            .withShortDescription("ChairGame command.")
            .withPermission(Permissions.ADMIN_PERMISSION.getNode())
            .withSubcommands(
                new SetCommand().command()
            )
            .withAliases("cg", "chairgame")
            .executes(this::executorAdmin);
    }

    private void executorAdmin(CommandSender sender, CommandArguments args) {
        sender.sendMessage(Translation.as("chairgame.help"));
    }
}
