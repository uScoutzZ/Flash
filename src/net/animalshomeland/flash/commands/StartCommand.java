package net.animalshomeland.flash.commands;

import net.animalshomeland.flash.Flash;
import net.animalshomeland.flash.game.GameState;
import net.animalshomeland.gameapi.command.Command;
import net.animalshomeland.gameapi.command.RegisterCommand;
import org.bukkit.entity.Player;

@RegisterCommand(name = "start", permission = "flash.command.start")
public class StartCommand extends Command {

    @Override
    public void onPlayerExecute(Player player, String command, String[] args) {
        if(Flash.getInstance().getGame().getGameState() == GameState.LOBBY) {
            Flash.getInstance().getGame().getGameCountdown().setForced(true);
        }
        Flash.getInstance().getGame().getGameCountdown().setTime(3);
    }
}
