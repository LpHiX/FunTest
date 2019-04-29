package me.i234.gangamlorder.funtest.listener.player;

import me.i234.gangamlorder.funtest.FunTest;
import me.i234.gangamlorder.funtest.object.Team;
import me.i234.gangamlorder.funtest.utils.Common;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.logging.Level;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void playerInteractEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if (item == null) {
            return;
        }
        switch (item.getType()) {
            // GRAY DYE
            default:
                break;
            case GRAY_DYE:
                if (event.getAction().equals(Action.LEFT_CLICK_AIR) | event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                    List<Block> blockList = player.getLineOfSight(null, 100);
                    for (Block block : blockList) {
                        if (block.getType().equals(Material.AIR)) {
                            continue;
                        }
                        //else if(player.getWorld().getBlockAt(block.getX(),block.getY()+1,block.getZ()).getType().equals(Material.AIR)) {
                        Location location = new Location(player.getWorld(), block.getX(), block.getY() + 1, block.getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());
                        player.teleport(location);
                        //}
                    }
                    event.setCancelled(true);
                }
                break;
            // GHAST TEAR
            case GHAST_TEAR:
                if (!event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                    return;
                }
                Location upToEye = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY() + 1.5, player.getLocation().getZ());
                for (int index = 0; index < 25; index++) {
                    Snowball snowball = player.getWorld().spawn(upToEye, Snowball.class);
                    snowball.setShooter(player);
                    Vector vector = player.getLocation().getDirection();
                    vector.multiply(2.5);
                    vector.add(new Vector(new Random().nextGaussian() * 0.3, new Random().nextGaussian() * 0.3, new Random().nextGaussian() * 0.3));
                    snowball.setVelocity(vector);
                }
                item.setAmount(item.getAmount() - 1);
                break;
            case BLAZE_ROD:
                if (!event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                    return;
                }
                if (!player.hasPermission("stop.stop")) {
                    player.sendMessage(Common.colorize("&cYou do not have permission to do this"));
                    return;
                }
                Map<Integer, ItemStack> buttonMap = new HashMap<>();
                buttonMap.put(1, Common.makeItem("&a&lHelp &6&lLpHiX", Material.DIAMOND_SWORD, Arrays.asList("&7Join LpHiX's team!", "Mobs in LpHiX's team will ignore you"), Collections.singletonMap(Enchantment.PROTECTION_ENVIRONMENTAL, 1), true));
                buttonMap.put(2, Common.makeItem("&aSet nearby mobs to LpHiX's team", Material.LIME_DYE, Arrays.asList("&7In a range of 20m", "WIP"), null, true));
                buttonMap.put(4, Common.makeItem("&c&lHelp &c&landrewandy", Material.WOODEN_SWORD, Arrays.asList("&7Join Andy's team!", "Mobs in Andy's team will ignore you"), null, true));
                buttonMap.put(5, Common.makeItem("&cSet nearby mobs to Andy's team", Material.ROSE_RED, Arrays.asList("&7In a range of 20m", "WIP"), null, true));
                buttonMap.put(7, Common.makeItem("&cExit the menu", Material.BARRIER, null, null, true));
                Inventory inv = Common.makeGUI("&c&lArmy &6&lController", 9, buttonMap, null);
                player.openInventory(inv);

                class ClickListener implements Listener {
                    @EventHandler
                    public void clickListener(InventoryClickEvent event) {
                        if (!event.getInventory().equals(inv)) {
                            return;
                        }
                        event.setCancelled(true);
                        switch (event.getSlot()) {
                            default:
                                break;
                            case 1:
                                Team.getTeamByName("LpHiX").addMember(player);
                                Common.log(Level.INFO, "&aAdded player to LpHiX's team: &e" + player);
                                if (!Team.getTeamByName("Andy").hasMember(player)) {
                                    Common.log(Level.INFO, "&cThis person is not in Andy's team");
                                    break;
                                }
                                Common.log(Level.INFO, "&cThis person is in Andy's team");
                                Team.getTeamByName("Andy").removeMember(player);
                                break;
                            case 2:
                                for (Entity mobs : player.getNearbyEntities(20, 20, 20)) {
                                    if (!(mobs instanceof LivingEntity)) {
                                        break;
                                    }
                                    Team.getTeamByName("LpHiX").addMember(mobs);
                                    Common.log(Level.INFO, "&aAdded entity to LpHiX's team: &e" + mobs.getType().toString());
                                    if (!Team.getTeamByName("Andy").hasMember(mobs)) {
                                        Common.log(Level.INFO, "&2This ENTITY is not in Andy's team");
                                        continue;
                                    }
                                    Common.log(Level.INFO, "&2This ENTITY is in Andy's team");
                                    Team.getTeamByName("Andy").removeMember(mobs);
                                }
                                break;
                            case 4:
                                Team.getTeamByName("Andy").addMember(player);
                                Common.log(Level.INFO, "&aAdded player to Andy's team: &e" + player);
                                if (!Team.getTeamByName("LpHiX").hasMember(player)) {
                                    Common.log(Level.INFO, "&cThis person is not in LpHiX's team");
                                    break;
                                }
                                Common.log(Level.INFO, "&cThis person is in LpHiX's team");
                                Team.getTeamByName("LpHiX").removeMember(player);
                                break;
                            case 5:
                                for (Entity mobs : player.getNearbyEntities(20, 20, 20)) {
                                    if (!(mobs instanceof LivingEntity)) {
                                        break;
                                    }
                                    Team.getTeamByName("Andy").addMember(mobs);
                                    Common.log(Level.INFO, "&aAdded entity to Andy's team: &e" + mobs.getType().toString());
                                    if (!Team.getTeamByName("LpHiX").hasMember(mobs)) {
                                        Common.log(Level.INFO, "&2This ENTITY is not in Andy's team");
                                        continue;
                                    }
                                    Common.log(Level.INFO, "&2This ENTITY is in Andy's team");
                                    Team.getTeamByName("LpHiX").removeMember(mobs);
                                }
                                break;
                            case 6:
                                player.closeInventory();
                                break;
                        }
                    }
                }
                FunTest.getInstance().getServer().getPluginManager().registerEvents(new ClickListener(), FunTest.getInstance());
                break;
        }
    }
}
