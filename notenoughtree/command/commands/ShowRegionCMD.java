package com.mrnatsu.notenoughtree.command.commands;

import com.mrnatsu.notenoughtree.NotEnoughTree;
import com.mrnatsu.notenoughtree.command.constructors.Command;
import com.mrnatsu.notenoughtree.command.constructors.CommandInfo;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;


@CommandInfo(name = "showregion", permission = "net.op",requirePlayer = true)
public class ShowRegionCMD extends Command {
    public ShowRegionCMD(NotEnoughTree plugin) {
        super(plugin, "showregion");
    }

    @Override
    public void execute(Player sender, String[] args) {

    }

    @Override
    public List<String> tab(CommandSender player, String[] args) {
        return List.of("Test");
    }
}
