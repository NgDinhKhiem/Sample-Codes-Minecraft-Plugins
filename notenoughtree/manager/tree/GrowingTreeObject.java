package com.mrnatsu.notenoughtree.manager.tree;

import com.mrnatsu.notenoughtree.NotEnoughTree;
import com.mrnatsu.notenoughtree.objects.LocationWrapper;
import com.mrnatsu.notenoughtree.utils.Logger;
import com.mrnatsu.notenoughtree.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Set;

import static com.mrnatsu.notenoughtree.utils.Utils.colorParse;

public class GrowingTreeObject {
    private final String treeType;
    private final LocationWrapper rootLocation;
    private final Set<LocationWrapper> listPlacedBlock;
    private final boolean isNatureSpawn;
    private int waterCount = 0;
    private long nextGrowth = 0;

    public int getState() {
        return state;
    }

    private int state = 0;

    public GrowingTreeObject(String treeType, LocationWrapper rootLocation, Set<LocationWrapper> listPlacedBlock, boolean isNatureSpawn) {
        this.treeType = treeType;
        this.rootLocation = rootLocation;
        this.listPlacedBlock = listPlacedBlock;
        this.isNatureSpawn = isNatureSpawn;
    }

    public void water(NotEnoughTree plugin,Player player) {
        //Removed
    }

    public String getTreeType() {
        return treeType;
    }

    public LocationWrapper getRootLocation() {
        return rootLocation;
    }

    public Set<LocationWrapper> getListPlacedBlock() {
        return listPlacedBlock;
    }

    public boolean isNatureSpawn() {
        return isNatureSpawn;
    }

    public int getWaterCount() {
        return waterCount;
    }

    public long getRemainTime() {
        return this.nextGrowth - Utils.getCurrentTime();
    }
    public String getHarvestState(NotEnoughTree plugin) {
        //Removed
    }

    public void grow(NotEnoughTree plugin) {
        if (plugin.getTreeManager().reachedMaxState(this)) {
            return;
        }
        state++;
        plugin.getTreeManager().destroyTree(this,false);
        plugin.getTreeManager().spawnTree(this);
    }

    public String getWaterState(NotEnoughTree plugin) {
        //Removed
    }

    public void harvest(NotEnoughTree plugin, Player player){
        //Removed
    }

    public void growTask(NotEnoughTree plugin) {
        if(getRemainTime()>0||state==waterCount){
            return;
        }
        state = waterCount;
        plugin.getTreeManager().destroyTree(this, false);
        plugin.getTreeManager().spawnTree(GrowingTreeObject.this);
    }

    public void setBlockSet(Set<LocationWrapper> locationList) {
        listPlacedBlock.clear();
        listPlacedBlock.addAll(locationList);
    }
}
