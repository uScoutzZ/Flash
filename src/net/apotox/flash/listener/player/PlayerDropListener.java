package net.apotox.flash.listener.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropListener implements Listener {

    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }
}
