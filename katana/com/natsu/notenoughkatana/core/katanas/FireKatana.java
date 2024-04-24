package com.natsu.notenoughkatana.core.katanas;

import com.natsu.notenoughkatana.NotEnoughKatanaPlugin;
import com.natsu.notenoughkatana.core.objects.ExtraData;
import com.natsu.notenoughkatana.core.objects.constructors.KatanaConfig;
import com.natsu.notenoughkatana.core.objects.constructors.KatanaData;
import com.natsu.notenoughkatana.core.objects.constructors.KatanaObject;
import com.natsu.notenoughkatana.core.objects.constructors.KatanaType;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

@KatanaConfig(configSection = "FireKatana", katanaType = KatanaType.FIRE_KATANA)
public class FireKatana extends KatanaObject {
    public FireKatana(NotEnoughKatanaPlugin plugin, KatanaData katanaData) {
        super(plugin, katanaData);
    }

    public FireKatana(NotEnoughKatanaPlugin plugin) {
        super(plugin, new KatanaData(KatanaType.FIRE_KATANA));
    }

    @Override
    protected void onHolding(Player player) {
        trigger1(player, 0);
    }

    @Override
    protected void onTriggerAbility(Player player, ClickType clickType) {
        if(clickType.equals(ClickType.SHIFT_RIGHT))
            trigger2(player, 2*60*20);
        if(clickType.equals(ClickType.RIGHT))
            trigger3(player, 2*60*20);
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
