package net.animalshomeland.flash.game;

import lombok.Getter;
import lombok.Setter;
import net.animalshomeland.flash.Flash;
import net.animalshomeland.flash.utilities.Locale;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class GameCountdown {

    @Getter @Setter
    private int lobbyTime;
    @Getter
    private int endTime, defaultLobbyTime, defaultEndTime;
    @Getter
    private BukkitTask lobbyTask, endTask, ingameTask;

    @Getter @Setter
    private long[] duration;

    public GameCountdown() {
        defaultLobbyTime = Flash.getInstance().getGameConfig().getConfigFile().getInt("lobby-timer");
        defaultEndTime = Flash.getInstance().getGameConfig().getConfigFile().getInt("end-timer");
    }

    public void startLobbyCounter() {
        lobbyTime = defaultLobbyTime;
        if(lobbyTime != 0 && Flash.getInstance().getGame().getGameState() == GameState.LOBBY) {
            lobbyTask = new BukkitRunnable() {
                @Override
                public void run() {
                    if(lobbyTime > 0) {
                        if(lobbyTime == defaultLobbyTime || lobbyTime == 20 || lobbyTime == 15 || lobbyTime == 10 || lobbyTime <= 5) {
                            String number = "plural";
                            if(lobbyTime == 1) {
                                number = "singular";
                            }
                            for(Player all : Bukkit.getOnlinePlayers()) {
                                all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1.0F, 1.0F);
                                all.sendMessage(Locale.get(all, "gamestart_" + number, String.valueOf(lobbyTime)));
                            }
                        }
                        lobbyTime--;
                    } else {
                        cancel();
                        Flash.getInstance().getGame().start();
                    }
                }
            }.runTaskTimer(Flash.getInstance(), 0, 20);
        }
    }

    public void startIngameCounter() {
        if(duration[0] != 0 && Flash.getInstance().getGame().getGameState() == GameState.INGAME) {
            ingameTask = new BukkitRunnable() {
                @Override
                public void run() {
                    if(duration[0] > 0) {
                        for(Player all : Bukkit.getOnlinePlayers()) {
                            Flash.getInstance().getScoreboardManager().update(all);
                        }
                        if(duration[0] == 60 || duration[0] == 20 || duration[0] == 15 || duration[0] == 10 || duration[0] <= 5) {
                            String number = "plural";
                            if(duration[0] == 1) {
                                number = "singular";
                            }
                            for(Player all : Bukkit.getOnlinePlayers()) {
                                all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1.0F, 1.0F);
                                all.sendMessage(Locale.get(all, "roundend_" + number, String.valueOf(duration[0])));
                            }
                        }
                        duration[0]--;
                    } else {
                        cancel();
                        Flash.getInstance().getGame().setGameState(GameState.END);
                        startEndCounter();
                    }
                }
            }.runTaskTimer(Flash.getInstance(), 0, 20);
        }
    }

    public void startEndCounter() {
        endTime = defaultEndTime;
        if(endTime != 0 && Flash.getInstance().getGame().getGameState() == GameState.END) {
            String message;
            String winner = "N/A";
            if(Flash.getInstance().getGame().getWinners().size() == 0) {
                message = "no-player-won";
            } else {
                message = "player-won";
                winner = Flash.getInstance().getGame().getWinners().get(0);
            }
            Location spawn = Flash.getInstance().getLocationManager().getLocation("spawn", false);
            for(Player all : Bukkit.getOnlinePlayers()) {
                all.teleport(spawn);
                all.sendMessage(Locale.get(all, message, winner));
            }
            endTask = new BukkitRunnable() {
                @Override
                public void run() {
                    if(endTime > 0) {
                        if(endTime == defaultEndTime || endTime == 20 || endTime == 15 || endTime == 10 || endTime <= 5) {
                            String number = "plural";
                            if(endTime == 1) {
                                number = "singular";
                            }
                            for(Player all : Bukkit.getOnlinePlayers()) {
                                all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1.0F, 1.0F);
                                all.sendMessage(Locale.get(all, "gameend_" + number, String.valueOf(endTime)));
                            }

                        }
                        endTime--;
                    } else {
                        cancel();
                        Flash.getInstance().getGame().end();
                    }
                }
            }.runTaskTimer(Flash.getInstance(), 0, 20);
        }
    }
}
