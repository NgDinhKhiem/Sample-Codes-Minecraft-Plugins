package com.natsu.notenoughkatana.core.katanas;

import com.natsu.notenoughkatana.NotEnoughKatanaPlugin;
import com.natsu.notenoughkatana.core.objects.ExtraData;
import com.natsu.notenoughkatana.core.objects.constructors.KatanaConfig;
import com.natsu.notenoughkatana.core.objects.constructors.KatanaData;
import com.natsu.notenoughkatana.core.objects.constructors.KatanaObject;
import com.natsu.notenoughkatana.core.objects.constructors.KatanaType;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@KatanaConfig(configSection = "StrengthKatana", katanaType = KatanaType.STRENGTH_KATANA)
public class StrengthKatana extends KatanaObject {
    public StrengthKatana(NotEnoughKatanaPlugin plugin, KatanaData katanaData) {
        super(plugin, katanaData);
    }

    public StrengthKatana(NotEnoughKatanaPlugin plugin) {
        super(plugin, new KatanaData(KatanaType.STRENGTH_KATANA));
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
