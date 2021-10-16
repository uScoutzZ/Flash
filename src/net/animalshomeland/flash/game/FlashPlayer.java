package net.animalshomeland.flash.game;

import lombok.Getter;
import lombok.Setter;
import net.animalshomeland.flash.Flash;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlashPlayer {

    private static Map<Player, FlashPlayer> players = new HashMap<>();

    @Getter @Setter
    private Player player;
    @Getter @Setter
    private int checkpoint;
    @Getter
    private List<Location> reachedCheckpoints;
    @Getter @Setter
    private boolean spectator;

    public FlashPlayer(Player player) {
        this.player = player;
        spectator = false;
        checkpoint = 0;
        players.put(player, this);
        reachedCheckpoints = new ArrayList<>();
    }

    public void teleportCheckpoint() {
        player.setFireTicks(0);
        if(checkpoint == 0) {
            player.teleport(Flash.getInstance().getGame().getGameMap().getSpawn());
        } else {
            player.teleport(Flash.getInstance().getGame().getGameMap().getCheckpoints().get(checkpoint-1));
        }
    }

    public static FlashPlayer getPlayer(Player player) {
        return players.get(player);
    }
}
