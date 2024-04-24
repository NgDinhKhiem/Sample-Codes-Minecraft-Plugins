package com.natsu.notenoughkatana.core.repcipe;

import com.natsu.notenoughkatana.NotEnoughKatanaPlugin;
import com.natsu.notenoughkatana.core.objects.constructors.KatanaObject;
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

import java.util.Objects;

import static com.natsu.notenoughkatana.utils.Utils.colorParse;
public class Upgrader implements Listener {
    private final NotEnoughKatanaPlugin plugin;
    private ItemStack upgrader;

    public Upgrader(NotEnoughKatanaPlugin plugin) {
        this.plugin = plugin;
    }
    public void register(){
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
        ConfigurationSection section = this.plugin.getConfig().getConfigurationSection("Items.Upgrader");
        ItemStack item = new ItemStack(Material.valueOf(section.getString("material").toUpperCase()));
        ItemMeta meta = item.getItemMeta();
        meta.setLore(colorParse(section.getStringList("lore")));
        meta.setDisplayName(colorParse(section.getString("name")));
        item.setItemMeta(meta);
        this.upgrader = item;
        ShapedRecipe upgraderRecipe = new ShapedRecipe(new NamespacedKey(this.plugin,"UPGRADER-RECIPE"),upgrader);
        upgraderRecipe.shape("XYX","YZY","XYX");
        upgraderRecipe.setIngredient('X', Material.DIAMOND_BLOCK);
        upgraderRecipe.setIngredient('Y', Material.GOLDEN_APPLE);
        upgraderRecipe.setIngredient('Z', Material.NETHER_STAR);
        this.plugin.getServer().addRecipe(upgraderRecipe);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onUpgraderUsing(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(event.getAction().equals(Action.RIGHT_CLICK_AIR)||event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            ItemStack item = player.getInventory().getItemInMainHand();
            if(item.isSimilar(this.upgrader)){
                if(!this.plugin.getCdManager().addCoolDown(player.getUniqueId(),"PLAYERINTERACTUPGRADER",5L))
                    return;
                if(upgradePlayerKatana(player))
                    item.setAmount(item.getAmount()-1);
                player.updateInventory();
                event.setCancelled(true);
            }
        }
    }

    public boolean upgradePlayerKatana(Player player){
        for(ItemStack item:player.getInventory()){
            if(item==null)
                continue;
            if(!this.plugin.getGamePlayerManager().isKatana(item))
                continue;
            KatanaObject katanaObject = this.plugin.getGamePlayerManager().getKatana(item);
            if(katanaObject.getKatanaData().getLevel()==3)
                return false;
            katanaObject.getKatanaData().upgrade(item);
            int slot = -1;
            for(int i = 0;i<player.getInventory().getSize();i++){
                if(Objects.equals(player.getInventory().getItem(i), item)){
                    slot = i;
                    break;
                }
            }
            if(slot==-1)
                continue;
            ItemStack newItem = katanaObject.getItem();
            newItem.addEnchantments(item.getEnchantments());
            item.setAmount(0);
            player.getInventory().setItem(slot,newItem);
            plugin.getGamePlayerManager().upgradePlayerKatana(player);
            player.sendMessage("SUCCESSFULLY UPGRADE TO LEVEL "+katanaObject.getKatanaData().getLevel());
            return true;
        }
        return false;
    }

    public ItemStack getUpgrader(){
        return this.upgrader.clone();
    }
}
