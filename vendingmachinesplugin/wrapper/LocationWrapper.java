package com.natsu.vendingmachinesplugin.wrapper;

import com.google.gson.Gson;
import com.natsu.vendingmachinesplugin.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.Objects;

public class LocationWrapper {
    private static final Gson gson = new Gson();
    private long x;
    private long y;
    private long z;
    private String world;

    public LocationWrapper(Location location){
        this.x=location.getBlockX();
        this.y=location.getBlockY();
        this.z=location.getBlockZ();
        this.world= Objects.requireNonNull(location.getWorld()).getName();
    }

    public LocationWrapper(double x, double y, double z, String world){
        this.x=(long)x;
        this.y=(long)y;
        this.z=(long)z;
        this.world=world;
    }

    public long getX() {
        return x;
    }

    public void setX(long x) {
        this.x = x;
    }

    public long getY() {
        return y;
    }

    public void setY(long y) {
        this.y = y;
    }

    public long getZ() {
        return z;
    }

    public void setZ(long z) {
        this.z = z;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public Location getLocation(){
        if(Bukkit.getWorld(this.world)==null)
            return null;
        if(new Location(Bukkit.getWorld(this.world),x,y,z).getBlock().getType().equals(Material.AIR))
            return null;
        Logger.log(new Location(Bukkit.getWorld(this.world),x,y,z).getBlock().getType());
        return new Location(Bukkit.getWorld(this.world),x,y,z);
    }
    public String encode(){return gson.toJson(this);}
}
