package io.github.melerodev.chairgame.command;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.milkdrinkers.wordweaver.Translation;
import org.bukkit.command.CommandSender;

public class ChairGameCommand {
    private static final String BASE_PERM = "chairgame.command";

    protected ChairGameCommand() {
        new CommandAPICommand("chairgame")
            .withFullDescription("ChairGame command.")
            .withShortDescription("ChairGame command.")
            .withPermission(BASE_PERM)
            .withSubcommands(
                new ReloadCommand().command(),
                new SpawnCommand().command()
            )
            .executes(this::executorReload)
            .register();
    }

    private void executorReload(CommandSender sender, CommandArguments args) {
        sender.sendMessage(Translation.as("chairgame.errors.no-arguments"));
    }
}
