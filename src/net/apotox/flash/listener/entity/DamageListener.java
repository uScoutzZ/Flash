package net.apotox.flash.listener.entity;

import net.apotox.flash.Flash;
import net.apotox.flash.game.GameState;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if(event.getEntityType() == EntityType.PLAYER) {
            Player player = (Player) event.getEntity();

            if(Flash.getInstance().getGame().getGameState() != GameState.INGAME) {
                event.setCancelled(true);
            } else {
                if(event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                    if(player.getLocation().getY() <= 0) {
                        event.setCancelled(true);
                    }
                } else if(event.getCause() == EntityDamageEvent.DamageCause.VOID) {
                    player.setHealth(0.0);
                }
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if(event.getEntityType() == EntityType.PLAYER
                && event.getDamager().getType() == EntityType.PLAYER) {
            event.setCancelled(true);
        }
    }
}
