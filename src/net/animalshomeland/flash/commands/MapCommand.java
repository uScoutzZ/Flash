package net.animalshomeland.flash.commands;

import net.animalshomeland.flash.Flash;
import net.animalshomeland.flash.game.GameMap;
import net.animalshomeland.flash.utilities.Locale;
import net.animalshomeland.gameapi.command.Command;
import net.animalshomeland.gameapi.command.RegisterCommand;
import org.bukkit.entity.Player;

@RegisterCommand(name = "map")
public class MapCommand extends Command {

    @Override
    public void onPlayerExecute(Player player, String command, String[] args) {
        GameMap gameMap = Flash.getInstance().getGame().getGameMap();

        player.sendMessage(Locale.get(player, "map", gameMap.getMapName(), gameMap.getMapOwner()));
    }
}
