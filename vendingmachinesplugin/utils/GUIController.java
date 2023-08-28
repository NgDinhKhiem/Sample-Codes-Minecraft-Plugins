package com.natsu.vendingmachinesplugin.utils;

import com.natsu.vendingmachinesplugin.VendingMachinesPlugin;
import com.natsu.vendingmachinesplugin.config.ConfigValues;
import com.natsu.vendingmachinesplugin.wrapper.InventoryContainer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;

import java.util.Objects;

public class GUIController implements Listener {
    private final InventoryContainer inventoryContainer;
    private final VendingMachinesPlugin plugin;
    private final Player player;

    public GUIController(InventoryContainer inventoryContainer, VendingMachinesPlugin plugin, Player player) {
        this.inventoryContainer = inventoryContainer;
        this.plugin = plugin;
        this.player = player;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
        this.player.openInventory(inventoryContainer.getInventory());
    }

    @EventHandler
    private void inventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getType().equals(InventoryType.PLAYER))
            return;
        if (!event.getView().getTitle().equals(inventoryContainer.getTitle()))
            return;
        Player player = (Player) event.getWhoClicked();
        if (!player.getUniqueId().equals(this.player.getUniqueId()))
            return;
        event.setCancelled(true);

        int slot = event.getSlot();

        if (!inventoryContainer.isItem(slot))
            return;

        int repeat = 0;
        if (event.isRightClick())
            repeat = 32;
        if (event.isLeftClick())
            repeat = 1;

        int boughtAmount = 0;
        while (repeat-- > 0) {
            if (!this.inventoryContainer.isInStock(slot)) {
                if (ConfigValues.getBuyUnsuccessfulStock() != null)
                    player.playSound(player, ConfigValues.getBuyUnsuccessfulStock(), 1, 1);
                if (ConfigValues.getBuyUnsuccessfulStockMessage() != null) {
                    player.sendMessage(ConfigValues.getBuyUnsuccessfulStockMessage()
                            .replace("%item%", Objects.requireNonNull(Objects.requireNonNull(event.getCurrentItem()).getItemMeta()).getDisplayName())
                            .replace("%amount%", String.valueOf(boughtAmount))
                            .replace("%price%", String.valueOf(boughtAmount * this.inventoryContainer.getCost(slot))));
                }
                return;
            }
            double cost = this.inventoryContainer.getCost(slot);
            if (this.plugin.getBalance(player) < cost) {
                if (ConfigValues.getBuyUnsuccessfulMoney() != null)
                    player.playSound(player, ConfigValues.getBuyUnsuccessfulMoney(), 1, 1);
                if (ConfigValues.getBuyUnsuccessfulMoneyMessage() != null) {
                    player.sendMessage(ConfigValues.getBuyUnsuccessfulMoneyMessage()
                            .replace("%item%", Objects.requireNonNull(Objects.requireNonNull(event.getCurrentItem()).getItemMeta()).getDisplayName())
                            .replace("%amount%", String.valueOf(boughtAmount))
                            .replace("%price%", String.valueOf(boughtAmount * this.inventoryContainer.getCost(slot))));
                }
                return;
            }

            this.inventoryContainer.reduceStock(slot);
            this.plugin.getEconomy().withdrawPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()), cost);
            this.player.getInventory().addItem(this.inventoryContainer.getItem(slot));
            boughtAmount++;
        }
        if (boughtAmount > 0) {
            if (ConfigValues.getBuySuccessful() != null)
                player.playSound(player, ConfigValues.getBuySuccessful(), 1, 1);
            if (ConfigValues.getBuySuccessfulMessage() != null) {
                player.sendMessage(ConfigValues.getBuySuccessfulMessage()
                        .replace("%item%", Objects.requireNonNull(Objects.requireNonNull(event.getCurrentItem()).getItemMeta()).getDisplayName())
                        .replace("%amount%", String.valueOf(boughtAmount))
                        .replace("%price%", String.valueOf(boughtAmount * this.inventoryContainer.getCost(slot))));
            }
        }
    }

    @EventHandler
    private void inventoryClose(InventoryCloseEvent event){
        if(event.getInventory().getType().equals(InventoryType.PLAYER))
            return;
        if(!event.getView().getTitle().equals(inventoryContainer.getTitle()))
            return;
        Player player = (Player) event.getPlayer();
        if(!player.getUniqueId().equals(this.player.getUniqueId()))
            return;
        HandlerList.unregisterAll(this);
    }
}
