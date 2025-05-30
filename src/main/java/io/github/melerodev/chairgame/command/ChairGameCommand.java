package io.github.melerodev.chairgame.command;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.melerodev.chairgame.permission.Permissions;
import io.github.milkdrinkers.wordweaver.Translation;
import org.bukkit.command.CommandSender;

public class ChairGameCommand {
    protected ChairGameCommand() {
        new CommandAPICommand("chairgame")
            .withFullDescription("ChairGame command.")
            .withShortDescription("ChairGame command.")
            .withPermission(Permissions.BASE_PERMISSION.getNode())
            .withSubcommands(
                new ReloadCommand().command(),
                new AdminCommand().command()
            )
            .withAliases("cg", "chairgame")
            .executes(this::executorReload)
            .register();
    }

    private void executorReload(CommandSender sender, CommandArguments args) {
        sender.sendMessage(Translation.as("chairgame.help"));
    }
}
