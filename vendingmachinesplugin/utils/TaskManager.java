package com.natsu.vendingmachinesplugin.utils;

import com.natsu.vendingmachinesplugin.VendingMachinesPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class TaskManager {
    private final VendingMachinesPlugin plugin;

    public TaskManager(VendingMachinesPlugin plugin) {
        this.plugin = plugin;
    }

    public void start(){
        new BukkitRunnable() {
            @Override
            public void run() {
                plugin.getGuiManager().restock();
            }
        }.runTaskTimer(plugin,0,36000);
    }

}
