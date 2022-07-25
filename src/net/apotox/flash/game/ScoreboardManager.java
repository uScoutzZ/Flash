package net.apotox.flash.game;

import net.apotox.flash.Flash;
import net.apotox.flash.utilities.Locale;
import net.apotox.gameapi.GameAPI;
import net.apotox.gameapi.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;

public class ScoreboardManager {

    public HashMap<org.bukkit.scoreboard.Scoreboard, Player> boards = new HashMap<>();

    public void setup(Player player) {

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("abcd", "abcd");

        objective.setDisplayName("§lAPOTOX.NET");
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
        Scoreboard board = player.getScoreboard();

        Objective objective = board.getObjective("abcd");
        Team timeTeam = board.getTeam("time");
        int duration = Flash.getInstance().getGame().getGameCountdown().getTime();
        timeTeam.setPrefix("§e" + String.format("%02d:%02d", duration / 60, duration % 60));

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
