package net.animalshomeland.flash.game;

import lombok.Getter;
import lombok.Setter;
import net.animalshomeland.flash.Flash;
import net.animalshomeland.flash.utilities.Locale;
import net.animalshomeland.gameapi.item.ItemBuilder;
import net.animalshomeland.gameapi.minigame.GameCountdown;
import net.animalshomeland.gameapi.user.User;
import net.animalshomeland.gameapi.util.ServerUtilities;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class Game {

    @Getter @Setter
    private GameState gameState;
    @Getter
    private GameCountdown gameCountdown;
    @Getter
    private GameMap gameMap;
    @Getter @Setter
    private long beginningTime;
    @Getter @Setter
    private int roundDuration, minPlayers;
    @Getter @Setter
    private List<Player> players;
    @Getter @Setter
    private List<String> winners;

    public Game() {
        gameState = GameState.LOBBY;
        roundDuration = 30;
        minPlayers = 2;
        final int[] lobbyTime = {Flash.getInstance().getGameConfig().getConfigFile().getInt("lobby-timer")};

        gameCountdown = new GameCountdown(lobbyTime[0])
                .setLocale(Locale.class, "gamestart")
                .setSound(Sound.BLOCK_NOTE_BLOCK_BASS)
                .setCondition(i -> Bukkit.getOnlinePlayers().size() > 1)
                .setActionOnRun(onlinePlayers -> {
                    if(Bukkit.getOnlinePlayers().size() != Flash.getInstance().getGame().getMinPlayers() && !gameCountdown.isForced()) {
                        gameCountdown.setTime(lobbyTime[0]);
                        onlinePlayers.forEach(player -> {
                            User.getFromPlayer(player).sendActionbar(Locale.get(player, "min-players",
                                    Flash.getInstance().getGame().getMinPlayers()), 1);
                        });
                    } else {
                        onlinePlayers.forEach(player ->
                                player.setLevel(gameCountdown.getTime()));
                    }
                })
                .setActionOnFinish(onlinePlayers -> {
                    gameMap.init();
                    ServerUtilities.setServerInvisible();
                    beginningTime = System.currentTimeMillis();
                    gameState = GameState.INGAME;
                    startIngameCounter();
                    Flash.getInstance().getGame().getGameMap().setSpawn(Flash.getInstance().getLocationManager().getLocation("spawn", true));

                    onlinePlayers.forEach(player -> {
                        players.add(player);
                        player.teleport(Flash.getInstance().getGame().getGameMap().getSpawn());
                        player.sendMessage(Locale.get(player, "gamestart_start"));
                        player.getInventory().setItem(4, ItemBuilder.create(Material.RED_DYE)
                                .name(Locale.get(player, "instant-death"))
                                .build());
                        player.getInventory().setItem(8, ItemBuilder.create(Material.LIME_DYE)
                                .name(Locale.get(player, "players-visible"))
                                .build());
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 5));
                        player.setLevel(0);
                        Flash.getInstance().getScoreboardManager().setup(player);
                    });
                }).start();

        gameMap = new GameMap();
        winners = new ArrayList<>();
        players = new ArrayList<>();
    }

    public void startIngameCounter() {
        gameCountdown = new GameCountdown(roundDuration)
                .setLocale(Locale.class, "roundend")
                .setSound(Sound.BLOCK_NOTE_BLOCK_BASS)
                .setActionOnRun(onlinePlayers -> {
                    onlinePlayers.forEach(player -> {
                        Flash.getInstance().getScoreboardManager().update(player);
                    });
                })
                .setActionOnFinish(onlinePlayers -> {
                    Flash.getInstance().getGame().setGameState(GameState.END);
                    String message;
                    String winner = "N/A";
                    if(Flash.getInstance().getGame().getWinners().size() == 0) {
                        message = "no-player-won";
                    } else {
                        message = "player-won";
                        winner = Flash.getInstance().getGame().getWinners().get(0);
                    }
                    Location spawn = Flash.getInstance().getLocationManager().getLocation("spawn", false);
                    String finalWinner = winner;
                    onlinePlayers.forEach(player -> {
                        player.teleport(spawn);
                        player.sendMessage(Locale.get(player, message, finalWinner));
                    });
                    int endTime = Flash.getInstance().getGameConfig().getConfigFile().getInt("end-timer");
                    gameCountdown = new GameCountdown(endTime)
                            .setLocale(Locale.class, "gameend")
                            .setSound(Sound.BLOCK_NOTE_BLOCK_BASS)
                            .setActionOnFinish(onlinePlayers2 -> {
                                onlinePlayers2.forEach(player -> player.sendMessage(Locale.get(player, "gameend_stop")));
                                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "ahstop");
                            }).start();
                }).start();
    }
}
