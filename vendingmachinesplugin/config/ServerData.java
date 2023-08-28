package com.natsu.vendingmachinesplugin.config;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.natsu.vendingmachinesplugin.utils.Encoder;
import com.natsu.vendingmachinesplugin.utils.Logger;
import com.natsu.vendingmachinesplugin.wrapper.LocationWrapper;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.*;

public class ServerData {
    private final Gson gson = new Gson();
    private final Map<String, ItemStack> itemHolder = new HashMap<>();
    @Expose
    private final Map<String, String> encodedItemHolder = new HashMap<>();
    @Expose
    private final Map<String,String> encodedWrapperMap = new HashMap<>();
    private final Map<String, String> locationWrapperMap = new HashMap<>();

    public void init(){
        encodedItemHolder.forEach((key,value)->{
            itemHolder.put(key,Encoder.decodeItem(value));
        });
        Set<String> removeList = new HashSet<>();
        encodedWrapperMap.forEach((key,value)->{
            String v = new String(Base64.getDecoder().decode(key.getBytes()));
            if(this.gson.fromJson(v, LocationWrapper.class).getLocation()!=null)
                locationWrapperMap.put(v,value);
            else removeList.add(key);
        });

        removeList.forEach(encodedWrapperMap::remove);
    }

    public ItemStack getItem(@Nonnull String key){
        if(!itemHolder.containsKey(key))
            throw new RuntimeException("There is no item with that key");
        return itemHolder.get(key);
    }
    public void addItem(@Nonnull String key, @Nonnull ItemStack itemStack){
        itemHolder.put(key,itemStack);
        encodedItemHolder.put(key, Encoder.encodeItem(itemStack));
    }

    public void removeItem(@Nonnull String key){
        itemHolder.remove(key);
        encodedItemHolder.remove(key);
    }

    public Set<String> getListItem(){
        return this.encodedItemHolder.keySet();
    }

    public void addLocation(@Nonnull String key, @Nonnull Location location){
        addLocation(key, new LocationWrapper(location));
    }

    public void addLocation(@Nonnull String key, @Nonnull LocationWrapper location){
        this.locationWrapperMap.put(location.encode(),key);
        this.encodedWrapperMap.put(Base64.getEncoder().encodeToString(gson.toJson(location).getBytes()),key);
    }

    public void removeLocation(){

    }

    public boolean hasGUI(Location location){
        return hasGUI(new LocationWrapper(location));
    }

    public boolean hasGUI(LocationWrapper location){
        return this.locationWrapperMap.containsKey(location.encode());
    }

    public String getGUI(Location location){
        return locationWrapperMap.get(new LocationWrapper(location).encode());
    }

}
