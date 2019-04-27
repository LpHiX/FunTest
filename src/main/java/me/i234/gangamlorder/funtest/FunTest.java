package me.i234.gangamlorder.funtest;

import com.gmail.andrewandy.object.TeamManager;
import me.i234.gangamlorder.funtest.listener.entities.EntityDamageByEntityListener;
import me.i234.gangamlorder.funtest.listener.entities.ProjectileHitListener;
import me.i234.gangamlorder.funtest.listener.player.PlayerInteractListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class FunTest extends JavaPlugin {

    private static FunTest instance;
    private static TeamManager manager = new TeamManager();

    public static FunTest getInstance() {
        return instance;
    }

    public static TeamManager getManager() {
        return manager;
    }

    @Override
    public void onEnable() {
        instance = this;
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerInteractListener(), this);
        getLogger().log(Level.INFO, "PlayerInteractListener successfully registered.");
        pluginManager.registerEvents(new ProjectileHitListener(), this);
        getLogger().log(Level.INFO, "ProjectileHitListener successfully registered.");
        pluginManager.registerEvents(new EntityDamageByEntityListener(), this);
        getLogger().log(Level.INFO, "EntityDamageByEntityListener successfully registered.");
        getLogger().log(Level.INFO, "Successfully enabled");


    }

    @Override
    public void onDisable() {
        instance = null;
        getLogger().log(Level.INFO, "Successfully disabled  ");
    }
}
