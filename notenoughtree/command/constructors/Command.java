package com.mrnatsu.notenoughtree.command.constructors;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.mrnatsu.notenoughtree.NotEnoughTree;
import com.mrnatsu.notenoughtree.utils.Logger;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static com.mrnatsu.notenoughtree.utils.Utils.colorParse;

public abstract class Command extends org.bukkit.command.Command implements PluginIdentifiableCommand {
    public final CommandInfo commandInfo;
    protected final NotEnoughTree plugin;
    public Command(NotEnoughTree plugin, String name) {
        super(name);
        this.plugin=plugin;
        this.commandInfo= getClass().getDeclaredAnnotation(CommandInfo.class);
        Objects.requireNonNull(this.commandInfo,"Commands must have CommandInfo annotations");
    }

    public CommandInfo getCommandInfo() {
        return commandInfo;
    }
    public void execute(CommandSender sender,String[] args){
    }

    public void execute(Player sender, String[] args){
    }

    @NotNull
    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        if(!sender.isOp())
            if(!sender.hasPermission(commandInfo.permission())){
                return ImmutableList.of();
            }
        return tab(sender,args);
    }

    @NotNull
    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args, @Nullable Location location) throws IllegalArgumentException {
        if(!sender.isOp())
            if(!sender.hasPermission(commandInfo.permission())){
                return ImmutableList.of();
            }
        return tab(sender,args);
    }

    public List<String> tab(CommandSender player,String[] args){return null;};

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if(!sender.isOp())
            if(!sender.hasPermission(commandInfo.permission())){
                sender.sendMessage(colorParse("&cYou do not have the permission for this command!"));
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

    @Override
    public Plugin getPlugin() {
        return this.plugin;
    }
}
