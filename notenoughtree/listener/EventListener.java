package com.mrnatsu.notenoughtree.listener;

import com.mrnatsu.notenoughtree.NotEnoughTree;
import org.bukkit.event.Listener;

public abstract class EventListener implements Listener {
    protected final NotEnoughTree plugin;

    protected EventListener(NotEnoughTree plugin) {
        this.plugin = plugin;
    }

    public void register(){
        this.plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }
}
