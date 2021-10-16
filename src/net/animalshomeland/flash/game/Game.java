package net.animalshomeland.flash.game;

import lombok.Getter;
import lombok.Setter;
import net.animalshomeland.flash.Flash;
import net.animalshomeland.flash.utilities.Locale;
import net.animalshomeland.gameapi.item.ItemBuilder;
import net.animalshomeland.gameapi.util.ServerUtilities;
import org.bukkit.Bukkit;
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
    private long beginningTime, roundDuration, minPlayers;
    @Getter @Setter
    private List<Player> players;
    @Getter @Setter
    private List<String> winners;

    public Game() {
        gameState = GameState.LOBBY;
        roundDuration = 8*60;
        minPlayers = 2;
        gameCountdown = new GameCountdown();
        gameCountdown.setDuration(new long[]{roundDuration});

        gameMap = new GameMap();
        winners = new ArrayList<>();
        players = new ArrayList<>();
    }

    public void start() {
        gameMap.init();
        ServerUtilities.setServerInvisible();
        beginningTime = System.currentTimeMillis();
        gameState = GameState.INGAME;
        gameCountdown.startIngameCounter();

        Flash.getInstance().getGame().getGameMap().setSpawn(Flash.getInstance().getLocationManager().getLocation("spawn", true));

        for(Player all : Bukkit.getOnlinePlayers()) {
            players.add(all);
            all.teleport(Flash.getInstance().getGame().getGameMap().getSpawn());
            all.sendMessage(Locale.get(all, "gamestart_start"));
            all.getInventory().setItem(4, ItemBuilder.create(Material.RED_DYE)
                    .name(Locale.get(all, "instant-death"))
                    .build());
            all.getInventory().setItem(8, ItemBuilder.create(Material.LIME_DYE)
                    .name(Locale.get(all, "players-visible"))
                    .build());
            all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
            all.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 5));
            Flash.getInstance().getScoreboardManager().setup(all);
        }
    }

    public void end() {
        for (Player all : Bukkit.getOnlinePlayers()) {
            all.sendMessage(Locale.get(all, "gameend_stop"));
        }
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "ahstop");
    }
}
