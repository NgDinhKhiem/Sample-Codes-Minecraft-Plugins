package com.natsu.vendingmachinesplugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.natsu.vendingmachinesplugin.command.constructors.Command;
import com.natsu.vendingmachinesplugin.config.ConfigValues;
import com.natsu.vendingmachinesplugin.config.DataManager;
import com.natsu.vendingmachinesplugin.config.GUIManager;
import com.natsu.vendingmachinesplugin.config.ServerData;
import com.natsu.vendingmachinesplugin.utils.Logger;
import com.natsu.vendingmachinesplugin.utils.TaskManager;
import com.natsu.vendingmachinesplugin.utils.reflections.Reflections;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.NamespacedKey;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public final class VendingMachinesPlugin extends JavaPlugin {
    private static final String packageName = VendingMachinesPlugin.class.getPackage().getName();
    private SimpleCommandMap commandMap;
    private Economy economy = null;
    private final DataManager dataManager = new DataManager(this);
    private Gson gson = new Gson();
    private final GUIManager guiManager = new GUIManager(this);
    private final TaskManager taskManager = new TaskManager(this);
    private final ConfigValues configValues = new ConfigValues(this);
    public static NamespacedKey key;

    @Override
    public void onEnable() {
        // Plugin startup logic
        Logger.setLogger(this.getLogger());
        key=new NamespacedKey(this,"vendingMachine");
        Logger.log(Logger.ANSI_RED+"RELOADING PLUGIN");
        gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        this.dataManager.init();
        setUpCommandMap();
        registerCommands();
        registerListeners();
        loadVaultAPI();
        guiManager.load();
        taskManager.start();
        configValues.load();
    }

    private void setUpCommandMap() {
        SimplePluginManager pluginManager = (SimplePluginManager) this.getServer().getPluginManager();
        Field field = null;
        try {
            field = SimplePluginManager.class.getDeclaredField("commandMap");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        assert field != null;
        field.setAccessible(true);

        try {
            this.commandMap = (SimpleCommandMap) field.get(pluginManager);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void registerListeners(){
        for (Class<? extends Listener> clazz: new Reflections(packageName+".eventListeners").getSubTypesOf(Listener.class)){
            try{
                Listener listener = clazz.getDeclaredConstructor(VendingMachinesPlugin.class).newInstance(this);
                getServer().getPluginManager().registerEvents(listener,this);
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    private void registerCommands(){
        for (Class<? extends Command> clazz: new Reflections(packageName+".command.commands").getSubTypesOf(Command.class)){
            try{
                Command command = clazz.getDeclaredConstructor(VendingMachinesPlugin.class).newInstance(this);
                this.commandMap.register("vmp",command);
                /*
                Logger.log("Registering command: "+ "\u001B[33m"
                        +command.getCommandInfo().name()+"\u001B[0m"
                        +" with permission: "+"\u001B[34m"+command.getCommandInfo().permission());*/
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadVaultAPI() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            Logger.log("\u001BVault is needed for the plugin to function!");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        RegisteredServiceProvider<Economy> rsp = this.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            Logger.log("Some Errors occur(#4xd0)!");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }
        economy = rsp.getProvider();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.dataManager.save();
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public Gson getGson() {
        if(gson==null)
            return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson;
    }

    public ServerData getServerData(){
        return dataManager.getServerData();
    }

    public GUIManager getGuiManager() {
        return guiManager;
    }

    public Economy getEconomy() {
        return economy;
    }

    public double getBalance(Player player){
        return  this.economy.getBalance(player);
    }
    public ConfigValues getConfigValues(){return this.configValues;}

    public void reload() {
        this.dataManager.init();
        guiManager.load();
    }
}
