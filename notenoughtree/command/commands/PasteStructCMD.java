package com.mrnatsu.notenoughtree.command.commands;

import com.mrnatsu.notenoughtree.NotEnoughTree;
import com.mrnatsu.notenoughtree.command.constructors.Command;
import com.mrnatsu.notenoughtree.command.constructors.CommandInfo;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

import static com.mrnatsu.notenoughtree.utils.Utils.colorParse;


@CommandInfo(name = "netpaste", permission = "net.op",requirePlayer = true)
public class PasteStructCMD extends Command {
    public PasteStructCMD(NotEnoughTree plugin) {
        super(plugin, "netpaste");
    }

    @Override
    public void execute(Player sender, String[] args) {
        if(args.length!=0){
            sender.sendMessage(colorParse("&cWrong usage: /netpaste"));
            return;
        }
        plugin.getAreaStructManager().pasteScheme();
    }

    @Override
    public List<String> tab(CommandSender player, String[] args) {
        return List.of("Test");
    }
}
