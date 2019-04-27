package me.i234.gangamlorder.funtest.listener.entities;

import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

public class EntityDamageByEntityListener implements Listener {

    @EventHandler
    public void entityDamageByEntityEvent(EntityDamageByEntityEvent event){
        Entity damager = event.getDamager();
        Entity damaged = event.getEntity();
        if (!damager.getType().equals(EntityType.SNOWBALL)) {
            return;
        }

        if (damaged instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) damaged;
            //Do code
        }



        for (double y = 0; y <= 2; y += 0.01) {
            double x = 1 * Math.cos(y * 10);
            double z = 1 * Math.sin(y * 10);
            damaged.getWorld().spawnParticle(Particle.FLAME, damaged.getLocation().getX() + x, damaged.getLocation().getY() + y, damaged.getLocation().getZ() + z, 10, 0, 0, .0, 0);
            Vector stopped = new Vector(0, 0, 0);
            damaged.setVelocity(stopped);
            damaged.setFireTicks(60);
        }
    }
}
