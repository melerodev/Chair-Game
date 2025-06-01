package io.github.melerodev.chairgame.arena;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.World;
import java.util.HashMap;
import java.util.Map;

public @Getter class Arena {
    private final String name;
    private final World world;
    private final int minPlayers;
    private final int maxPlayers;
    private Location pos1;
    private Location pos2;
    private boolean isActive;
    @Setter
    private Location lobbySpawn;
    private final Map<String, Location> playerSpawns = new HashMap<>();
    private final Map<String, Location> chairsLocations = new HashMap<>();

    public static final String RE_ARENA_NAME = "^[a-zA-Z0-9_]{3,16}$";
    public static final int MIN_PLAYERS = 2;
    public static final int MAX_PLAYERS = 16;

    public Arena (String name, World world, int minPlayers, int maxPlayers) {
        if (minPlayers < MIN_PLAYERS) {
            throw new IllegalArgumentException("The minimum number of players cannot be less than " + MIN_PLAYERS);
        }

        if (maxPlayers > MAX_PLAYERS) {
            throw new IllegalArgumentException("The maximum number of players cannot exceed " + MAX_PLAYERS);
        }

        if (minPlayers > maxPlayers) {
            throw new IllegalArgumentException("The minimum number of players cannot be greater than the maximum number of players.");
        }

        if (!name.matches(RE_ARENA_NAME)) {
            throw new IllegalArgumentException("The arena name must match the pattern: " + RE_ARENA_NAME);
        }

        this.name = name;
        this.world = world;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
    }

    public void setPositions(Location pos1, Location pos2) {
        this.pos1 = pos1;
        this.pos2 = pos2;
    }

    public void addPlayerSpawn(Location spawnLocation) {
        String numPlayerLocation = "";
        if (playerSpawns.size() >= maxPlayers) {
            throw new IllegalStateException("Arena is full, cannot add more players.");
        }
        playerSpawns.put(numPlayerLocation, spawnLocation);
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



