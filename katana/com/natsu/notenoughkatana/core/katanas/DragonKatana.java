package com.natsu.notenoughkatana.core.katanas;

import com.natsu.notenoughkatana.NotEnoughKatanaPlugin;
import com.natsu.notenoughkatana.core.objects.ExtraData;
import com.natsu.notenoughkatana.core.objects.constructors.KatanaConfig;
import com.natsu.notenoughkatana.core.objects.constructors.KatanaData;
import com.natsu.notenoughkatana.core.objects.constructors.KatanaObject;
import com.natsu.notenoughkatana.core.objects.constructors.KatanaType;
import org.bukkit.entity.DragonFireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

@KatanaConfig(configSection = "DragonKatana", katanaType = KatanaType.DRAGON_KATANA)
public class DragonKatana extends KatanaObject {
    public DragonKatana(NotEnoughKatanaPlugin plugin, KatanaData katanaData) {
        super(plugin, katanaData);
    }

    /*public DragonKatana(NotEnoughKatanaPlugin plugin) {
        super(plugin, new KatanaData(KatanaType.DRAGON_KATANA));
    }*/

    @Override
    protected void onHolding(Player player) {
        trigger1(player, 0, new ExtraData(false,null,true));
    }

    @Override
    protected void onTriggerAbility(Player player, ClickType clickType) {
        switch (clickType){
            case SHIFT_RIGHT:
                trigger2(player,2*60*20L);
                break;
            case RIGHT:
                trigger3(player,60*20L);
        }

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
