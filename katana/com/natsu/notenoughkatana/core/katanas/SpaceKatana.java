package com.natsu.notenoughkatana.core.katanas;

import com.natsu.notenoughkatana.NotEnoughKatanaPlugin;
import com.natsu.notenoughkatana.core.objects.ExtraData;
import com.natsu.notenoughkatana.core.objects.constructors.KatanaConfig;
import com.natsu.notenoughkatana.core.objects.constructors.KatanaData;
import com.natsu.notenoughkatana.core.objects.constructors.KatanaObject;
import com.natsu.notenoughkatana.core.objects.constructors.KatanaType;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

@KatanaConfig(configSection = "SpaceKatana", katanaType = KatanaType.SPACE_KATANA)
public class SpaceKatana extends KatanaObject {
    public SpaceKatana(NotEnoughKatanaPlugin plugin, KatanaData katanaData) {
        super(plugin, katanaData);
    }

    /*public DragonKatana(NotEnoughKatanaPlugin plugin) {
        super(plugin, new KatanaData(KatanaType.DRAGON_KATANA));
    }*/

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
