package com.mrnatsu.commands;

import com.mrnatsu.RandomThings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

public abstract class Command implements CommandExecutor{
    private final CommandInfo commandInfo;
    protected Plugin plugin;
    public Command(Plugin plugin) {
        this.plugin=plugin;
        this.commandInfo= getClass().getDeclaredAnnotation(CommandInfo.class);
        Objects.requireNonNull(this.commandInfo,"Commands must have CommandInfo annotations");
    }

    public CommandInfo getCommandInfo() {
        return commandInfo;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if(!sender.hasPermission(commandInfo.permission())){
            sender.sendMessage(ChatColor.RED+"You have no permission to run this command.");
            return true;
        }
        if(commandInfo.requirePlayer()){
            if(!(sender instanceof Player)){
                sender.sendMessage(ChatColor.RED + "You need to be a player to run this command.");
                return true;
            }
            execute((Player) sender,args);
        }
        execute(sender,args);
        return true;
    }

    public void execute(CommandSender sender,String[] args){
    }

    public void execute(Player sender, String[] args){
    }
}
