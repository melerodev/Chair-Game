package io.github.melerodev.chairgame.command;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.melerodev.chairgame.ChairGame;
import io.github.melerodev.chairgame.arena.Arena;
import io.github.melerodev.chairgame.permission.Permissions;
import io.github.milkdrinkers.wordweaver.Translation;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class AdminCommand {
    protected CommandAPICommand command() {
        List<CommandAPICommand> arenaSubcommands = new ArrayList<>();

        for (Arena arena : ChairGame.getInstance().getArenaManager().getArenas()) {
            arenaSubcommands.add(
                new CommandAPICommand(arena.getName())
                    .withSubcommands(
                        new SetCommand().command()
                    )
            );
        }

        return new CommandAPICommand("admin")
            .withFullDescription("ChairGame command.")
            .withShortDescription("ChairGame command.")
            .withPermission(Permissions.ADMIN_PERMISSION.getNode())
            .withSubcommands(
                arenaSubcommands.toArray(new CommandAPICommand[0])
            )
            .executes(this::executorAdmin);
    }

    private void executorAdmin(CommandSender sender, CommandArguments args) {
        sender.sendMessage(Translation.as("chairgame.help"));
    }
}
