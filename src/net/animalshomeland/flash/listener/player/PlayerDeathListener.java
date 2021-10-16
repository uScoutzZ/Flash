package net.animalshomeland.flash.listener.player;

import net.animalshomeland.flash.Flash;
import net.animalshomeland.flash.game.FlashPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player p = event.getEntity();

        p.spigot().respawn();
        event.setDeathMessage(null);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player p = event.getPlayer();

        Bukkit.getScheduler().scheduleSyncDelayedTask(Flash.getInstance(), new Runnable() {
            @Override
            public void run() {
                FlashPlayer.getPlayer(p).teleportCheckpoint();
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 20));
                p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 5));
            }
        }, 1);
    }
}
