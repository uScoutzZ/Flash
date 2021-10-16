package net.animalshomeland.flash.game;

import net.animalshomeland.flash.Flash;
import net.animalshomeland.flash.utilities.Locale;
import net.animalshomeland.gameapi.GameAPI;
import net.animalshomeland.gameapi.user.User;
import net.animalshomeland.gameapi.util.Debug;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;

public class ScoreboardManager {

    public HashMap<org.bukkit.scoreboard.Scoreboard, Player> boards = new HashMap<>();

    public void setup(Player player) {

        org.bukkit.scoreboard.Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("abcd", "abcd");

        objective.setDisplayName("§e§lANIMALSHOMELAND.NET");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.getScore("§d§8").setScore(16);

        /*LEVEL*/
        objective.getScore("§1§f" + Locale.get(player, "scoreboard_time")).setScore(15);
        Team timeTeam = scoreboard.registerNewTeam("time");
        timeTeam.addEntry("§d§9");
        objective.getScore("§d§9").setScore(14);
        objective.getScore("§d").setScore(13);

        Team checkpointTeam = scoreboard.registerNewTeam("checkpoint");
        for(Player all : Bukkit.getOnlinePlayers()) {
            checkpointTeam.addEntry(User.getFromPlayer(all).getPermissionName());
            checkpointTeam.setSuffix("§b");
            FlashPlayer flashPlayer = FlashPlayer.getPlayer(all);
            if(!flashPlayer.isSpectator()) {
                objective.getScore(all.getName()).setScore(flashPlayer.getCheckpoint());
            }
        }

        player.setScoreboard(scoreboard);

        boards.put(scoreboard, player);
        player.setScoreboard(scoreboard);
        GameAPI.getInstance().setTag();
    }

    public void update(Player player) {
        org.bukkit.scoreboard.Scoreboard board = player.getScoreboard();

        Objective objective = board.getObjective("abcd");
        Team timeTeam = board.getTeam("time");
        long[] duration = Flash.getInstance().getGame().getGameCountdown().getDuration();
        timeTeam.setPrefix("§e" + String.format("%02d:%02d", duration[0] / 60, duration[0] % 60));

        Team checkpointTeam = board.getTeam("checkpoint");
        for(Player all : Bukkit.getOnlinePlayers()) {
            checkpointTeam.addEntry(User.getFromPlayer(all).getPermissionName());
            FlashPlayer flashPlayer = FlashPlayer.getPlayer(all);
            if(!flashPlayer.isSpectator()) {
                objective.getScore(all.getName()).setScore(flashPlayer.getCheckpoint());
            }
        }
    }
}
