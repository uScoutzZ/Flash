package net.animalshomeland.flash.commands;

import net.animalshomeland.flash.utilities.Locale;
import net.animalshomeland.gameapi.command.Command;
import net.animalshomeland.gameapi.command.RegisterCommand;
import org.bukkit.entity.Player;

@RegisterCommand(name = "stats")
public class StatsCommand extends Command {

    @Override
    public void onPlayerExecute(Player player, String command, String[] args) {
        player.sendMessage(Locale.get(player, "no-stats"));
    }
}
