package com.natsu.vendingmachinesplugin.utils;

import com.natsu.vendingmachinesplugin.VendingMachinesPlugin;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public final class ItemDataContainer {

    public static void addDataToItem(ItemStack itemStack,String gui){
        ItemMeta meta = itemStack.getItemMeta();
        assert meta != null;
        meta.getPersistentDataContainer().set(VendingMachinesPlugin.key, PersistentDataType.STRING,gui);
        itemStack.setItemMeta(meta);
    }

    public static boolean hasData(ItemStack itemStack){
        ItemMeta meta = itemStack.getItemMeta();
        if(meta==null)
            return false;
        return meta.getPersistentDataContainer().has(VendingMachinesPlugin.key,PersistentDataType.STRING);
    }

    public static String getData(ItemStack itemStack){
        ItemMeta meta = itemStack.getItemMeta();
        if(meta==null)
            return null;
        return meta.getPersistentDataContainer().get(VendingMachinesPlugin.key,PersistentDataType.STRING);
    }

}
