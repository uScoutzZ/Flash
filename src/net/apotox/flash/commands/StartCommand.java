package net.apotox.flash.commands;

import net.apotox.flash.Flash;
import net.apotox.flash.game.GameState;
import net.apotox.gameapi.command.Command;
import net.apotox.gameapi.command.RegisterCommand;
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
