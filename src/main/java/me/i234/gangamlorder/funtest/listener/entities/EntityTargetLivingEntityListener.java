package me.i234.gangamlorder.funtest.listener.entities;

import me.i234.gangamlorder.funtest.object.Team;
import me.i234.gangamlorder.funtest.utils.Common;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

import java.util.TreeMap;
import java.util.logging.Level;

public class EntityTargetLivingEntityListener implements Listener {

    public static void findNewTarget() {
        Team LTeam = Team.getTeamByName("LpHiX");
        Team ATeam = Team.getTeamByName("Andy");
        World world = Bukkit.getWorld("world");
        if (world == null) {
            throw new IllegalStateException("World is null");
        }
        for (LivingEntity nonLivingEntity : world.getLivingEntities()) {
            EntityType enType = nonLivingEntity.getType();
            if (enType.equals(EntityType.PLAYER) || enType.equals(EntityType.BAT) || enType.equals(EntityType.PHANTOM) || enType.equals(EntityType.SLIME)) {
                continue;
            }
            Creature entity = (Creature) nonLivingEntity;
            Team entityTeam = Team.getTeamByEntity(entity);
            if (entityTeam == null) {
                continue;
            }
            if (entityTeam.equals(LTeam)) {
                TreeMap<Double, Entity> nearbyEntities = new TreeMap<>();
                for (Entity singleEntity : entity.getNearbyEntities(20, 20, 20)) {
                    double x1 = singleEntity.getLocation().getX();
                    double y1 = singleEntity.getLocation().getY();
                    double z1 = singleEntity.getLocation().getZ();
                    double x2 = entity.getLocation().getX();
                    double y2 = entity.getLocation().getY();
                    double z2 = entity.getLocation().getZ();
                    Double distance = Math.sqrt((Math.abs(
                            Math.pow(x1 - x2, 2)
                                    + Math.pow(y1 - y2, 2)
                                    + Math.pow(z1 - z2, 2))));
                    nearbyEntities.put(distance, singleEntity);
                    Common.log(Level.INFO, "&aGetting entity&e: " + distance + " &aMob&e: " + singleEntity.getType().toString());
                }
                for (Entity singleEntity : nearbyEntities.values()) {
                    if (Team.getTeamByEntity(singleEntity) != ATeam) {
                        continue;
                    }
                    if (!(singleEntity instanceof LivingEntity)) {
                        continue;
                    }
                    Common.log(Level.INFO, "&cSetting Target&e:" + singleEntity.getType().toString());
                    entity.setTarget((LivingEntity) singleEntity);
                }
            }
            if (entityTeam.equals(ATeam)) {
                TreeMap<Double, Entity> nearbyEntities = new TreeMap<>();
                for (Entity singleEntity : entity.getNearbyEntities(20, 20, 20)) {
                    double x1 = singleEntity.getLocation().getX();
                    double y1 = singleEntity.getLocation().getY();
                    double z1 = singleEntity.getLocation().getZ();
                    double x2 = entity.getLocation().getX();
                    double y2 = entity.getLocation().getY();
                    double z2 = entity.getLocation().getZ();
                    Double distance = Math.sqrt((Math.abs(
                            Math.pow(x1 - x2, 2)
                                    + Math.pow(y1 - y2, 2)
                                    + Math.pow(z1 - z2, 2))));
                    nearbyEntities.put(distance, singleEntity);
                    Common.log(Level.INFO, "&cGetting entity&e: " + distance + " &aMob&e: " + singleEntity.getType().toString());
                }
                for (Entity singleEntity : nearbyEntities.values()) {
                    if (Team.getTeamByEntity(singleEntity) != LTeam) {
                        continue;
                    }
                    if (!(singleEntity instanceof LivingEntity)) {
                        continue;
                    }
                    Common.log(Level.INFO, "&cSetting Target&e:" + singleEntity.getType().toString());
                    entity.setTarget((LivingEntity) singleEntity);
                }
            }
        }
    }

    @EventHandler
    public void entityTargetLivingEntityEvent(EntityTargetLivingEntityEvent event) {
        LivingEntity target = event.getTarget();
        Entity nonLivingEntity = event.getEntity();
        if (!(nonLivingEntity instanceof LivingEntity)) {
            return;
        }
        LivingEntity entity = (LivingEntity) nonLivingEntity;
        Team targetTeam = Team.getTeamByEntity(target);
        Team entityTeam = Team.getTeamByEntity(entity);
        Common.log(Level.INFO, "1");
        if (targetTeam == null | entityTeam == null) {
            Common.log(Level.INFO, "2");
            return;
        }
        Common.log(Level.INFO, "&aTarget team=&e " + targetTeam.getName() + " &bOrigin team = &c" + entityTeam.getName());
        Common.log(Level.INFO, "3");
        if (targetTeam.equals(entityTeam)) {
            event.setCancelled(true);
        }

    }
}
