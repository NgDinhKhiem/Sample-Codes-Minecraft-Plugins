package com.mrnatsu.notenoughtree.utils;

import org.bukkit.ChatColor;

public final class Logger {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    private static java.util.logging.Logger logger;

    public static void setLogger(java.util.logging.Logger logge){
        logger=logge;
    }

    public static void log(Object str) {
        logger.info(parseColor(str.toString())+ANSI_RESET);
    }

    public static String parseColor(String str){
        return (ChatColor.translateAlternateColorCodes('&',str));
    }

    public static void warning(Object str){logger.warning(ANSI_RED+parseColor(str.toString())+ANSI_RESET);}
}
