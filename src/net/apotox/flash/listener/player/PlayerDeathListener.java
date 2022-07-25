package net.apotox.flash.listener.player;

import net.apotox.flash.Flash;
import net.apotox.flash.game.FlashPlayer;
import org.bukkit.Bukkit;
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
        Player player = event.getEntity();

        player.spigot().respawn();
        event.setDeathMessage(null);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        Bukkit.getScheduler().scheduleSyncDelayedTask(Flash.getInstance(), new Runnable() {
            @Override
            public void run() {
                FlashPlayer.getPlayer(player).teleportCheckpoint();
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 20));
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 5));
            }
        }, 1);
    }
}
