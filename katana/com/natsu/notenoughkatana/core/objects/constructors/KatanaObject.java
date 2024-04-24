package com.natsu.notenoughkatana.core.objects.constructors;

import com.google.common.collect.Multimap;
import com.natsu.notenoughkatana.NotEnoughKatanaPlugin;
import com.natsu.notenoughkatana.core.objects.ExtraData;
import com.natsu.notenoughkatana.core.objects.Skill;
import com.natsu.notenoughkatana.utils.Utils;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

import static com.natsu.notenoughkatana.utils.Utils.colorParse;

public abstract class KatanaObject implements Katana {
    private final KatanaConfig katanaConfig;
    private final int baseDamage;
    private final KatanaData katanaData;
    private final Material material;
    private final int customDataModel;
    private final String name;
    private final NotEnoughKatanaPlugin plugin;

    protected KatanaObject(NotEnoughKatanaPlugin plugin, KatanaData katanaData) {

    }

    public void holding(Player player) {
        if(getKatanaData().getLevel()<1){
            return;
        }
        onHolding(player);
    }

    public void triggerAbility(Player player, ClickType clickType){
        if(getKatanaData().getLevel()<1){
            return;
        }
        ItemStack item = player.getInventory().getItemInOffHand();
        if(item.getType().equals(Material.SHIELD)){
            return;
        }
        onTriggerAbility(player, clickType);
    }

    public void triggerAbility(Player player, ClickType clickType, LivingEntity entity){
        if(getKatanaData().getLevel()<1){
            return;
        }
        onTriggerAbility(player, clickType, entity);
    }

    protected void trigger1(Player player, long time, ExtraData... data){
        if(this.katanaData.getLevel()<1)
            return;
        triggerLevel1Skill(player);
    }
    protected void trigger2(Player player, long time, ExtraData... data){
        if(getKatanaData().getLevel()<2){
            return;
        }
        triggerLevel2Skill(player);
    }
    protected void trigger3(Player player, long time, ExtraData... data){
        if(getKatanaData().getLevel()<3){
            return;
        }
        triggerLevel3Skill(player);
    }

    public ItemStack getItem(){
        return itemStack;
    }

    protected NotEnoughKatanaPlugin getPlugin() {
        return plugin;
    }

    public KatanaType getType(){return this.katanaData.getType();}

    public KatanaData getKatanaData() {
        return katanaData;
    }

    protected abstract void onHolding(Player player);

    protected abstract void onTriggerAbility(Player player, ClickType clickType);
    protected abstract void onTriggerAbility(Player player, ClickType clickType, LivingEntity entity);

    protected abstract void triggerLevel1Skill(Player player, ExtraData... extraData);

    protected abstract void triggerLevel2Skill(Player player, ExtraData... extraData);

    protected abstract void triggerLevel3Skill(Player player, ExtraData... extraData);
}
