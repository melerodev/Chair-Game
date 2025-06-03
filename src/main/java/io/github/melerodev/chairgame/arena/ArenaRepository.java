package io.github.melerodev.chairgame.arena;

import io.github.melerodev.chairgame.api.LocationFormatOptions;
import io.github.milkdrinkers.crate.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ArenaRepository {
    private final File folder;
    public static final String ARENA_FILE_EXTENSION = ".yml";

    public ArenaRepository(@NotNull File folder) {
        Objects.requireNonNull(folder, "Folder cannot be null");
        this.folder = folder;
        if (!folder.exists()) folder.mkdirs();
    }

    public List<Arena> loadAll() {
        List<Arena> arenas = new ArrayList<>();

        File[] files = folder.listFiles();
        if (files == null) return arenas;

        for (File file : files) {
            // ERROR: FALTARIA VALIDAR QUE EL ARCHIVO EXISTA ANTES DE CARGARLO....
            if (file.isFile() && file.getName().endsWith(ARENA_FILE_EXTENSION)) {
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

    private Arena loadArenaFromFile(@NotNull File file) {
        Objects.requireNonNull(file, "File cannot be null");
        Config config = new Config(file);

        String name = config.getString("name");
        World world = Bukkit.getWorld(config.getString("world"));

        if (world == null) throw new IllegalArgumentException("The world specified in the arena config does not exist: " + config.getString("world"));

        Location pos1 = parseLocationFromString(config.getString("pos1"), world);

        Location pos2 = parseLocationFromString(config.getString("pos2"), world);

        Location lobbySpawn = parseLocationFromString(config.getString("spawns.lobbySpawn"), null);

        int min = config.getInt("minPlayers");
        int max = config.getInt("maxPlayers");

        Arena arena = new Arena(name, world, min, max);
        arena.setPos1(pos1);
        arena.setPos2(pos2);
        arena.setLobbySpawn(lobbySpawn);
//        loadPlayerSpawns(config, arena);

        System.out.println("Num of players here: " + arena.getPlayerSpawns());

        return arena;
    }

    public static Location parseLocationFromString(@NotNull String raw, @Nullable World defaultWorld) {
        Objects.requireNonNull(raw, "Location string cannot be null");

        String[] parts = raw.split(",");

        if (parts.length == 3) {
            // x,y,z
            Objects.requireNonNull(defaultWorld, "Default world is required for 3-part location");

            double x = Double.parseDouble(parts[0]);
            double y = Double.parseDouble(parts[1]);
            double z = Double.parseDouble(parts[2]);

            return new Location(defaultWorld, x, y, z);
        } else if (parts.length == 6) {
            // world,x,y,z,yaw,pitch
            World world = Bukkit.getWorld(parts[0]);
            if (world == null) {
                throw new IllegalArgumentException("World '" + parts[0] + "' not found.");
            }

            double x = Double.parseDouble(parts[1]);
            double y = Double.parseDouble(parts[2]);
            double z = Double.parseDouble(parts[3]);
            float yaw = Float.parseFloat(parts[4]);
            float pitch = Float.parseFloat(parts[5]);

            return new Location(world, x, y, z, yaw, pitch);
        } else {
            throw new IllegalArgumentException("Invalid location string format: " + raw);
        }
    }

//    public void delete(String arenaName) {
//        File file = new File(folder, arenaName + ".yml");
//        if (file.exists()) {
//            if (file.delete()) {
//                System.out.println("Archivo de arena " + arenaName + " eliminado.");
//            } else {
//                System.out.println("No se pudo eliminar el archivo de arena " + arenaName + ".");
//            }
//        }
//    }

    public File getFolder() {
        return folder;
    }

    public static String parseLocationToString(@NotNull Location loc, @NotNull LocationFormatOptions options) {
        Objects.requireNonNull(loc, "Location cannot be null");
        Objects.requireNonNull(options, "LocationFormatOptions cannot be null");

        StringBuilder sb = new StringBuilder();

        if (options.includeWorld) {
            sb.append(loc.getWorld().getName()).append(",");
        }

        sb.append(loc.getX()).append(",")
            .append(loc.getY()).append(",")
            .append(loc.getZ());

        if (options.includeYawPitch) {
            sb.append(",").append(loc.getYaw())
                .append(",").append(loc.getPitch());
        }

        return sb.toString();
    }

//    public static void loadPlayerSpawns(@NotNull Config config, @NotNull Arena arena) {
//        Objects.requireNonNull(config, "Config cannot be null");
//        List<String> playerSpawns = config.getStringList("spawns.players");
//        for (String spawn : playerSpawns) {
//            System.out.println("config = " + spawn);
//            World world;
//            try {
//                world = Bukkit.getWorld(config.getString("world"));
//            } catch (NullPointerException e) {
//                throw new NullPointerException("World specified in the config does not exist: " + e);
//            }
//            Location location = parseLocationFromString(config, spawn, world);
//            arena.addPlayerSpawn(location);
//        }
//    }
}