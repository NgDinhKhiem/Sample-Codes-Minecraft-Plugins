package com.natsu.notenoughkatana.core.katanas;

import com.natsu.notenoughkatana.NotEnoughKatanaPlugin;
import com.natsu.notenoughkatana.core.objects.ExtraData;
import com.natsu.notenoughkatana.core.objects.constructors.KatanaConfig;
import com.natsu.notenoughkatana.core.objects.constructors.KatanaData;
import com.natsu.notenoughkatana.core.objects.constructors.KatanaObject;
import com.natsu.notenoughkatana.core.objects.constructors.KatanaType;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@KatanaConfig(configSection = "SpeedKatana", katanaType = KatanaType.SPEED_KATANA)
public class SpeedKatana extends KatanaObject {
    public SpeedKatana(NotEnoughKatanaPlugin plugin, KatanaData katanaData) {
        super(plugin, katanaData);
    }

    public SpeedKatana(NotEnoughKatanaPlugin plugin) {
        super(plugin, new KatanaData(KatanaType.SPEED_KATANA));
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

    private boolean isInRange(Player player, Entity entity, double range){
        return entity.getLocation().clone().distance(player.getLocation().clone()) <= range;
    }
}
