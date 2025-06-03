package io.github.melerodev.chairgame.command.spawn;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.melerodev.chairgame.ChairGame;
import io.github.melerodev.chairgame.arena.Arena;
import io.github.melerodev.chairgame.arena.ArenaEditor;
import io.github.melerodev.chairgame.arena.ArenaRepository;
import io.github.melerodev.chairgame.permission.Permissions;
import io.github.milkdrinkers.wordweaver.Translation;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Optional;

public class CommandSetSpawnPlayer {
    private final ChairGame plugin;

    public CommandSetSpawnPlayer() {
        this.plugin = ChairGame.getInstance();
    }

    public CommandAPICommand command() {
        return new CommandAPICommand("player")
            .withFullDescription("Set the spawn point for the game.")
            .withShortDescription("Set the spawn point for the game.")
            .withPermission(Permissions.SET_SPAWN_PLAYER_PERMISSION.getNode())
            .executes(this::executorSetSpawnPlayer);
    }

    private void executorSetSpawnPlayer(CommandSender sender, CommandArguments args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Translation.as("chairgame.errors.not-from-console"));
            return;
        }

        String arenaName = args.get("arena").toString();

        if (arenaName == null || arenaName.trim().isEmpty()) {
            sender.sendMessage(Translation.as("chairgame.errors.no-enough-arguments"));
            return;
        }

        Optional<Arena> optionalArena = plugin.getArenaHandler().getArenaByName(arenaName);

        if (optionalArena.isEmpty()) {
            sender.sendMessage(Translation.as("chairgame.errors.arena-not-found") + arenaName);
            return;
        }

        Arena arena = optionalArena.get();

        File configFile = new File(plugin.getDataFolder(), "arenas" + File.separator + arenaName + ArenaRepository.ARENA_FILE_EXTENSION);

        if (!configFile.exists()) throw new IllegalArgumentException("Arena configuration file does not exist: " + configFile.getAbsolutePath());

        ArenaEditor editor = new ArenaEditor(arenaName, plugin.getArenaHandler().getRepository(), arena);
        editor.addPlayer(player.getLocation());

        sender.sendMessage(Translation.as("chairgame.spawn-player"));
    }
}
