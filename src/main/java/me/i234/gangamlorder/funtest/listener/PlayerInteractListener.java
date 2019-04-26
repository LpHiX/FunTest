package me.i234.gangamlorder.funtest.listener;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void playerInteractEvent(PlayerInteractEvent event){
        Player player = event.getPlayer();
        List<Block> blockList = player.getLineOfSight(null, 20);
        for(int i = 0; i<=20; i++){
            player.sendMessage(blockList.get(i).getBlockData().getMaterial().name()+" "+i);
        }

    }
}
