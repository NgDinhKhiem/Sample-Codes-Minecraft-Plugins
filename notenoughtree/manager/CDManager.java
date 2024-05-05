package com.mrnatsu.notenoughtree.manager;

import com.mrnatsu.notenoughtree.NotEnoughTree;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CDManager {
    private final NotEnoughTree plugin;
    private final Map<String, Long> coolDownMap = new HashMap<>();

    public CDManager(NotEnoughTree plugin) {
        this.plugin = plugin;
    }
    public boolean isOnCoolDown(UUID uuid, String id){
        return this.coolDownMap.containsKey(uuid+id);
    }
    public boolean addCoolDown(UUID uuid, String id, long tick) {
        if (isOnCoolDown(uuid, id)) {
            return false;
        }
        if (tick == 0)
            return true;
        this.coolDownMap.put(uuid + id, new Date().getTime() + tick / 20 * 1000);
        new BukkitRunnable() {
            @Override
            public void run() {
                coolDownMap.remove(uuid + id);
            }
        }.runTaskLaterAsynchronously(this.plugin, tick);
        return true;
    }
    public long getCoolDown(UUID uuid, String id) {
        if (!isOnCoolDown(uuid, id))
            return 0;
        return -(int) (new Date().getTime() - this.coolDownMap.get(uuid + id)) / 1000;
    }
}
