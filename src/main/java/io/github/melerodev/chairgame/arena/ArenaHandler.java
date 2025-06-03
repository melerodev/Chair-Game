package io.github.melerodev.chairgame.arena;

import io.github.melerodev.chairgame.ChairGame;
import io.github.melerodev.chairgame.Reloadable;
import lombok.Getter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ArenaHandler implements Reloadable {
    private final ChairGame plugin;
    @Getter
    public final ArenaRepository repository;
    @Getter
    private List<Arena> arenas;

    public ArenaHandler(ChairGame plugin) {
        Objects.requireNonNull(plugin, "Plugin cannot be null");
        this.plugin = plugin;
        this.repository = new ArenaRepository(new File(plugin.getDataFolder(), "arenas"));
        this.arenas = new ArrayList<>();
    }

    @Override
    public void onLoad(ChairGame plugin) {
        this.arenas = repository.loadAll();
    }

    @Override
    public void onEnable(ChairGame plugin) {}

    @Override
    public void onDisable(ChairGame plugin) {}

    public void reload() {
        onLoad(plugin);
    }

//    public void removeArena(String name) {
//        Objects.requireNonNull(name, "Arena name cannot be null");
//        repository.delete(name);
//        arenas.removeIf(a -> a.getName().equals(name));
//    }

    public Optional<Arena> getArenaByName(String name) {
        Objects.requireNonNull(name, "Arena name cannot be null");
        return arenas.stream()
                .filter(arena -> arena.getName().equalsIgnoreCase(name))
                .findFirst();
    }
}
