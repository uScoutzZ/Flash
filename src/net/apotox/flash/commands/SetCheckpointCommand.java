package net.apotox.flash.commands;

import net.apotox.flash.game.FlashPlayer;
import net.apotox.flash.utilities.Locale;
import net.apotox.gameapi.command.Command;
import net.apotox.gameapi.command.RegisterCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@RegisterCommand(name = "setcheckpoint", permission = "flash.command.setcheckpoint")
public class SetCheckpointCommand extends Command {

    @Override
    public void onPlayerExecute(Player player, String command, String[] args) {
        if(args.length == 2) {
            if(Bukkit.getPlayer(args[0]) != null) {
                Player target = Bukkit.getPlayer(args[0]);
                int checkpoint = Integer.parseInt(args[1]);
                FlashPlayer.getPlayer(target).setCheckpoint(checkpoint);
                player.sendMessage(Locale.get(player, "command_setcheckpoint", checkpoint));
            }
        }
    }
}
