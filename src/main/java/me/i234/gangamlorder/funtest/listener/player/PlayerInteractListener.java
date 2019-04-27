package me.i234.gangamlorder.funtest.listener.player;

import me.i234.gangamlorder.funtest.utils.Common;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.*;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void playerInteractEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if(item==null){
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
                buttonMap.put(1, Common.makeItem("&a&lHelp &6&lLpHiX", Material.DIAMOND_SWORD, Arrays.asList("&7Join LpHiX's team!", "Mobs in LpHiX's team will ignore you"), Collections.singletonList(Enchantment.PROTECTION_ENVIRONMENTAL), Collections.singletonList(1), true));
                buttonMap.put(2, Common.makeItem("&aSet nearby mobs to LpHiX's team", Material.LIME_DYE, Arrays.asList("&7In a range of 20m", "WIP"), null, null, true));
                buttonMap.put(4, Common.makeItem("&c&lHelp &c&landrewandy", Material.WOODEN_SWORD, Arrays.asList("&7Join Andy's team!", "Mobs in Andy's team will ignore you"), null, null, true));
                buttonMap.put(5, Common.makeItem("&cSet nearby mobs to Andy's team", Material.ROSE_RED, Arrays.asList("&7In a range of 20m", "WIP"), null, null, true));
                buttonMap.put(7, Common.makeItem("&cExit the menu", Material.BARRIER, null, null, null, true));
                Inventory inv = Common.makeGUI("&c&lArmy &6&lController", 9, buttonMap, null);
                player.openInventory(inv);
                break;
        }
    }
}
