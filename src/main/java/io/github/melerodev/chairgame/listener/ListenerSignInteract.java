package io.github.melerodev.chairgame.listener;

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

//        String line0 = sign.getLine(0).trim();


        String line0 = PLAIN_SERIALIZER
            .serialize(sign.getSide(Side.FRONT).line(0)) // sign debe ser org.bukkit.block.Sign (en Paper)
            .trim();

        if (line0.equalsIgnoreCase("[ChairGame]")) {
            Player player = event.getPlayer();
            if (event.getPlayer().hasPermission(Permissions.JOIN_PERMISSION.getNode()) || event.getPlayer().hasPermission(Permissions.ADMIN_PERMISSION.getNode())) {
                if (!Cfg.get().getStringList("allowed-worlds").contains(player.getWorld().getName())) return;

                Config config = Cfg.get();

                String worldName = config.getString("lobby-spawn-location.world");
                World world = Bukkit.getWorld(worldName);

                if ((Bukkit.getWorld(config.getString("lobby-spawn-location.world")) == null)) {
                    player.sendMessage(Translation.as("chairgame.errors.world-not-found"));
                    event.setCancelled(true);
                    return;
                }

                Location spawnLocation = new Location(
                    world,
                    config.getDouble("lobby-spawn-location.x"),
                    config.getDouble("lobby-spawn-location.y"),
                    config.getDouble("lobby-spawn-location.z"),
                    (float) config.getDouble("lobby-spawn-location.yaw"),
                    (float) config.getDouble("lobby-spawn-location.pitch")
                );

                player.teleport(spawnLocation);
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