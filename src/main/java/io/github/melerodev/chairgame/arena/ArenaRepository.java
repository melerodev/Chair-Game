package io.github.melerodev.chairgame.arena;

import io.github.milkdrinkers.crate.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ArenaRepository {
    private final File folder;

    public ArenaRepository(File folder) {
        this.folder = folder;
        if (!folder.exists()) folder.mkdirs();
    }

    public List<Arena> loadAll() {
        List<Arena> arenas = new ArrayList<>();

        File[] files = folder.listFiles();
        if (files == null) return arenas;

        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".yml")) {
                try {
                    Arena arena = loadArenaFromFile(file);
                    if (arena != null) arenas.add(arena);
                } catch (Exception e) {
                    System.out.println("Error al cargar arena desde " + file.getName() + ": " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }

        return arenas;
    }

    private Arena loadArenaFromFile(File file) {
        Config config = new Config(file);

        String name = config.getString("name");
        World world = Bukkit.getWorld(config.getString("world"));
        if (world == null) throw new IllegalArgumentException("El mundo no existe");

        String[] pos1Parts = config.getString("pos1").split(",");
        Location pos1 = new Location(
            world,
            Double.parseDouble(pos1Parts[0]),
            Double.parseDouble(pos1Parts[1]),
            Double.parseDouble(pos1Parts[2])
        );

        String[] pos2Parts = config.getString("pos2").split(",");
        Location pos2 = new Location(
            world,
            Double.parseDouble(pos2Parts[0]),
            Double.parseDouble(pos2Parts[1]),
            Double.parseDouble(pos2Parts[2])
        );

        String[] spawnParts = config.getString("spawns.lobbySpawn").split(",");
        Location lobbySpawn = new Location(
            Bukkit.getWorld(spawnParts[0]),
            Double.parseDouble(spawnParts[1]),
            Double.parseDouble(spawnParts[2]),
            Double.parseDouble(spawnParts[3]),
            Float.parseFloat(spawnParts[4]),
            Float.parseFloat(spawnParts[5])
        );

        int min = config.getInt("minPlayers");
        int max = config.getInt("maxPlayers");

        Arena arena = new Arena(name, world, min, max);
        arena.setPositions(pos1, pos2);
        arena.setLobbySpawn(lobbySpawn);

        System.out.println("Arena " + name + " cargada desde archivo.");
        return arena;
    }

    public void delete(String arenaName) {
        File file = new File(folder, arenaName + ".yml");
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Archivo de arena " + arenaName + " eliminado.");
            } else {
                System.out.println("No se pudo eliminar el archivo de arena " + arenaName + ".");
            }
        }
    }
}
