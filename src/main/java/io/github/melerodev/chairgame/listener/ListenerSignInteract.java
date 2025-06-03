package io.github.melerodev.chairgame.listener;

import io.github.melerodev.chairgame.ChairGame;
import io.github.melerodev.chairgame.arena.ArenaRepository;
import io.github.melerodev.chairgame.permission.Permissions;
import io.github.melerodev.chairgame.utility.Cfg;
import io.github.milkdrinkers.crate.Config;
import io.github.milkdrinkers.wordweaver.Translation;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ListenerSignInteract implements Listener {
    private static final PlainTextComponentSerializer PLAIN_SERIALIZER = PlainTextComponentSerializer.plainText();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) return;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Material type = event.getClickedBlock().getType();
        if (type != Material.OAK_SIGN && type != Material.OAK_WALL_SIGN) return;

        Block block = event.getClickedBlock();
        Sign sign = (Sign) block.getState();

        String line0 = PLAIN_SERIALIZER
            .serialize(sign.getSide(Side.FRONT).line(0)) // sign debe ser org.bukkit.block.Sign (en Paper)
            .trim();

        if (line0.equalsIgnoreCase("[ChairGame]")) {
            Player player = event.getPlayer();
            if (event.getPlayer().hasPermission(Permissions.JOIN_PERMISSION.getNode()) || event.getPlayer().hasPermission(Permissions.ADMIN_PERMISSION.getNode())) {
                if (!Cfg.get().getStringList("allowed-worlds").contains(player.getWorld().getName())) {
                    player.sendMessage(Translation.as("chairgame.errors.world-not-allowed"));
                    event.setCancelled(true);
                    return;
                };

                player.teleport(ChairGame.getInstance().getArenaHandler().getArenaByName("Sillas").get().getLobbySpawn());

                player.sendMessage(Translation.as("chairgame.join"));
                event.setCancelled(true); // Do not do the default interactions
            } else {
                player.sendMessage(Translation.as("chairgame.errors.no-permission"));
                event.setCancelled(true);
            }
        } else {
            event.getPlayer().sendMessage(line0);
        }
    }
}