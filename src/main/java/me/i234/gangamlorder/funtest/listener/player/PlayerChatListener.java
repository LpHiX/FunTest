package me.i234.gangamlorder.funtest.listener.player;

import me.i234.gangamlorder.funtest.FunTest;
import me.i234.gangamlorder.funtest.listener.entities.EntityTargetLivingEntityListener;
import me.i234.gangamlorder.funtest.utils.Common;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerChatListener implements Listener {

    private Boolean helixOn = false;
    private Boolean spiralOn = false;

    @EventHandler
    public void playerChatEvent(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        String message = event.getMessage();
        String prefix = "";
        if(player.hasPermission("funtest.mod")){
            prefix = "&2[Mod] ";
        }
        if(player.hasPermission("funtest.owner")){
            prefix = "&c[Owner] ";
        }

        event.setFormat(Common.colorize("&a"+prefix+"%1$s &7: &r%2$s"));

        if (message.equals("Spinning rod helix")){
            if (helixOn){
                helixOn = false;
                return;
            }
            helixOn = true;
            new BukkitRunnable() {
                double t = 0;
                public void run() {
                    for (double y = 0; y<=2 ; y+=0.01){
                        for (double a = 0; a<2; a+=1){
                            double insideNum = t+a*3.14159+y*5;
                            double x = 0.5*y*Math.cos(insideNum);
                            double z = 0.5*y*Math.sin(insideNum);
                            Location pLoc = player.getLocation();
                            Location location = new Location(player.getWorld(), pLoc.getX()+x,pLoc.getY()+y-1,pLoc.getZ()+z);
                            Location upsideLocation = new Location(player.getWorld(), pLoc.getX()+z*-1,pLoc.getY()+(y-3)*-1,pLoc.getZ()+x*-1);
                            player.spawnParticle(Particle.FLAME, location,1, 0, 0, 0, 0);
                            player.spawnParticle(Particle.FLAME, upsideLocation,1, 0, 0, 0, 0);
                        }
                    }
                    t+=0.01;
                    if(!helixOn){
                        this.cancel();}
                }
            }.runTaskTimer(FunTest.getInstance(), 0, 1);
        }
        if (message.equals("Fountain fire spiral")){
            if (spiralOn){
                spiralOn = false;
                return;
            }
            spiralOn = true;
            new BukkitRunnable() {
                double t = 0;
                public void run() {
                    for (double y = 0; y<=2 ; y+=0.01){
                        for (double a = 0; a<2; a+=0.5){
                            double insideNum = t+a*3.14159+y*5;
                            double x = 5*y*Math.cos(insideNum);
                            double z = 5*y*Math.sin(insideNum);
                            Location pLoc = player.getLocation();
                            Location location = new Location(player.getWorld(), pLoc.getX()+x, pLoc.getY() + 2*y*Math.pow(y-2.5, 2)-0.5,pLoc.getZ()+z);
                            player.spawnParticle(Particle.FLAME, location,1, 0, 0, 0, 0);
                        }
                    }
                    t+=0.5;
                    if(!spiralOn){
                        this.cancel();}
                }
            }.runTaskTimer(FunTest.getInstance(), 0, 5);
        }

    }
}
