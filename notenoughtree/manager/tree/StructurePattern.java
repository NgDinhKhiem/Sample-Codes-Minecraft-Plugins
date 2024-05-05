package com.mrnatsu.notenoughtree.manager.tree;

import com.mrnatsu.notenoughtree.objects.LocationWrapper;
import com.mrnatsu.notenoughtree.objects.Position;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Breedable;

import java.util.*;

import static com.mrnatsu.notenoughtree.utils.Utils.gson;

public class StructurePattern {
    private final Map<String, String> blockHolder = new HashMap<>();
    private Position mid;

    public void addBlock(Position position,String blockData){
        this.blockHolder.put(position.serialize(),blockData);
    }

    public Set<LocationWrapper> placeBlock(Location location){
        //Removed
    }
    public String serialize(){
        return gson.toJson(this);
    }

    public static StructurePattern deserialize(String data){
        return gson.fromJson(data,StructurePattern.class);
    }
}
