package net.animalshomeland.flash.listener.block;

import net.animalshomeland.flash.Flash;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player p = event.getPlayer();
        if(p.getGameMode() == GameMode.CREATIVE) {
            return;
        }
        event.setCancelled(true);
    }
}
