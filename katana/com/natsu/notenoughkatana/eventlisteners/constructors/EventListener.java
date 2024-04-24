package com.natsu.notenoughkatana.eventlisteners.constructors;

import com.natsu.notenoughkatana.NotEnoughKatanaPlugin;
import org.bukkit.event.Listener;

public abstract class EventListener implements Listener {
    private final NotEnoughKatanaPlugin plugin;

    protected EventListener(NotEnoughKatanaPlugin plugin) {
        this.plugin = plugin;
    }

    public void register(){
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }
}
