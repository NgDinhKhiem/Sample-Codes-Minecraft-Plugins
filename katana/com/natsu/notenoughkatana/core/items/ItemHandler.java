package com.natsu.notenoughkatana.core.items;

import com.natsu.notenoughkatana.NotEnoughKatanaPlugin;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

import static com.natsu.notenoughkatana.utils.Utils.colorParse;

public class ItemHandler implements Listener {
    private final NotEnoughKatanaPlugin plugin;
    private final Item item;

    public ItemHandler(NotEnoughKatanaPlugin plugin, Item item) {
        this.plugin = plugin;
        this.item = item;
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }
    private final Set<UUID> protectedUUIDs = new HashSet<>();

    @EventHandler
    private void onUsingTridentAbility(PlayerInteractEvent event) {
    }

    @EventHandler
    private void onUsingScytheAbility(PlayerInteractEvent event) {
    }

    @EventHandler
    private void onUsingWitheredAxeAbility(PlayerInteractEvent event) {

    }

    @EventHandler
    private void onUsingWitheredAxeAbility(BlockBreakEvent event) {

    }

    @EventHandler
    private void onWitheredAxeCriticalHit(EntityDamageByEntityEvent event) {
    }

    private void breakNearbyLog(Location location, ItemStack item){
        if(!whiteListedLogs.contains(location.getBlock().getType()))
            return;
        location.getBlock().breakNaturally(item);
        location.getWorld().playSound(location, Sound.BLOCK_WOOD_BREAK,1f,1f);
        breakNearbyLog(location.clone().add(0,0,1),item);
        breakNearbyLog(location.clone().add(0,0,-1),item);
        breakNearbyLog(location.clone().add(1,0,0),item);
        breakNearbyLog(location.clone().add(-1,0,0),item);
        breakNearbyLog(location.clone().add(-1,0,1),item);
        breakNearbyLog(location.clone().add(-1,0,-1),item);
        breakNearbyLog(location.clone().add(1,0,-1),item);
        //
        breakNearbyLog(location.clone().add(0,1,0),item);
        //
        breakNearbyLog(location.clone().add(0,1,1),item);
        breakNearbyLog(location.clone().add(0,1,-1),item);
        breakNearbyLog(location.clone().add(1,1,0),item);
        breakNearbyLog(location.clone().add(-1,1,0),item);
        breakNearbyLog(location.clone().add(-1,1,1),item);
        breakNearbyLog(location.clone().add(-1,1,-1),item);
        breakNearbyLog(location.clone().add(1,1,-1),item);
    }

    @EventHandler
    private void onUsingScytheAbility(PlayerInteractAtEntityEvent event) {

    }

    @EventHandler
    private void playerUseDustBow(ProjectileLaunchEvent event){

    }

    @EventHandler
    private void playerThrowTrident(ProjectileLaunchEvent event){

    }

    @EventHandler
    private void tridentHit(ProjectileHitEvent event){

    }

    @EventHandler
    private void projectTitleHit(ProjectileHitEvent event){
        if(!(event.getEntity() instanceof Arrow))
            return;
        if(!event.getEntity().getScoreboardTags().contains("DUSTBOWARROW"))
            return;
        event.setCancelled(true);
        event.getEntity().remove();
        if(!(event.getHitEntity() instanceof LivingEntity))
            return;
        LivingEntity hitEntity = (LivingEntity) event.getHitEntity();
        hitEntity.addPotionEffect(
                new PotionEffect(
                        PotionEffectType.BLINDNESS,
                        20*10,0,true,true,true));
        hitEntity.addPotionEffect(
                new PotionEffect(
                        PotionEffectType.POISON,
                        20*20,0,true,true,true));
        hitEntity.addPotionEffect(
                new PotionEffect(
                        PotionEffectType.WEAKNESS,
                        20*30,0,true,true,true));
        int damage = 10;
        if(((LivingEntity)hitEntity).getHealth()>damage) {
            ((LivingEntity) hitEntity).setHealth(Math.max(1, ((LivingEntity) hitEntity).getHealth() - damage));
            ((LivingEntity) hitEntity).damage(0.1, (Entity) event.getEntity().getShooter());
        }else {
            ((LivingEntity) hitEntity).damage(Integer.MAX_VALUE, (Entity) event.getEntity().getShooter());
        }
    }
}
