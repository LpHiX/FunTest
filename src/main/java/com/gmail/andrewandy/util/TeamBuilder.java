package com.gmail.andrewandy.util;

import com.gmail.andrewandy.object.Team;
import me.i234.gangamlorder.funtest.utils.Common;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

import java.util.logging.Level;

public class TeamBuilder implements Listener {

    @EventHandler
    public void test(EntityTargetLivingEntityEvent event) {
        Entity entity = event.getEntity();
        Entity target = event.getTarget();

        Team entityTeam = Team.getTeamFromEntity(entity);
        Team targetTeam = Team.getTeamFromEntity(target);

        if (entityTeam == null || targetTeam == null) {
            Common.log(Level.WARNING, "One of the teams is null!");
            return;
        }

        if (entityTeam.equals(targetTeam)) {
            event.setCancelled(true);
        }

    }

}
