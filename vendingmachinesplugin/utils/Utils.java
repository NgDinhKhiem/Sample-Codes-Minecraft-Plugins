package com.natsu.vendingmachinesplugin.utils;

import org.bukkit.ChatColor;

public final class Utils {
    public static String parseColor(String str){
        return ChatColor.translateAlternateColorCodes('&',str);
    }
}
