package io.github.melerodev.chairgame.listener;

import io.github.melerodev.chairgame.permission.Permissions;
import io.github.melerodev.chairgame.utility.Cfg;
import io.github.milkdrinkers.wordweaver.Translation;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class ListenerSignChange implements Listener {
    private static final PlainTextComponentSerializer PLAIN_SERIALIZER = PlainTextComponentSerializer.plainText();

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        Player player = event.getPlayer();
        Component line = event.line(0); // Cada línea de texto (máximo 4)

        assert line != null;
        if (PLAIN_SERIALIZER.serialize(line).equalsIgnoreCase("[ChairGame]")) {
            if (Cfg.get().getStringList("allowed-worlds").contains(player.getWorld().getName())) {
                if (player.hasPermission(Permissions.ADMIN_PERMISSION.getNode())) {
                    event.line(0, Component.text("[ChairGame]").color(NamedTextColor.GREEN));
                    player.sendMessage(PLAIN_SERIALIZER.serialize(event.line(0)));
                } else {
                    player.sendMessage(Translation.as("no-permission"));
                    event.setCancelled(true);
                }
            } else {
                player.sendMessage(Translation.as("world-not-allowed"));
                event.setCancelled(true);
            }
        }
    }
}
