package com.mrnatsu.notenoughtree.manager.tree;

import com.google.gson.reflect.TypeToken;
import com.mrnatsu.notenoughtree.NotEnoughTree;
import com.mrnatsu.notenoughtree.objects.LocationWrapper;
import com.mrnatsu.notenoughtree.utils.Logger;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

import static com.mrnatsu.notenoughtree.utils.Utils.gson;

public class TreeManager implements Listener {
    private final Map<String,StructurePattern> structureHolder = new HashMap<>();
    private final Map<String,TreeObject> treeBaseDataHolder = new HashMap<>();
    private final Map<String, GrowingTreeObject> placedDownTreeMap = new HashMap<>();
    private final File treeDataFile;
    private final NamespacedKey treeKey;
    private final NotEnoughTree plugin;

    public TreeManager(NotEnoughTree plugin) {
        this.plugin = plugin;
        this.treeKey = new NamespacedKey(plugin,"TREE");
        this.treeDataFile = new File(plugin.getDataFolder().getPath()+File.separator+"trees_data.gson");
    }
    public void load(){
        //Removed
    }

    private void task(){
        new BukkitRunnable() {
            @Override
            public void run() {
                Map<String,GrowingTreeObject> markedObject = new HashMap<>();
                for(Map.Entry<String,GrowingTreeObject> entry: placedDownTreeMap.entrySet()){
                    GrowingTreeObject object = entry.getValue();
                    String json = gson.toJson(object);
                    if(markedObject.containsKey(json))
                        continue;
                    markedObject.put(json,object);
                }
                markedObject.forEach((key,obj)->obj.growTask(plugin));
            }
        }.runTaskTimer(plugin,1L,20L);
    }

    public void loadTree(){
        //Removed
    }

    public void saveTree(){
        try {
            if (!treeDataFile.exists()) {
                if (treeDataFile.createNewFile()) {
                    Logger.log("Created new <trees_data.yml> file");
                }
            }
            FileWriter writer = new FileWriter(treeDataFile);
            writer.write(gson.toJson(placedDownTreeMap));
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getListTree(){
        List<String> listTree = new ArrayList<>();
        treeBaseDataHolder.forEach((key,value)->listTree.add(key));
        return listTree;
    }

    public ItemStack getItem(String key){
        ItemStack item = new ItemStack(Material.AIR);
        if(!treeBaseDataHolder.containsKey(key))
            return item;
        return treeBaseDataHolder.get(key).getDisplayItem();
    }

    public boolean reachedMaxState(GrowingTreeObject growingTreeObject){
        TreeObject treeObject = treeBaseDataHolder.get(growingTreeObject.getTreeType());
        if(treeObject==null)
            return true;
        if(growingTreeObject.getState()%treeObject.getMaxWateringCount()==0){
            return true;
        }
        return false;
    }

    public void spawnTree(Location rootLocation, String key, int state, boolean isNatureSpawn){
        TreeObject treeObject = treeBaseDataHolder.get(key);
        if(treeObject==null)
            return;
        StructurePattern structurePattern = structureHolder.get(treeObject.getTreeSchemeAtState(state));
        if(structurePattern==null)
            return;
        Set<LocationWrapper> locationList = structurePattern.placeBlock(rootLocation);
        GrowingTreeObject growingTreeObject = new GrowingTreeObject(key,new LocationWrapper(rootLocation),locationList,isNatureSpawn);
        locationList.forEach(location->{
            placedDownTreeMap.put(location.getString(),growingTreeObject);
        });
    }

    public void spawnTree(GrowingTreeObject growingTreeObject){
        TreeObject treeObject = treeBaseDataHolder.get(growingTreeObject.getTreeType());
        if(treeObject==null)
            return;
        StructurePattern structurePattern = structureHolder.get(treeObject.getTreeSchemeAtState(growingTreeObject.getState()));
        if(structurePattern==null)
            return;
        Set<LocationWrapper> locationList = structurePattern.placeBlock(growingTreeObject.getRootLocation().getLocation());
        growingTreeObject.setBlockSet(locationList);
        locationList.forEach(location->{
            placedDownTreeMap.put(location.getString(),growingTreeObject);
        });
    }

    public boolean isTreeSeed(@Nullable ItemStack item){
        if(item==null)
            return false;
        ItemMeta meta = item.getItemMeta();
        if(meta==null)
            return false;
        return meta.getPersistentDataContainer().has(treeKey, PersistentDataType.STRING);
    }

    public String getTreeSeed(ItemStack item){
        if(!isTreeSeed(item))
            return "";
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        return meta.getPersistentDataContainer().get(treeKey, PersistentDataType.STRING);
    }

    @EventHandler(priority = EventPriority.MONITOR,ignoreCancelled = true)
    private void onPlayerBreakBlock(BlockBreakEvent event){
        //Removed
    }

    public void placeDownNewTree(String key){

    }
    @Nullable
    public GrowingTreeObject getGrowingTreeObject(Location location) {
        return this.placedDownTreeMap.get(new LocationWrapper(location).getString());
    }

    public void destroyTree(GrowingTreeObject treeObject, boolean dropSeed) {
        treeObject.getListPlacedBlock().forEach(s->{
            s.getLocation().getBlock().setType(Material.AIR);
            placedDownTreeMap.remove(s.getString());
        });
        if(dropSeed){
            Location dropLocation = treeObject.getRootLocation().getLocation();
            Objects.requireNonNull(dropLocation.getWorld()).dropItem(dropLocation,getTreeObject(treeObject).getDisplayItem());
        }

    }

    public TreeObject getTreeObject(GrowingTreeObject growingTreeObject){
        return this.treeBaseDataHolder.get(growingTreeObject.getTreeType());
    }
}
