package com.mrnatsu.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

@CommandInfo(name = "ping",permission = "random.ping",requirePlayer = true)
public class Ping extends Command{
    public Ping(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void execute(Player sender, String[] args) {
        sender.sendMessage(ChatColor.GREEN+"Ping: "+ChatColor.WHITE+sender.getPing());
    }
}
