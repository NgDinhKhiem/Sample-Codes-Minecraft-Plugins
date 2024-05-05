package com.mrnatsu.notenoughtree.gui;

import com.mrnatsu.notenoughtree.NotEnoughTree;
import com.mrnatsu.notenoughtree.manager.tree.GrowingTreeObject;
import com.mrnatsu.notenoughtree.manager.tree.TreeObject;
import com.mrnatsu.notenoughtree.objects.ClickHolder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;

import static com.mrnatsu.notenoughtree.utils.Utils.*;

public class GUIMenu implements Listener {

    private final Player user;
    private final NotEnoughTree plugin;
    private Inventory inventory;
    private final GrowingTreeObject growingTreeObject;
    private final TreeObject treeObject;
    private final Map<Integer, Function<ClickHolder,Boolean>> slotProcessor = new HashMap<>();
    private final Set<BukkitRunnable> runnables = new HashSet<>();

    public GUIMenu(Player player, String gui, NotEnoughTree plugin, GrowingTreeObject growingTreeObject) {
        this.user = player;
        this.plugin = plugin;
        this.growingTreeObject = growingTreeObject;
        this.treeObject = plugin.getTreeManager().getTreeObject(growingTreeObject);
        loadFile(gui);
        BukkitRunnable run = new BukkitRunnable() {
            @Override
            public void run() {
                loadFile(gui);
            }
        };
        runnables.add(run);
        run.runTaskTimer(plugin,20L,2L);
        this.plugin.getServer().getPluginManager().registerEvents(this,plugin);
        this.user.openInventory(this.inventory);
    }

    private void loadFile(String gui){
        if(!gui.endsWith(".yml"))
            throw new RuntimeException("Not a valid file");

        File file = new File(this.plugin.getDataFolder().getPath()+File.separator+"gui"+File.separator+gui);

        if(!file.exists())
            throw new RuntimeException("Not a valid file");
        YamlConfiguration configuration = new YamlConfiguration();
        try {
            configuration.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
        if(inventory==null) {
            String title = colorParse(
                    configuration.getString("Title", "Not yet named")
                            .replace("%tree_name%", plugin.getTreeManager().getItem(growingTreeObject.getTreeType()).getItemMeta().getDisplayName()));

            inventory = Bukkit.createInventory(null, configuration.getInt("Rows") * 9, title);
        }
        ConfigurationSection inventorySection = configuration.getConfigurationSection("Inventory");

        inventorySection.getKeys(false).forEach(item->{
            ConfigurationSection itemSection = inventorySection.getConfigurationSection(item);
            ItemStack displayItem = itemBuilder(itemSection);

            List<Integer> slots = itemSection.getIntegerList("slots");
            GUIAction action = GUIAction.valueOf(itemSection.getString("action","DUMMY").toUpperCase());
            slots.forEach(slot->{
                inventory.setItem(slot,displayItem);
            });
            switch (action) {
                case WATER:{
                    Function<ClickHolder,Boolean> function = new Function<ClickHolder, Boolean>() {
                        @Override
                        public Boolean apply(ClickHolder clickHolder) {
                            if(treeObject.isHarvestable(growingTreeObject))
                                return false;
                            growingTreeObject.water(plugin,user);
                            return false;
                        }
                    };
                    slots.forEach(slot->{
                        slotProcessor.put(slot,function);
                    });
                    break;
                }
                case HARVEST:{
                    Function<ClickHolder,Boolean> function = new Function<ClickHolder, Boolean>() {
                        @Override
                        public Boolean apply(ClickHolder clickHolder) {
                            if(!treeObject.isHarvestable(growingTreeObject))
                                return false;
                            growingTreeObject.harvest(plugin,user);
                            return false;
                        }
                    };
                    slots.forEach(slot->{
                        slotProcessor.put(slot,function);
                    });
                    break;
                }
                case REMOVE:{
                    Function<ClickHolder,Boolean> function = new Function<ClickHolder, Boolean>() {
                        @Override
                        public Boolean apply(ClickHolder clickHolder) {
                            plugin.getTreeManager().destroyTree(growingTreeObject,true);
                            user.closeInventory();
                            return false;
                        }
                    };
                    slots.forEach(slot->{
                        slotProcessor.put(slot,function);
                    });
                    break;
                }
                default:
                    break;
            }
        });
    }

    private ItemStack itemBuilder(ConfigurationSection itemSection){
        String name = parse(colorParse(itemSection.getString("name")));
        String material = Objects.requireNonNull(itemSection.getString("material"),"Material cannot be null: "+itemSection.getName()).toUpperCase();
        List<String> lore = new ArrayList<>();
        itemSection.getStringList("lore").forEach(s-> {
            lore.add(parse(colorParse(s)));
        });
        ItemStack item = new ItemStack(Material.valueOf(material));

        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    @EventHandler
    private void onClickInventory(InventoryClickEvent e){
        if(!e.getView().getTopInventory().equals(this.inventory))
            return;
        if(!e.getWhoClicked().getUniqueId().equals(this.user.getUniqueId()))
            return;
        e.setCancelled(true);
        if(this.slotProcessor.containsKey(e.getSlot()))
            this.slotProcessor.get(e.getSlot()).apply(new ClickHolder(this.user,e.getClick()));

    }

    @EventHandler
    private void onCloseInventory(InventoryCloseEvent e){
        if(!e.getPlayer().getUniqueId().equals(this.user.getUniqueId()))
            return;

        HandlerList.unregisterAll(this);
        runnables.forEach(task->{
            if (!task.isCancelled()) {
                task.cancel();
            }});
    }

    private String parse(String s){
        return s.replace("%tree_name%",treeObject.getDisplayItem().getItemMeta().getDisplayName())
                .replace("%water_cost%", format(treeObject.getWateringCost(growingTreeObject)))
                .replace("%harvest_value%", format(treeObject.getMoneyReward(growingTreeObject)))
                .replace("%harvest_state%", growingTreeObject.getHarvestState(plugin))
                .replace("%water_state%", growingTreeObject.getWaterState(plugin));
    }
}
