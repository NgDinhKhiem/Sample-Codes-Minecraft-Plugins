package com.natsu.notenoughkatana.commands;

import com.natsu.notenoughkatana.NotEnoughKatanaPlugin;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;

public abstract class SpigotCommand implements CommandExecutor, TabCompleter {
    protected final NotEnoughKatanaPlugin plugin;

    protected SpigotCommand(NotEnoughKatanaPlugin plugin) {
        this.plugin = plugin;
    }
}
