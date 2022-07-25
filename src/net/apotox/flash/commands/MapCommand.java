package net.apotox.flash.commands;

import net.apotox.flash.Flash;
import net.apotox.flash.game.GameMap;
import net.apotox.flash.utilities.Locale;
import net.apotox.gameapi.command.Command;
import net.apotox.gameapi.command.RegisterCommand;
import org.bukkit.entity.Player;

@RegisterCommand(name = "map")
public class MapCommand extends Command {

    @Override
    public void onPlayerExecute(Player player, String command, String[] args) {
        GameMap gameMap = Flash.getInstance().getGame().getGameMap();

        player.sendMessage(Locale.get(player, "map", gameMap.getMapName(), gameMap.getMapOwner()));
    }
}
