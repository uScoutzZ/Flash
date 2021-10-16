package net.animalshomeland.flash.listener.player;

import net.animalshomeland.flash.Flash;
import net.animalshomeland.flash.game.FlashPlayer;
import net.animalshomeland.flash.game.GameState;
import net.animalshomeland.flash.utilities.Locale;
import net.animalshomeland.gameapi.util.Debug;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player p = event.getPlayer();

        if(Flash.getInstance().getGame().getGameState() == GameState.INGAME) {
            if(!FlashPlayer.getPlayer(p).isSpectator()) {
                for(Player all : Bukkit.getOnlinePlayers()) {
                    all.sendMessage(Locale.get(all, "player-left", p.getDisplayName()));
                }
                Flash.getInstance().getGame().getPlayers().remove(p);

                if(Flash.getInstance().getGame().getPlayers().size() == 1) {
                    for(Player all : Bukkit.getOnlinePlayers()) {
                        all.sendMessage(Locale.get(all, "all-players-left"));
                    }

                    Player last = Flash.getInstance().getGame().getPlayers().get(0);
                    if(!FlashPlayer.getPlayer(last).isSpectator()) {
                        Flash.getInstance().getGame().getGameCountdown().setDuration(new long[]{0});
                    }
                } else if(Flash.getInstance().getGame().getPlayers().size() == 0) {
                    Flash.getInstance().getGame().end();
                }
            } else {
                event.setQuitMessage(null);
            }
        } else {
            if(Flash.getInstance().getGame().getGameState() == GameState.LOBBY) {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    all.sendMessage(Locale.get(all, "player-left", p.getDisplayName()));
                }
                if(Bukkit.getOnlinePlayers().size()-1 < Flash.getInstance().getGame().getMinPlayers()) {
                    Flash.getInstance().getGame().getGameCountdown().getLobbyTask().cancel();
                }
            }
        }
    }
}
