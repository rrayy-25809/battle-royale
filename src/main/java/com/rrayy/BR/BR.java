package com.rrayy.BR;

import org.bukkit.plugin.java.JavaPlugin;

import com.rrayy.BR.FightMode.FightMode;


public class BR extends JavaPlugin {
    private FightMode FightMode;

    @Override
    public void onEnable() { // Plugin startup logic
        getLogger().info("Battle Royal has been enabled!");
        FightMode = new FightMode(this);
        getServer().getPluginManager().registerEvents(FightMode.Event, this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Battle Royal has been disabled!");
    }
}
