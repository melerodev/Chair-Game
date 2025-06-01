package io.github.melerodev.chairgame.command;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.melerodev.chairgame.ChairGame;
import io.github.melerodev.chairgame.arena.Arena;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class ArenaSubcommandBuilder {
    public static List<CommandAPICommand> buildArenaSubcommands() {
        List<Arena> arenas = ChairGame.getInstance().getArenaHandler().getArenas();
        List<CommandAPICommand> arenaSubcommands = new ArrayList<>();

        for (Arena arena : arenas) {
            arenaSubcommands.add(
                new CommandAPICommand(arena.getName())
                    .withSubcommands(
                        new SetCommand().command()
                    )
            );
        }
        return arenaSubcommands;
    }

    public static List<String> suggestArenas(CommandSender sender, CommandArguments args) {
        return ChairGame.getInstance().getArenaHandler().getArenas()
            .stream()
            .map(Arena::getName)
            .toList();
    }
}
