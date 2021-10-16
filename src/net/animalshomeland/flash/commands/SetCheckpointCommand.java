package net.animalshomeland.flash.commands;


import net.animalshomeland.flash.Flash;
import net.animalshomeland.flash.game.FlashPlayer;
import net.animalshomeland.flash.utilities.Locale;
import net.animalshomeland.gameapi.command.Command;
import net.animalshomeland.gameapi.command.RegisterCommand;
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
