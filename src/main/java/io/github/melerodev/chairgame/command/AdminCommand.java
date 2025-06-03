package io.github.melerodev.chairgame.command;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.melerodev.chairgame.ChairGame;
import io.github.melerodev.chairgame.permission.Permissions;
import io.github.milkdrinkers.wordweaver.Translation;
import org.bukkit.command.CommandSender;

public class AdminCommand {
    private final ChairGame plugin;

    public AdminCommand() {
        this.plugin = ChairGame.getInstance();
    }

    public void register() {
        command().register();
    }

    protected CommandAPICommand command() {
        return new CommandAPICommand("admin")
            .withFullDescription("ChairGame command.")
            .withShortDescription("ChairGame command.")
            .withPermission(Permissions.ADMIN_PERMISSION.getNode())
            .withArguments(
                ArenaSubcommandBuilder.buildArenaSubcommands(plugin)
            )
            .withSubcommands(
                new SetCommand().command()
            )
            .executes(this::executorAdmin);
    }

    private void executorAdmin(CommandSender sender, CommandArguments args) {
        sender.sendMessage(Translation.as("chairgame.help"));
    }
}
