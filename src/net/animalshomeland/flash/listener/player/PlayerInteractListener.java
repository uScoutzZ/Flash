package net.animalshomeland.flash.listener.player;

import net.animalshomeland.flash.Flash;
import net.animalshomeland.flash.game.FlashPlayer;
import net.animalshomeland.flash.game.GameState;
import net.animalshomeland.flash.utilities.Locale;
import net.animalshomeland.gameapi.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if(event.getAction() == Action.PHYSICAL) {
            if(Flash.getInstance().getGame().getGameState() == GameState.INGAME) {
                FlashPlayer flashPlayer = FlashPlayer.getPlayer(player);
                if(!flashPlayer.isSpectator() && player.getGameMode() != GameMode.SPECTATOR) {
                    if(event.getClickedBlock().getType() == Material.STONE_PRESSURE_PLATE) {
                        Location corner1 = Flash.getInstance().getGame().getGameMap().getCornerpoints().get(flashPlayer.getCheckpoint()+1).get(0);
                        Location corner2 = Flash.getInstance().getGame().getGameMap().getCornerpoints().get(flashPlayer.getCheckpoint()+1).get(1);
                        if(player.getLocation().getBlockX() >= Math.min(corner1.getBlockX(), corner2.getBlockX())
                                && player.getLocation().getBlockX() <= Math.max(corner1.getBlockX(), corner2.getBlockX())
                                && player.getLocation().getBlockZ() >= Math.min(corner1.getBlockZ(), corner2.getBlockZ())
                                && player.getLocation().getBlockZ() <= Math.max(corner1.getBlockZ(), corner2.getBlockZ())) {
                            if(!(flashPlayer.getReachedCheckpoints().contains(corner1))) {
                                flashPlayer.getReachedCheckpoints().add(corner1);
                                flashPlayer.setCheckpoint(flashPlayer.getCheckpoint()+1);
                                for(Player all : Bukkit.getOnlinePlayers()) {
                                    all.sendMessage(Locale.get(all, "checkpoint_reached", player.getName(), flashPlayer.getCheckpoint(),
                                            flashPlayer.getCheckpoint(), Flash.getInstance().getGame().getGameMap().getCheckpoints().size()));
                                }
                            }
                        }
                    } else if(event.getClickedBlock().getType() == Material.OAK_PRESSURE_PLATE) {
                        if(flashPlayer.getCheckpoint() == Flash.getInstance().getGame().getGameMap().getCheckpoints().size()) {
                            if(Flash.getInstance().getGame().getWinners().size() == 0) {
                                if(Flash.getInstance().getGame().getGameCountdown().getTime() > 60) {
                                    Flash.getInstance().getGame().getGameCountdown().setTime(60);
                                }
                                Flash.getInstance().getGame().getWinners().add(player.getDisplayName());
                                for(Player all : Bukkit.getOnlinePlayers()) {
                                    all.sendMessage(Locale.get(all, "player-finished-first", player.getName(), Flash.getInstance().getGame().getGameCountdown().getTime()));
                                }
                            } else {
                                for(Player all : Bukkit.getOnlinePlayers()) {
                                    all.sendMessage(Locale.get(all, "player-finished", player.getName()));
                                }
                            }
                            if(Flash.getInstance().getGame().getWinners().size() == Flash.getInstance().getGame().getPlayers().size()) {
                                for(Player all : Bukkit.getOnlinePlayers()) {
                                    all.sendMessage(Locale.get(all, "all-finished"));
                                }
                                Flash.getInstance().getGame().getGameCountdown().setTime(0);
                            }
                            player.setGameMode(GameMode.SPECTATOR);
                            player.getInventory().clear();
                            player.getActivePotionEffects().clear();
                        } else {
                            player.sendMessage(Locale.get(player, "not-all-points-reached"));
                        }
                    }
                }
            }
        } else {
            if(event.getItem() != null) {
                if(event.getItem().getItemMeta() != null) {
                    if(event.getItem().getType() == Material.RED_DYE) {
                        FlashPlayer.getPlayer(player).teleportCheckpoint();
                    } else if(event.getItem().getType() == Material.GRAY_DYE) {
                        player.getInventory().setItem(player.getInventory().getHeldItemSlot(), ItemBuilder.create(Material.LIME_DYE)
                                .name(Locale.get(player, "players-visible"))
                                .build());
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            player.showPlayer(all);
                        }
                    } else if(event.getItem().getType() == Material.LIME_DYE) {
                        player.getInventory().setItem(player.getInventory().getHeldItemSlot(), ItemBuilder.create(Material.GRAY_DYE)
                                .name(Locale.get(player, "players-invisible"))
                                .build());
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            player.hidePlayer(all);
                        }
                    }
                }
            }

            if(player.getGameMode() != GameMode.CREATIVE) {
                if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onInteractEntity(PlayerInteractAtEntityEvent event) {
        if(event.getRightClicked().getType() == EntityType.ARMOR_STAND) {
            event.setCancelled(true);
        }
    }
}
