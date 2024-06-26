package com.mrnatsu.notenoughtree.objects;

import static com.mrnatsu.notenoughtree.utils.Utils.gson;

public class Position {
    private int x;
    private int y;
    private int z;

    public Position(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public String serialize(){
        return gson.toJson(this);
    }

    public static Position deserialize(String data){
        return gson.fromJson(data,Position.class);
    }
}
