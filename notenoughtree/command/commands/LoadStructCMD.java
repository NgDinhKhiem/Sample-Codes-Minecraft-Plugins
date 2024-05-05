package com.mrnatsu.notenoughtree.command.commands;

import com.mrnatsu.notenoughtree.NotEnoughTree;
import com.mrnatsu.notenoughtree.command.constructors.Command;
import com.mrnatsu.notenoughtree.command.constructors.CommandInfo;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

import static com.mrnatsu.notenoughtree.utils.Utils.colorParse;


@CommandInfo(name = "loadstruct", permission = "net.op",requirePlayer = true)
public class LoadStructCMD extends Command {
    public LoadStructCMD(NotEnoughTree plugin) {
        super(plugin, "loadstruct");
    }

    @Override
    public void execute(Player sender, String[] args) {
        if(args.length!=1){
            sender.sendMessage(colorParse("&cWrong usage: /loadstruct <scheme>"));
            return;
        }
        plugin.getAreaStructManager().loadScheme(args[0]);
    }

    @Override
    public List<String> tab(CommandSender player, String[] args) {
        return List.of("Test");
    }
}
