package me.i234.gangamlorder.funtest.listener.player;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Random;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void playerInteractEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if(item==null){
            return;
        }
        // GRAY DYE
        if(item.getType().equals(Material.GRAY_DYE)) {
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
        }

        //
        if(item.getType().equals(Material.GHAST_TEAR)) {
            Location upToEye = new Location(player.getWorld(), player.getLocation().getX(),player.getLocation().getY()+1.5,player.getLocation().getZ());
            for (int index = 0; index<25; index++) {
                Snowball snowball = player.getWorld().spawn(upToEye, Snowball.class);
                snowball.setShooter(player);
                Vector vector = player.getLocation().getDirection();
                vector.multiply(2.5);
                vector.add(new Vector(new Random().nextGaussian()*0.3, new Random().nextGaussian()*0.3, new Random().nextGaussian()*0.3));
                snowball.setVelocity(vector);
            }
        }
    }
}
