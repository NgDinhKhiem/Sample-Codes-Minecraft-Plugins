package com.mrnatsu.commands;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

@CommandInfo(name = "fly",permission = "random.fly",requirePlayer = true)
public class Fly extends Command {
    public Fly(Plugin plugin) {
        super(plugin);
    }
    @Override
    public void execute(Player sender, String[] args) {
        sender.setAllowFlight(true);
    }
}
