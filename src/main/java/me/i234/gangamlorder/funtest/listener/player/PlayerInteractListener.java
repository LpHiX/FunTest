package me.i234.gangamlorder.funtest.listener.player;

import me.i234.gangamlorder.funtest.FunTest;
import me.i234.gangamlorder.funtest.object.Team;
import me.i234.gangamlorder.funtest.utils.Common;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
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
                        Team TeamL = Team.getTeamByName("LpHiX");
                        Team TeamA = Team.getTeamByName("Andy");
                        if (TeamL == null || TeamA == null) {
                            throw new IllegalStateException("Team is null");
                        }
                        switch (event.getSlot()) {
                            default:
                                break;
                            case 1:
                                TeamL.addMember(player);
                                Common.log(Level.INFO, "&aAdded player to LpHiX's team: &e" + player);
                                if (!TeamA.hasMember(player)) {
                                    Common.log(Level.INFO, "&cThis person is not in Andy's team");
                                    break;
                                }
                                Common.log(Level.INFO, "&cThis person is in Andy's team");
                                TeamA.removeMember(player);
                                break;
                            case 2:
                                Common.log(Level.INFO, "10");
                                for (Entity mobs : player.getNearbyEntities(10, 10, 10)) {
                                    Common.log(Level.INFO, "20");
                                    TeamL.addMember(mobs);
                                    Common.log(Level.INFO, "&aAdded entity to LpHiX's team: &e" + mobs.getType().toString());
                                    for (double y = 0; y >= -2; y -= 0.01) {
                                        double x = y * Math.cos(y * 10);
                                        double z = y * Math.sin(y * 10);
                                        Location particleLocation = new Location(mobs.getWorld(), mobs.getLocation().getX() + x, mobs.getLocation().getY() + y + 2, mobs.getLocation().getZ() + z);
                                        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(0, 255, 0), 3);
                                        mobs.getWorld().spawnParticle(Particle.REDSTONE, particleLocation, 2, dustOptions);
                                    }
                                    if (!TeamA.hasMember(mobs)) {
                                        Common.log(Level.INFO, "&2This ENTITY is not in Andy's team");
                                        continue;
                                    }
                                    Common.log(Level.INFO, "&2This ENTITY is in Andy's team");
                                    TeamA.removeMember(mobs);
                                }
                                break;
                            case 4:
                                TeamA.addMember(player);
                                Common.log(Level.INFO, "&aAdded player to Andy's team: &e" + player);
                                if (!TeamL.hasMember(player)) {
                                    Common.log(Level.INFO, "&cThis person is not in LpHiX's team");
                                    break;
                                }
                                Common.log(Level.INFO, "&cThis person is in LpHiX's team");
                                TeamL.removeMember(player);
                                break;
                            case 5:
                                for (Entity mobs : player.getNearbyEntities(10, 10, 10)) {
                                    TeamA.addMember(mobs);
                                    Common.log(Level.INFO, "&aAdded entity to Andy's team: &e" + mobs.getType().toString());
                                    for (double y = 0; y >= -2; y -= 0.01) {
                                        double x = y * Math.cos(y * 10);
                                        double z = y * Math.sin(y * 10);
                                        Common.log(Level.INFO, "x= " + x + " y= " + y + " z= " + z);
                                        Location particleLocation = new Location(mobs.getWorld(), mobs.getLocation().getX() + x, mobs.getLocation().getY() + y + 2, mobs.getLocation().getZ() + z);
                                        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(255, 0, 0), 3);
                                        mobs.getWorld().spawnParticle(Particle.REDSTONE, particleLocation, 2, dustOptions);
                                    }
                                    if (!TeamL.hasMember(mobs)) {
                                        Common.log(Level.INFO, "&2This ENTITY is not in Andy's team");
                                        continue;
                                    }
                                    Common.log(Level.INFO, "&2This ENTITY is in LpHiX's team");
                                    TeamL.removeMember(mobs);
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
