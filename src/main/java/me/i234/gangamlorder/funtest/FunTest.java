package me.i234.gangamlorder.funtest;

import me.i234.gangamlorder.funtest.listener.entities.EntityDamageByEntityListener;
import me.i234.gangamlorder.funtest.listener.entities.EntityTargetLivingEntityListener;
import me.i234.gangamlorder.funtest.listener.entities.ProjectileHitListener;
import me.i234.gangamlorder.funtest.listener.player.PlayerInteractListener;
import me.i234.gangamlorder.funtest.object.Team;
import me.i234.gangamlorder.funtest.object.TeamManager;
import me.i234.gangamlorder.funtest.utils.Common;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class FunTest extends JavaPlugin {

    private static FunTest instance;

    private static TeamManager teamManager = new TeamManager();

    public static TeamManager getTeamManager() {
        return teamManager;
    }

    public static FunTest getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerInteractListener(), this);
        pluginManager.registerEvents(new ProjectileHitListener(), this);
        pluginManager.registerEvents(new EntityDamageByEntityListener(), this);
        pluginManager.registerEvents(new EntityTargetLivingEntityListener(), this);
        Common.log(Level.INFO, "Successfully Enabled!");

        Team L = new Team("LpHiX");
        teamManager.createTeam(L);

        Team Andy = new Team("Andy");
        teamManager.createTeam(Andy);


    }

    @Override
    public void onDisable() {
        instance = null;
        getLogger().log(Level.INFO, "Successfully disabled  ");
    }
}
