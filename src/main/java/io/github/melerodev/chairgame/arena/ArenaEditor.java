package io.github.melerodev.chairgame.arena;

import io.github.melerodev.chairgame.api.LocationFormatOptions;
import io.github.milkdrinkers.crate.Config;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;
import java.util.Objects;

import static io.github.melerodev.chairgame.arena.Arena.DEFAULT_MAX_PLAYERS;
import static io.github.melerodev.chairgame.arena.Arena.DEFAULT_MIN_PLAYERS;

public class ArenaEditor {
    private final File file;
    private final Config config;
    private Arena arena;

    public ArenaEditor(@NotNull String arenaName, @NotNull ArenaRepository repository, @NotNull Arena arena) {
        this.file = new File(repository.getFolder(), arenaName + ArenaRepository.ARENA_FILE_EXTENSION);
        if (!file.exists()) throw new IllegalArgumentException("La arena '" + arenaName + "' no existe.");
        this.config = new Config(file);
        this.arena = arena;
    }

    public ArenaEditor setMinPlayers(int min) {
        if (min < DEFAULT_MIN_PLAYERS) throw new IllegalArgumentException("El número mínimo de jugadores no puede ser menor que " + DEFAULT_MIN_PLAYERS + ".");

        config.set("minPlayers", min);
        return this;
    }

    public ArenaEditor setMaxPlayers(int max) {
        if (max < DEFAULT_MIN_PLAYERS) throw new IllegalArgumentException("El número máximo de jugadores no puede ser menor que el mínimo de jugadores (" + DEFAULT_MIN_PLAYERS + ").");
        if (max > DEFAULT_MAX_PLAYERS) throw new IllegalArgumentException("El número máximo de jugadores no puede ser mayor que " + DEFAULT_MAX_PLAYERS);

        config.set("maxPlayers", max);
        return this;
    }

    public ArenaEditor setLobbySpawn(@NotNull Location spawn) {
        Objects.requireNonNull(spawn, "spawn cannot be null");
        arena.setLobbySpawn(spawn);
        config.set("spawns.lobbySpawn", ArenaRepository.parseLocationToString(spawn, LocationFormatOptions.FULL));
        return this;
    }

    public ArenaEditor setPos1(@NotNull Location pos1) {
        Objects.requireNonNull(pos1, "pos1 cannot be null");
        arena.setPos1(pos1); // Ensure pos2 is not null
        config.set("pos1", ArenaRepository.parseLocationToString(pos1, LocationFormatOptions.X_Y_Z));
        return this;
    }

    public ArenaEditor setPos2(@NotNull Location pos2) {
        Objects.requireNonNull(pos2, "pos2 cannot be null");
        arena.setPos2(pos2); // Ensure pos2 is not null
        config.set("pos2", ArenaRepository.parseLocationToString(pos2, LocationFormatOptions.X_Y_Z));
        return this;
    }

    public void addPlayer(@NotNull Location playerLocation) {
        Objects.requireNonNull(playerLocation, "The player location cannot be null");
        List<String> players = config.getStringList("spawns.players");

        arena.addPlayerSpawn(playerLocation);
        players.add(ArenaRepository.parseLocationToString(playerLocation, LocationFormatOptions.X_Y_Z_YAW_PITCH));

        config.set("spawns.players", players);
    }
}
