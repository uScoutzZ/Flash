package net.apotox.flash.listener.player;

import net.apotox.flash.Flash;
import net.apotox.flash.game.FlashPlayer;
import net.apotox.flash.game.GameState;
import net.apotox.flash.utilities.Locale;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if(Flash.getInstance().getGame().getGameState() == GameState.INGAME) {
            if(!FlashPlayer.getPlayer(player).isSpectator()) {
                for(Player all : Bukkit.getOnlinePlayers()) {
                    all.sendMessage(Locale.get(all, "player-left", player.getDisplayName()));
                }
                Flash.getInstance().getGame().getPlayers().remove(player);

                if(Flash.getInstance().getGame().getPlayers().size() == 1) {
                    for(Player all : Bukkit.getOnlinePlayers()) {
                        all.sendMessage(Locale.get(all, "all-players-left"));
                    }

                    Player last = Flash.getInstance().getGame().getPlayers().get(0);
                    if(!FlashPlayer.getPlayer(last).isSpectator()) {
                        Flash.getInstance().getGame().getGameCountdown().setTime(0);
                    }
                } else if(Flash.getInstance().getGame().getPlayers().size() == 0) {
                    Flash.getInstance().getGame().getGameCountdown().setTime(0);
                }
            } else {
                event.setQuitMessage(null);
            }
        } else {
            if(Flash.getInstance().getGame().getGameState() == GameState.LOBBY) {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    all.sendMessage(Locale.get(all, "player-left", player.getDisplayName()));
                }
            }
        }
    }
}
