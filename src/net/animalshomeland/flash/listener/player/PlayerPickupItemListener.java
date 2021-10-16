package net.animalshomeland.flash.listener.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayerPickupItemListener implements Listener {

    @EventHandler
    public void onPlayerPickup(PlayerPickupItemEvent event) {
        event.setCancelled(true);
    }
}
