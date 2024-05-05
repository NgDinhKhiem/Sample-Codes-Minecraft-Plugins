package com.mrnatsu.notenoughtree.listener;

import com.mrnatsu.notenoughtree.NotEnoughTree;
import com.mrnatsu.notenoughtree.gui.GUIMenu;
import com.mrnatsu.notenoughtree.manager.tree.GrowingTreeObject;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import static com.mrnatsu.notenoughtree.utils.Utils.colorParse;

public class TreeInteractListener extends EventListener{
    public TreeInteractListener(NotEnoughTree plugin) {
        super(plugin);
        register();
    }
    @EventHandler(ignoreCancelled = true,priority = EventPriority.HIGH)
    private void onPlayerInteractWithTree(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
            return;
        if(plugin.getCoolDownManager().isOnCoolDown(player.getUniqueId(),"RIGHTACTIONTREE"))
            return;
        if(event.getClickedBlock()==null)
            return;
        plugin.getCoolDownManager().addCoolDown(player.getUniqueId(),"RIGHTACTIONTREE",5L);
        GrowingTreeObject growingTreeObject = plugin.getTreeManager().getGrowingTreeObject(event.getClickedBlock().getLocation());
        if(growingTreeObject==null)
            return;
        if(growingTreeObject.isNatureSpawn()){
            return;
        }
        new GUIMenu(player,"treeGUI.yml",plugin,growingTreeObject);
    }

    @EventHandler(ignoreCancelled = true)
    private void onTreeNaturallyGrowth(StructureGrowEvent event){
        GrowingTreeObject growingTreeObject = plugin.getTreeManager().getGrowingTreeObject(event.getLocation());
        if(growingTreeObject==null)
            return;
        event.setCancelled(true);
    }
}
