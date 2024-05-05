package com.mrnatsu.notenoughtree.objects;

import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.Objects;

public class LocationWrapper {
    private int x;
    private int y;
    private int z;
    private float pitch;
    private float yaw;
    private final String world;

    public LocationWrapper(int x, int y, int z, World world){
        this.x=x;
        this.y=y;
        this.z=z;
        this.world=world.getName();
    }

    public LocationWrapper(org.bukkit.Location location){
        this.x=location.getBlockX();
        this.y=location.getBlockY();
        this.z=location.getBlockZ();
        this.pitch=location.getPitch();
        this.yaw=location.getYaw();
        this.world= Objects.requireNonNull(location.getWorld()).getName();
    }

    public String getString(){
        return x+", "+y+", "+z;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public org.bukkit.Location getLocation(){
        Objects.requireNonNull(Bukkit.getWorld(this.world));
        return new org.bukkit.Location(Bukkit.getWorld(this.world),this.x,this.y,this.z);
    }

    public org.bukkit.Location getLocation(String world){
        return new org.bukkit.Location(Bukkit.getWorld(world),this.x,this.y,this.z);
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }
}
