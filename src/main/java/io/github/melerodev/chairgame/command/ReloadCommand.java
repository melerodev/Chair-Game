package io.github.melerodev.chairgame.command;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.melerodev.chairgame.ChairGame;
import io.github.milkdrinkers.wordweaver.Translation;
import org.bukkit.command.CommandSender;

public class ReloadCommand {
    private static final String BASE_PERM = "chairgame.command";

    protected CommandAPICommand command() {
        return new CommandAPICommand("reload")
            .withFullDescription("Reload all config.")
            .withShortDescription("Reload all config.")
            .withPermission(BASE_PERM + ".reload")
            .executes(this::executorReload);
    }

    private void executorReload(CommandSender sender, CommandArguments args) {
        ChairGame.getInstance().reloadConfig();
        sender.sendMessage(Translation.as("chairgame.reloaded"));
    }
}
