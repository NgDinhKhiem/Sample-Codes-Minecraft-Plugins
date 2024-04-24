package com.natsu.notenoughkatana.core.objects.constructors;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public interface Katana {
    private void onHolding(Player player){};
    private void onTriggerAbility(Player player, ClickType clickType){};
    private void onTriggerAbility(Player player, ClickType clickType, Entity entity){};
    private void triggerLevel1Skill(Player player, Entity... target){};
    private void triggerLevel2Skill(Player player, Entity... target){};
    private void triggerLevel3Skill(Player player, Entity... target){};
    ItemStack getItem();
}
