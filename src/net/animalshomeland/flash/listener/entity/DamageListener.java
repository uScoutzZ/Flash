package net.animalshomeland.flash.listener.entity;

import net.animalshomeland.flash.Flash;
import net.animalshomeland.flash.game.FlashPlayer;
import net.animalshomeland.flash.game.GameState;
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
            Player p = (Player) event.getEntity();

            if(Flash.getInstance().getGame().getGameState() != GameState.INGAME) {
                event.setCancelled(true);
            } else {
                if(event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                    if(p.getLocation().getY() <= 0) {
                        event.setCancelled(true);
                    }
                } else if(event.getCause() == EntityDamageEvent.DamageCause.VOID) {
                    p.setHealth(0.0);
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
