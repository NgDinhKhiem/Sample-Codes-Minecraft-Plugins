package com.natsu.notenoughkatana.core.repcipe;

import com.natsu.notenoughkatana.NotEnoughKatanaPlugin;
import com.natsu.notenoughkatana.core.gui.RevivingGUI;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import static com.natsu.notenoughkatana.utils.Utils.colorParse;

public class Reviver implements Listener {
    private final NotEnoughKatanaPlugin plugin;
    private ItemStack reviver;

    public Reviver(NotEnoughKatanaPlugin plugin) {
        this.plugin = plugin;
    }
    public void register(){
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
        ConfigurationSection section = this.plugin.getConfig().getConfigurationSection("Items.Reviver");
        ItemStack item = new ItemStack(Material.valueOf(section.getString("material").toUpperCase()));
        ItemMeta meta = item.getItemMeta();
        meta.setLore(colorParse(section.getStringList("lore")));
        meta.setDisplayName(colorParse(section.getString("name")));
        item.setItemMeta(meta);
        this.reviver = item;
        ShapedRecipe upgraderRecipe = new ShapedRecipe(new NamespacedKey(this.plugin,"REVIVER_RECIPE"), reviver);
        upgraderRecipe.shape("XYX","YZY","CYC");
        upgraderRecipe.setIngredient('X', Material.NETHER_STAR);
        upgraderRecipe.setIngredient('Y', Material.NETHERITE_INGOT);
        upgraderRecipe.setIngredient('Z', Material.BEACON);
        upgraderRecipe.setIngredient('C', Material.DIAMOND_BLOCK);
        this.plugin.getServer().addRecipe(upgraderRecipe);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onReviverUse(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(event.getAction().equals(Action.RIGHT_CLICK_AIR)||event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            ItemStack item = player.getInventory().getItemInMainHand();
            if(item.isSimilar(this.reviver)){
                if(!this.plugin.getCdManager().addCoolDown(player.getUniqueId(),"PLAYERINTERACTReviver",5L))
                    return;
                new RevivingGUI(this.plugin,player);
                player.updateInventory();
                event.setCancelled(true);
            }
        }
    }

    public ItemStack getReviver(){
        return this.reviver;
    }

}
