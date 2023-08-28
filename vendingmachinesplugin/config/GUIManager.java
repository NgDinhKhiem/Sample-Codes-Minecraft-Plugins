package com.natsu.vendingmachinesplugin.config;

import com.natsu.vendingmachinesplugin.VendingMachinesPlugin;
import com.natsu.vendingmachinesplugin.utils.Logger;
import com.natsu.vendingmachinesplugin.wrapper.InventoryContainer;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class GUIManager {
    private final VendingMachinesPlugin plugin;
    private final Map<String, InventoryContainer> guiHolder = new HashMap<>();

    public GUIManager(VendingMachinesPlugin plugin){
        this.plugin=plugin;
    }

    public void load(){
        for(File file: Objects.requireNonNull(new File("plugins/VendingMachinesPlugin/gui").listFiles())){
            guiHolder.put(file.getName().replace(".yml",""),new InventoryContainer(file.getPath(),plugin));
        }
        Logger.log("Loaded "+guiHolder.size()+" inventory");
    }

    public void restock(){
        for(File file: Objects.requireNonNull(new File("plugins/VendingMachinesPlugin/gui").listFiles())){
            guiHolder.put(file.getName().replace(".yml",""),new InventoryContainer(file.getPath(),plugin));
        }
    }

    public InventoryContainer getGUI(String key){
        return guiHolder.get(key);
    }

    public Set<String> getListKeys(){
        return this.guiHolder.keySet();
    }

}
