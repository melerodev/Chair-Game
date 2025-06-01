package io.github.melerodev.chairgame.command;

import dev.jorel.commandapi.CommandAPICommand;
import io.github.melerodev.chairgame.ChairGame;
import io.github.melerodev.chairgame.arena.Arena;
import io.github.melerodev.chairgame.permission.Permissions;

import java.util.ArrayList;
import java.util.List;

public class ArenasCommand {
    public static List<CommandAPICommand> buildArenaSubcommands() {
        List<CommandAPICommand> arenaSubcommands = new ArrayList<>();

        for (Arena arena : ChairGame.getInstance().getArenaManager().getArenas()) {
            arenaSubcommands.add(
                new CommandAPICommand(arena.getName())
                    .withSubcommands(
                        new SetCommand().command()
                    )
            );
        }

        return arenaSubcommands;
    }
}
