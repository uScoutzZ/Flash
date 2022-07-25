package net.apotox.flash.commands;

import net.apotox.flash.utilities.Locale;
import net.apotox.gameapi.command.Command;
import net.apotox.gameapi.command.RegisterCommand;
import org.bukkit.entity.Player;

@RegisterCommand(name = "stats")
public class StatsCommand extends Command {

    @Override
    public void onPlayerExecute(Player player, String command, String[] args) {
        player.sendMessage(Locale.get(player, "no-stats"));
    }
}
