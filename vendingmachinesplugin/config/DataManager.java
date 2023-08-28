package com.natsu.vendingmachinesplugin.config;

import com.google.gson.GsonBuilder;
import com.natsu.vendingmachinesplugin.VendingMachinesPlugin;
import com.natsu.vendingmachinesplugin.utils.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class DataManager {
    private final VendingMachinesPlugin plugin;
    private final File serverDataFile = new File("plugins/VendingMachinesPlugin/data.json");
    private ServerData serverData = null;

    public DataManager(VendingMachinesPlugin plugin){
        this.plugin = plugin;
        //this.init();
    }

    public void init(){
        new File("plugins/VendingMachinesPlugin").mkdir();
        new File("plugins/VendingMachinesPlugin/gui").mkdir();
        try {
            File file = new File("plugins/VendingMachinesPlugin/data.yml");
            if(!file.exists())
                file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        if(!serverDataFile.exists()) {
            serverData = new ServerData();
            return;
        }

        try {
            StringBuilder dataString = new StringBuilder();
            Files.readAllLines(serverDataFile.toPath()).forEach(dataString::append);
            serverData = this.plugin.getGson().fromJson(dataString.toString(), ServerData.class);
            serverData.init();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ServerData getServerData(){return this.serverData;}

    public void save(){
        try{
            FileWriter writer = new FileWriter(serverDataFile);
            writer.write(new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create().toJson(this.serverData));
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
