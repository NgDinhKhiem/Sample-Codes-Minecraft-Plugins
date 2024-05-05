package com.mrnatsu.notenoughtree.listener;

import com.mrnatsu.notenoughtree.NotEnoughTree;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import static com.mrnatsu.notenoughtree.utils.Utils.colorParse;

public class TreeSeedPlaceListener extends EventListener{
    public TreeSeedPlaceListener(NotEnoughTree plugin) {
        super(plugin);
        register();
    }
    @EventHandler(ignoreCancelled = true,priority = EventPriority.HIGH)
    private void onPlayerPlaceTreeSeed(BlockPlaceEvent event){
        ItemStack item = event.getItemInHand();
        if(!plugin.getTreeManager().isTreeSeed(item))
            return;
        Block block = event.getBlockAgainst();
        if(!block.getType().equals(Material.GRASS_BLOCK)){
            event.setCancelled(true);
            event.getPlayer().sendMessage(colorParse("&cYou can only plant seed on grass!"));
            return;
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                plugin.getTreeManager().spawnTree(event.getBlockPlaced().getLocation(),plugin.getTreeManager().getTreeSeed(item),0,false);
            }
        }.runTaskLater(plugin,1L);
    }
}
