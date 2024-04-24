package com.natsu.notenoughkatana.core.tasks;

import com.natsu.notenoughkatana.NotEnoughKatanaPlugin;
import com.natsu.notenoughkatana.core.objects.Skill;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class CDManager {
    private final NotEnoughKatanaPlugin plugin;
    private final Map<String, Long> coolDownMap = new HashMap<>();

    public CDManager(NotEnoughKatanaPlugin plugin) {
        this.plugin = plugin;
    }


    private String encode(UUID uuid, String id, Skill skill){
        return uuid.toString()+skill+id;
    }

    public boolean isOnCoolDown(OfflinePlayer player, String id, Skill skill){return isOnCoolDown(player.getUniqueId(), id, skill);}
    public boolean isOnCoolDown(UUID uuid, String id, Skill skill){
        return this.coolDownMap.containsKey(encode(uuid, id, skill));
    }

    public boolean isOnCoolDown(UUID uuid, String id){
        return this.coolDownMap.containsKey(id);
    }

    public boolean addCoolDown(OfflinePlayer player, String id, Skill skill, long tick){return addCoolDown(player.getUniqueId(), id, skill, tick);}
    public boolean addCoolDown(UUID uuid, String id, Skill skill, long tick){
        if(isOnCoolDown(uuid, id,skill)) {
            return false;
        }
        if(isWearingCrown(uuid)){
            tick/=2;
        }
        if(tick==0)
            return true;
        String key = encode(uuid, id,skill);
        this.coolDownMap.put(key, new Date().getTime()+tick/20*1000);
        new BukkitRunnable() {
            @Override
            public void run() {
                coolDownMap.remove(key);
            }
        }.runTaskLaterAsynchronously(this.plugin, tick);
        return true;
    }

    public boolean addCoolDown(UUID uuid, String id, long tick){
        if(isOnCoolDown(uuid, id)) {
            return false;
        }
        if(isWearingCrown(uuid)){
            tick/=2;
        }
        if(tick==0)
            return true;
        this.coolDownMap.put(id, new Date().getTime()+tick/20*1000);
        new BukkitRunnable() {
            @Override
            public void run() {
                coolDownMap.remove(id);
            }
        }.runTaskLaterAsynchronously(this.plugin, tick);
        return true;
    }
    public long getCoolDown(OfflinePlayer player, String id, Skill skill){
        return getCoolDown(player.getUniqueId(), id, skill);
    }
    public long getCoolDown(UUID uuid, String id, Skill skill){
        if(!isOnCoolDown(uuid, id, skill))
            return 0;
        return -(int)(new Date().getTime()-this.coolDownMap.get(encode(uuid, id,skill)))/1000;
    }

    public long getCoolDown(UUID uuid, String id){
        if(!isOnCoolDown(uuid, id))
            return 0;
        return -(int)(new Date().getTime()-this.coolDownMap.get(id))/1000;
    }

    private boolean isWearingCrown(UUID uuid){
        OfflinePlayer offlinePlayer = Bukkit.getPlayer(uuid);
        if(offlinePlayer == null)
            return false;
        if(!offlinePlayer.isOnline())
            return false;
        if(offlinePlayer.getPlayer()==null)
            return false;
        ItemStack item = Objects.requireNonNull(offlinePlayer.getPlayer()).getInventory().getHelmet();
        return this.plugin.getItem().isCrown(item);
    }
}
