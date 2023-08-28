package com.natsu.vendingmachinesplugin.wrapper;

import com.natsu.vendingmachinesplugin.VendingMachinesPlugin;
import com.natsu.vendingmachinesplugin.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class InventoryContainer {
    private final VendingMachinesPlugin plugin;
    private final int rows;
    private final String title;
    private final HashMap<Integer, VendingItem> itemContainer = new HashMap<>();
    private final HashMap<Integer,ItemStack> dummyContainer = new HashMap<>();

    public InventoryContainer(String path, VendingMachinesPlugin plugin) {
        this.plugin = plugin;
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(new File(path));
        this.rows = configuration.getInt("rows");
        this.title = configuration.getString("title");

        ConfigurationSection section = configuration.getConfigurationSection("inventory");
        if(section==null)
            return;
        section.getKeys(false).forEach(key->{
            int slot = Integer.parseInt(key);
            String itemPath = "inventory."+key+".";

            if(configuration.get(itemPath+"dummy_item")!=null){
                ItemStack dummy = new ItemStack(Material.valueOf(Objects.requireNonNull(configuration.getString(itemPath + "dummy_item.material")).toUpperCase()));
                ItemMeta meta = dummy.getItemMeta();
                if (meta == null)
                    throw new RuntimeException("Item should not be null");
                meta.setDisplayName(Utils.parseColor(configuration.getString(itemPath + "dummy_item.name")));
                List<String> lore = new ArrayList<>();
                configuration.getStringList(itemPath + "dummy_item.lore").forEach(line -> lore.add(Utils.parseColor(line)));
                meta.setLore(lore);
                meta.setCustomModelData(configuration.getInt(itemPath + "dummy_item.customModelData"));
                dummy.setItemMeta(meta);
                this.dummyContainer.put(slot,dummy);
            }else {

                ItemStack displayItem = new ItemStack(Material.valueOf(Objects.requireNonNull(configuration.getString(itemPath + "display_item.material")).toUpperCase()));

                ItemMeta meta = displayItem.getItemMeta();
                if (meta == null)
                    throw new RuntimeException("Item should not be null");
                meta.setDisplayName(Utils.parseColor(configuration.getString(itemPath + "display_item.name")));
                List<String> lore = new ArrayList<>();
                configuration.getStringList(itemPath + "display_item.lore").forEach(line -> lore.add(Utils.parseColor(line)));
                meta.setLore(lore);
                meta.setCustomModelData(configuration.getInt(itemPath + "display_item.customModelData"));
                displayItem.setItemMeta(meta);
                ItemStack givingItem = this.plugin.getDataManager().getServerData().getItem(Objects.requireNonNull(configuration.getString(itemPath + "given_item")));
                VendingItem item =
                        new VendingItem(
                                configuration.getDouble(itemPath + "cost"),
                                displayItem, givingItem,
                                configuration.getLong(itemPath + "stock")
                        );
                this.itemContainer.put(slot, item);
            }
        });
    }

    public void pushItem(int slot,VendingItem item){
        this.itemContainer.put(slot,item);
    }

    public Inventory getInventory(){
        Inventory inventory = Bukkit.createInventory(null,rows*9, title);

        dummyContainer.forEach(inventory::setItem);

        itemContainer.forEach((slot,item)->{
            inventory.setItem(slot,item.getDisplayItem());
        });
        return inventory;
    }

    public String getTitle(){return this.title;}

    public boolean isItem(int slot){
        return this.itemContainer.containsKey(slot);
    }

    public double getCost(int slot){
        if(!this.isItem(slot))
            throw new RuntimeException("Error (E120)");
        return this.itemContainer.get(slot).getCost();
    }

    public ItemStack getItem(int slot) {
        return this.itemContainer.get(slot).getGivingItem();
    }

    public boolean isInStock(int slot){
        if(!this.isItem(slot))
            throw new RuntimeException("Error (E120)");
        return this.itemContainer.get(slot).inStock();
    }

    public void reduceStock(int slot){
        if(!this.isItem(slot))
            throw new RuntimeException("Error (E120)");
        this.itemContainer.get(slot).removeStock();
    }
}
