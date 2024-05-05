package com.mrnatsu.notenoughtree.manager.tree;

import com.mrnatsu.notenoughtree.NotEnoughTree;
import com.mrnatsu.notenoughtree.utils.Encoder;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mrnatsu.notenoughtree.utils.Utils.colorParse;
@Nonnull
public class TreeObject {
    private final Map<Integer, String> treeSchemeHolder = new HashMap<>();
    private final ItemStack displayItem;
    private final String treeID;
    private final NamespacedKey namespacedKey;
    private final double baseGrowthTime;
    private final double baseWateringCost;
    private final double baseMoneyReward;
    private final double growthTimeMultiplier;
    private final double wateringCostMultiplier;
    private final double moneyRewardMultiplier;
    private final int maxWateringCount;

    public TreeObject(ConfigurationSection section, NamespacedKey namespacedKey, String treeID) {
        this.namespacedKey = namespacedKey;
        this.treeID = treeID;
        this.displayItem = itemBuilder(section.getConfigurationSection("Item"));
        this.baseGrowthTime = section.getDouble("Meta.BaseGrowthTime");
        this.baseWateringCost = section.getDouble("Meta.BaseWateringCost");
        this.baseMoneyReward = section.getDouble("Meta.BaseMoneyReward");
        this.growthTimeMultiplier = section.getDouble("Meta.GrowthTimeMultiplier");
        this.wateringCostMultiplier = section.getDouble("Meta.WateringCostMultiplier");
        this.moneyRewardMultiplier = section.getDouble("Meta.MoneyRewardMultiplier");
        int max = 0;
        for(String key:section.getConfigurationSection("Meta.TreeState").getKeys(false)){
            int value = Integer.parseInt(key);
            max = Math.max(max,value);
            treeSchemeHolder.put(value,section.getString("Meta.TreeState."+key));
        }
        for(int i = 1;i<max;i++){
            if(treeSchemeHolder.containsKey(i))
                continue;
            treeSchemeHolder.put(i,treeSchemeHolder.get(i-1));
        }
        this.maxWateringCount = max;
    }

    private ItemStack itemBuilder(@Nullable ConfigurationSection section){
        //Removed
    }

    @Nonnull
    public Map<Integer, String> getTreeSchemeHolder() {
        return new HashMap<>(treeSchemeHolder);
    }
    @Nonnull
    public List<String> getListSchematics(){
        List<String> treeSchematics = new ArrayList<>();
        treeSchemeHolder.forEach((key,value)->treeSchematics.add(value));
        return treeSchematics;
    }
    public ItemStack getDisplayItem() {
        return displayItem;
    }
    public double getBaseGrowthTime() {
        return baseGrowthTime*1000;
    }
    public long getGrowthTime(GrowingTreeObject growingTreeObject){return (long) (getBaseGrowthTime()*(1+getGrowthTimeMultiplier()*growingTreeObject.getState()));}
    public double getBaseWateringCost() {
        return baseWateringCost;
    }
    public double getWateringCost(int wateringCount){return baseWateringCost*Math.pow(wateringCostMultiplier,wateringCount);}
    public double getWateringCost(GrowingTreeObject growingTreeObject){return baseWateringCost*(1+wateringCostMultiplier*growingTreeObject.getState());}
    public double getBaseMoneyReward() {
        return baseMoneyReward;
    }
    public double getMoneyReward(GrowingTreeObject growingTreeObject){return baseMoneyReward*(1+baseMoneyReward*growingTreeObject.getState());}
    public double getGrowthTimeMultiplier() {
        return growthTimeMultiplier;
    }
    public double getWateringCostMultiplier() {
        return wateringCostMultiplier;
    }
    public double getMoneyRewardMultiplier() {
        return moneyRewardMultiplier;
    }
    public int getMaxWateringCount() {
        return maxWateringCount;
    }
    public boolean isHarvestable(GrowingTreeObject object){
        return (object.getState()%(maxWateringCount+1))==maxWateringCount;
    }

    public String getTreeSchemeAtState(int state) {
        /*if(state>maxWateringCount)
            state = maxWateringCount;*/
        if(state<0)
            state = maxWateringCount;
        state = state%(maxWateringCount+1);
        return this.treeSchemeHolder.get(state);
    }
}
