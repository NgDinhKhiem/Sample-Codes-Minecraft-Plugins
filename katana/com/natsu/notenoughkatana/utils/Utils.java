package com.natsu.notenoughkatana.utils;

import com.natsu.notenoughkatana.core.objects.State;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public final class Utils {
    public static String trimBack(String s){
        int i = s.length()-1;
        for(;i>0&&s.charAt(i)!='}';i--){
        }
        return s.substring(i+1,s.length());
    }
    public static String colorParse(String str){return ChatColor.translateAlternateColorCodes('&',str);}
    public static List<String> colorParse(List<String> str){
        List<String> rs = new ArrayList<>();
        if(str==null)
            return rs;
        str.forEach(s->rs.add(colorParse(s)));
        return rs;
    }

    public static List<String> colorParse(String... str){
        List<String> rs = new ArrayList<>();
        if(str==null)
            return rs;
        for(String s:str){
            rs.add(colorParse(s));
        }
        return rs;
    }

    public static List<String> getMatchList(String s, String... list){
        List<String> rs = new ArrayList<>();
        for(String str:list){
            if(str.toLowerCase().startsWith(s.toLowerCase()))
                rs.add(str);
        }
        return rs;
    }

    public static List<String> getMatchList(String s, List<String> list) {
        List<String> rs = new ArrayList<>();
        for (String str : list) {
            if (str.toLowerCase().startsWith(s.toLowerCase()))
                rs.add(str);
        }
        return rs;
    }
}
