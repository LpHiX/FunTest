package me.i234.gangamlorder.funtest;

import me.i234.gangamlorder.funtest.listener.PlayerInteractListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class FunTest extends JavaPlugin{

    private static FunTest instance;

    @Override
    public void onEnable() {
        instance = this;
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerInteractListener(), this);
        getLogger().log(Level.INFO, "PlayerInteractListener successfully registered.");
        getLogger().log(Level.INFO, "Successfully enabled");


    }

    @Override
    public void onDisable() {
        instance = null;
        getLogger().log(Level.INFO, "Successfully disabled");
    }
}
