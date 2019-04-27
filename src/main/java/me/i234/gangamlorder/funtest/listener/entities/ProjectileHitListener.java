package me.i234.gangamlorder.funtest.listener.entities;

import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ProjectileHitListener implements Listener {

    @EventHandler
    public void projectileHitEvent(ProjectileHitEvent event) {
        Entity entity = event.getEntity();
        if (!entity.getType().equals(EntityType.SNOWBALL)) {
            return;
        }
        entity.getWorld().spawnParticle(Particle.LAVA, entity.getLocation(), 3, 0.5, 0.5, 0.5);
        entity.getWorld().spawnParticle(Particle.SMOKE_NORMAL, entity.getLocation(), 3, 0.5, 0.5, 0.5);
    }
}
