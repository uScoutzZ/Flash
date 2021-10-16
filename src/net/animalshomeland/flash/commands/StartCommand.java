package net.animalshomeland.flash.commands;

import net.animalshomeland.flash.Flash;
import net.animalshomeland.gameapi.command.Command;
import net.animalshomeland.gameapi.command.RegisterCommand;
import org.bukkit.entity.Player;

@RegisterCommand(name = "start", permission = "flash.command.start")
public class StartCommand extends Command {

    @Override
    public void onPlayerExecute(Player player, String command, String[] args) {
        if(Flash.getInstance().getGame().getGameCountdown().getLobbyTask() == null ||
                Flash.getInstance().getGame().getGameCountdown().getLobbyTask().isCancelled()) {
            Flash.getInstance().getGame().getGameCountdown().startLobbyCounter();
        }
        Flash.getInstance().getGame().getGameCountdown().setLobbyTime(3);
    }
}
