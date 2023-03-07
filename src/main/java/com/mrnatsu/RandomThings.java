package com.mrnatsu;

import com.mrnatsu.commands.Command;
import com.mrnatsu.ults.Reflection;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public final class RandomThings extends JavaPlugin {
    private final String packageName = getClass().getPackage().getName();
    @Override
    public void onEnable() {
        registerEventListener();
        registerCommands();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerCommands(){
        for (Class<Command> clazz: new Reflection(packageName+".commands").getSubTypesOf(Command.class)){
            try{
                Command command = clazz.getDeclaredConstructor(Plugin.class).newInstance(this);
                Objects.requireNonNull(getCommand(command.getCommandInfo().name())).setExecutor(command);
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    private void registerEventListener(){
        for (Class<Listener> clazz: new Reflection(packageName+".eventListeners").getSubTypesOf(Listener.class)){
            try{
                Listener listener = clazz.getDeclaredConstructor().newInstance();
                getServer().getPluginManager().registerEvents(listener,this);
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

}
