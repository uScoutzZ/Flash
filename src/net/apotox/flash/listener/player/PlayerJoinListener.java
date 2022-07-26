package net.apotox.flash.listener.player;

import net.apotox.flash.Flash;
import net.apotox.flash.game.FlashPlayer;
import net.apotox.flash.game.GameState;
import net.apotox.flash.utilities.Locale;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.setGameMode(GameMode.ADVENTURE);
        player.getInventory().clear();
        player.getActivePotionEffects().clear();
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 20));
        FlashPlayer flashPlayer = new FlashPlayer(player);

        if(Flash.getInstance().getGame().getGameState() == GameState.LOBBY) {
            player.teleport(Flash.getInstance().getLocationManager().getLocation("spawn", false));
            for(Player all : Bukkit.getOnlinePlayers()) {
                all.sendMessage(Locale.get(all, "player-joined", player.getDisplayName()));
            }
        } else {
            flashPlayer.setSpectator(true);
            Flash.getInstance().getScoreboardManager().setup(player);
            event.setJoinMessage(null);
            player.teleport(Flash.getInstance().getGame().getGameMap().getSpawn());
            player.setGameMode(GameMode.SPECTATOR);
        }
    }
}
