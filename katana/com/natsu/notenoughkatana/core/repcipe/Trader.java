package com.natsu.notenoughkatana.core.repcipe;

import com.natsu.notenoughkatana.NotEnoughKatanaPlugin;
import com.natsu.notenoughkatana.core.objects.constructors.KatanaObject;
import com.natsu.notenoughkatana.core.objects.constructors.KatanaType;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Random;

import static com.natsu.notenoughkatana.utils.Utils.colorParse;

public class Trader implements Listener {
    private final NotEnoughKatanaPlugin plugin;
    private ItemStack trader;

    public Trader(NotEnoughKatanaPlugin plugin) {
        this.plugin = plugin;
    }
    public void register(){
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
        ConfigurationSection section = this.plugin.getConfig().getConfigurationSection("Items.Trader");
        ItemStack item = new ItemStack(Material.valueOf(section.getString("material").toUpperCase()));
        ItemMeta meta = item.getItemMeta();
        meta.setLore(colorParse(section.getStringList("lore")));
        meta.setDisplayName(colorParse(section.getString("name")));
        item.setItemMeta(meta);
        this.trader = item;
        ShapedRecipe upgraderRecipe = new ShapedRecipe(new NamespacedKey(this.plugin,"TRADER_RECIPE"), trader);
        upgraderRecipe.shape(
                "XYX",
                "YZY",
                "XYX");
        upgraderRecipe.setIngredient('X', Material.DIAMOND_BLOCK);
        upgraderRecipe.setIngredient('Y', Material.NETHERITE_INGOT);
        upgraderRecipe.setIngredient('Z', Material.SCULK_CATALYST);
        this.plugin.getServer().addRecipe(upgraderRecipe);
    }

    @EventHandler
    private void onTraderUsing(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(event.getAction().equals(Action.RIGHT_CLICK_AIR)||event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            ItemStack item = player.getInventory().getItemInMainHand();
            if(item.isSimilar(this.trader)){
                if(!this.plugin.getCdManager().addCoolDown(player.getUniqueId(),"PLAYERINTERACTTrader",5L))
                    return;
                item.setAmount(item.getAmount()-1);
                player.updateInventory();
                changeTypePlayerKatana(player);
                event.setCancelled(true);
            }
        }
    }

    private void changeTypePlayerKatana(Player player){
        for(ItemStack item:player.getInventory()){
            if(item==null)
                continue;
            if(!this.plugin.getGamePlayerManager().isKatana(item))
                return;
            KatanaObject katanaObject = this.plugin.getGamePlayerManager().getKatana(item);
            KatanaType katanaType = rollNewKatanaType(katanaObject.getType());
            katanaObject.getKatanaData().setType(katanaType);
            katanaObject.getKatanaData().setOriginalType(katanaType);
            //KatanaObject newKatana = katanaObject.getType().getKatanaObject(plugin,katanaObject.getKatanaData());
            //ItemMeta meta = item.getItemMeta();
            //meta.getPersistentDataContainer().set(this.plugin.getNamespacedKey(), PersistentDataType.STRING,this.plugin.getGson().toJson(katanaObject.getKatanaData()));
            //item.setItemMeta(meta);
            ItemStack katanaItem = katanaObject.getItem();
            katanaItem.addEnchantments(item.getEnchantments());
            item.setType(katanaItem.getType());
            item.setData(katanaItem.getData());
            item.setItemMeta(katanaItem.getItemMeta());
            plugin.getGamePlayerManager().forceChangePlayerKatanaType(player,katanaObject.getType());
            player.updateInventory();
            player.sendMessage("Your katana got traded to "+katanaObject.getKatanaData().getType());
        }
    }

    private KatanaType rollNewKatanaType(KatanaType katanaType){
        KatanaType newType = KatanaType.valueOf(katanaType.name());
        int value = new Random().nextInt(0,1000)%KatanaType.values().length;
        newType = KatanaType.values()[value];
        while (newType.equals(katanaType)||newType.equals(KatanaType.DRAGON_KATANA)){
            value++;
            value%=KatanaType.values().length;
            newType = KatanaType.values()[value];
        }
        return newType;
    }

    public ItemStack getTrader() {
        return this.trader.clone();
    }
}
