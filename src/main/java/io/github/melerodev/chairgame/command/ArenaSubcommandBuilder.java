package io.github.melerodev.chairgame.command;

import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.StringArgument;
import io.github.melerodev.chairgame.ChairGame;
import io.github.melerodev.chairgame.arena.Arena;

public class ArenaSubcommandBuilder {
    public static Argument<String> buildArenaSubcommands(ChairGame plugin) {
        return new StringArgument("arena")
            .replaceSuggestions(ArgumentSuggestions.stringCollection(unused ->
                plugin.getArenaHandler().getArenas().stream()
                    .map(Arena::getName)
                    .toList()
            ));
    }
}
