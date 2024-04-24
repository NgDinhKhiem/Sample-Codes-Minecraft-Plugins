package com.natsu.notenoughkatana.core.katanas;

import com.natsu.notenoughkatana.NotEnoughKatanaPlugin;
import com.natsu.notenoughkatana.core.objects.ExtraData;
import com.natsu.notenoughkatana.core.objects.constructors.KatanaConfig;
import com.natsu.notenoughkatana.core.objects.constructors.KatanaData;
import com.natsu.notenoughkatana.core.objects.constructors.KatanaObject;
import com.natsu.notenoughkatana.core.objects.constructors.KatanaType;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

@KatanaConfig(configSection = "LifeKatana", katanaType = KatanaType.LIFE_KATANA)
public class LifeKatana extends KatanaObject {
    public LifeKatana(NotEnoughKatanaPlugin plugin, KatanaData katanaData) {
        super(plugin, katanaData);
    }

    public LifeKatana(NotEnoughKatanaPlugin plugin) {
        super(plugin, new KatanaData(KatanaType.LIFE_KATANA));
    }

    @Override
    protected void onHolding(Player player) {

    }

    @Override
    protected void onTriggerAbility(Player player, ClickType clickType) {

    }
    @Override
    protected void onTriggerAbility(Player player, ClickType clickType, LivingEntity entity) {

    }

    @Override
    protected void triggerLevel1Skill(Player player, ExtraData... extraData) {

    }

    @Override
    protected void triggerLevel2Skill(Player player, ExtraData... extraData) {

    }

    @Override
    protected void triggerLevel3Skill(Player player, ExtraData... extraData) {

    }
}
