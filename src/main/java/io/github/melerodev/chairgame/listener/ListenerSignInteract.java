package io.github.melerodev.chairgame.listener;

import io.github.melerodev.chairgame.utility.Cfg;
import io.github.milkdrinkers.wordweaver.Translation;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ListenerSignInteract implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) return;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Material type = event.getClickedBlock().getType();
        if (type != Material.OAK_SIGN && type != Material.OAK_WALL_SIGN) return;

        Block block = event.getClickedBlock();
        Sign sign = (Sign) block.getState();

        String line0 = sign.getLine(0).trim();

        if (line0.equalsIgnoreCase("[ChairGame]")) {
            Player player = event.getPlayer();
            if (!Cfg.get().getStringList("allowed-worlds").contains(player.getWorld().getName())) return;
            player.sendMessage(Translation.as("chairgame.join"));
//            player.performCommand("sw join");
            event.setCancelled(true);
        }
    }

}