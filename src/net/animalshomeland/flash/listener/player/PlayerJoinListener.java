package net.animalshomeland.flash.listener.player;

import net.animalshomeland.flash.Flash;
import net.animalshomeland.flash.game.FlashPlayer;
import net.animalshomeland.flash.game.GameState;
import net.animalshomeland.flash.game.ScoreboardManager;
import net.animalshomeland.flash.utilities.Locale;
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
            if(Bukkit.getOnlinePlayers().size() == Flash.getInstance().getGame().getMinPlayers()) {
                Flash.getInstance().getGame().getGameCountdown().startLobbyCounter();
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
