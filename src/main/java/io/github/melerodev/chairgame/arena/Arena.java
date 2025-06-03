package io.github.melerodev.chairgame.arena;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public @Getter class Arena {
    private final String name;
    private final World world;
    private final int minPlayers;
    private final int maxPlayers;
    @Setter
    private Location pos1;
    @Setter
    private Location pos2;
    private boolean isActive;
    @Setter
    private Location lobbySpawn;

    @Getter
    private final Map<String, Location> playerSpawns = new HashMap<>();
    private final Map<String, Location> chairsLocations = new HashMap<>();
    public static final String RE_ARENA_NAME = "^[a-zA-Z0-9_]{3,16}$";
    public static final int DEFAULT_MIN_PLAYERS = 2;
    public static final int DEFAULT_MAX_PLAYERS = 16;

    public Arena(String name, World world, int minPlayers, int maxPlayers) {
        this.name = Objects.requireNonNull(name, "Arena name cannot be null");
        if (this.name.isBlank()) {
            throw new IllegalArgumentException("Arena name cannot be blank");
        }
        if (!this.name.matches(RE_ARENA_NAME)) {
            throw new IllegalArgumentException("The arena name must match the pattern: " + RE_ARENA_NAME);
        }

        this.world = Objects.requireNonNull(world, "World cannot be null");
        // Comprobar que el mundo está cargado (usando el nombre real del mundo)
        if (Bukkit.getWorld(this.world.getName()) == null) {
            throw new IllegalArgumentException("The specified world does not exist or is not loaded: " + this.world.getName());
        }

        if (minPlayers < DEFAULT_MIN_PLAYERS) {
            throw new IllegalArgumentException("The minimum number of players cannot be less than " + DEFAULT_MIN_PLAYERS);
        }

        if (maxPlayers > DEFAULT_MAX_PLAYERS) {
            throw new IllegalArgumentException("The maximum number of players cannot exceed " + DEFAULT_MAX_PLAYERS);
        }

        if (minPlayers > maxPlayers) {
            throw new IllegalArgumentException("The minimum number of players cannot be greater than the maximum number of players.");
        }

        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
    }

    public void addPlayerSpawn(Location spawnLocation) {
        String numPlayerLocation = "";
        if (playerSpawns.size() > maxPlayers) {
            throw new IllegalStateException("Arena is full, cannot add more players.");
        }
        playerSpawns.put(numPlayerLocation, spawnLocation);
        System.out.println("PENE");
    }

    public void addChair(String id, Location location) {
        chairsLocations.put(id, location);
    }

    public void startGame() {
        if (!isReady()) {
            throw new IllegalStateException("Arena is not ready to start the game.");
        }
        isActive = true;
        // lógica de inicio del juego
    }

    public void stopGame() {
        isActive = false;
        playerSpawns.clear();
        // lógica de limpieza
    }

    public boolean isReady() {
        return pos1 != null && pos2 != null && lobbySpawn != null
                && !chairsLocations.isEmpty() && playerSpawns.size() >= minPlayers;
    }
}



