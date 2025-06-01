package io.github.melerodev.chairgame.arena;

import io.github.melerodev.chairgame.ChairGame;
import io.github.melerodev.chairgame.Reloadable;
import io.github.milkdrinkers.crate.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.File;
import java.util.List;

public class ArenaHandler implements Reloadable {
    private final ChairGame plugin;
    public List<Arena> arenas;
    private final File arenasFolder;

    public ArenaHandler(ChairGame plugin) {
        this.plugin = plugin;
        this.arenasFolder = new File(plugin.getDataFolder(), "arenas");
    }

    @Override
    public void onLoad(ChairGame plugin) {
        loadArenas();
    }

    @Override
    public void onEnable(ChairGame plugin) {
    }

    @Override
    public void onDisable(ChairGame plugin) {
    }

    private void loadArenas() {
        if (arenasFolder.exists()) {
            File[] files = arenasFolder.listFiles();
            if (files != null && files.length > 0) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".yml")) {
                        Config config = new Config(file);
                        String name = config.getString("name");
                        World world = Bukkit.getWorld(config.getString("world"));

                        // GET THE POSITION1
                        String[] pos1Parts = config.getString("pos1").split(",");
                        Location post1 = new Location(
                                                            world /*world*/,
                                                            Double.parseDouble(pos1Parts[0]) /*x*/,
                                                            Double.parseDouble(pos1Parts[1]) /*y*/,
                                                            Double.parseDouble(pos1Parts[2]) /*z*/
                        );

                        // GET THE POSITION2
                        String[] pos2Parts = config.getString("pos2").split(",");
                        Location pos2 = new Location(
                            world /*world*/,
                            Double.parseDouble(pos2Parts[0]) /*x*/,
                            Double.parseDouble(pos2Parts[1]) /*y*/,
                            Double.parseDouble(pos2Parts[2]) /*z*/
                        );

                        int minPlayers = config.getInt("minPlayers");
                        int maxPlayers = config.getInt("maxPlayers");

                        String[] locationParts = config.getString("lobbySpawn").split(",");
                        Location lobbySpawn = new Location(
                                                            Bukkit.getWorld(locationParts[0]) /*world*/,
                                                            Double.parseDouble(locationParts[1]) /*y*/,
                                                            Double.parseDouble(locationParts[2])  /*z*/,
                                                            Double.parseDouble(locationParts[3]) /*x*/,
                                                            Float.parseFloat(locationParts[4]) /*yaw*/,
                                                            Float.parseFloat(locationParts[5]) /*pitch*/
                        );

                        Arena arena = new Arena(name, world, minPlayers, maxPlayers);
                        arena.setLobbySpawn(lobbySpawn);
                    }
                }
            }
        }

    }

    public void removeArena(Arena arena) {
        File arenaFile = new File(arenasFolder, arena.getName() + ".yml");
        if (arenaFile.exists()) {
            if (arenaFile.delete()) {
                System.out.println("Arena " + arena.getName() + " has been removed successfully.");
            } else {
                System.out.println("Failed to remove arena " + arena.getName() + ".");
            }
        }
    }

    public void addArena(String uuid, String name) {
        File arenaFolder = new File(plugin.getDataFolder(), "arenas");
        if (!arenaFolder.exists()) arenaFolder.mkdir();

        System.out.println("Se creado el minijuego:" + name + "-" + uuid);
    }

    public List<Arena> getArenas() {
        return arenas;
    }
}
