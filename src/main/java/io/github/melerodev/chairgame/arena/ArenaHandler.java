package io.github.melerodev.chairgame.arena;

import io.github.melerodev.chairgame.ChairGame;
import io.github.melerodev.chairgame.Reloadable;
import lombok.Getter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ArenaHandler implements Reloadable {
    private final ChairGame plugin;
    private final ArenaRepository repository;
    @Getter
    private List<Arena> arenas;

    public ArenaHandler(ChairGame plugin) {
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
        this.arenas = repository.loadAll();
    }

    public void removeArena(String name) {
        repository.delete(name);
        arenas.removeIf(a -> a.getName().equals(name));
    }
}
