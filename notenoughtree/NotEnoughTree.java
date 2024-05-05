package com.mrnatsu.notenoughtree;

import com.mrnatsu.notenoughtree.command.constructors.Command;
import com.mrnatsu.notenoughtree.listener.EventListener;
import com.mrnatsu.notenoughtree.manager.CDManager;
import com.mrnatsu.notenoughtree.manager.tree.TreeManager;
import com.mrnatsu.notenoughtree.utils.AreaStructManager;
import com.mrnatsu.notenoughtree.utils.Logger;
import com.mrnatsu.notenoughtree.utils.reflections.Reflections;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public final class NotEnoughTree extends JavaPlugin {
    private final String packageName = getClass().getPackage().getName();
    private SimpleCommandMap commandMap;
    private final CDManager coolDownManager = new CDManager(this);
    private final AreaStructManager areaStructManager = new AreaStructManager(this);
    private final TreeManager treeManager = new TreeManager(this);
    @Override
    public void onEnable() {
        // Plugin startup logic
        Logger.setLogger(this.getLogger());
        saveDefaultConfig();
        areaStructManager.register();
        treeManager.load();
        setUpCommandMap();
        registerCommands();
        registerBukkitListener();
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

    private void registerCommands(){
        for (Class<? extends Command> clazz: new Reflections(packageName+".command.commands").getSubTypesOf(Command.class)){
            try{
                Command command = clazz.getDeclaredConstructor(getClass()).newInstance(this);
                this.commandMap.register("notenoughtree",command);
                Logger.log("Registering command: "+ "\u001B[33m"
                        +command.getCommandInfo().name()+"\u001B[0m"
                        +" with permission: "+"\u001B[34m"+command.getCommandInfo().permission()+Logger.ANSI_RESET);
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    private void registerBukkitListener(){
        for (Class<? extends EventListener> clazz: new Reflections(packageName+".listener").getSubTypesOf(EventListener.class)){
            try{
                EventListener command = clazz.getDeclaredConstructor(getClass()).newInstance(this);
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    private void registerListeners(){

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        treeManager.saveTree();
    }

    public AreaStructManager getAreaStructManager() {
        return areaStructManager;
    }

    public TreeManager getTreeManager() {
        return treeManager;
    }

    public CDManager getCoolDownManager() {
        return coolDownManager;
    }
}
