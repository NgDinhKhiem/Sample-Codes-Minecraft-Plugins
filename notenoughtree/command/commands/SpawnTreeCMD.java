package com.mrnatsu.notenoughtree.command.commands;

import com.mrnatsu.notenoughtree.NotEnoughTree;
import com.mrnatsu.notenoughtree.command.constructors.Command;
import com.mrnatsu.notenoughtree.command.constructors.CommandInfo;
import com.mrnatsu.notenoughtree.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

import static com.mrnatsu.notenoughtree.utils.Utils.colorParse;


@CommandInfo(name = "spawnTree", permission = "net.op",requirePlayer = true)
public class SpawnTreeCMD extends Command {
    public SpawnTreeCMD(NotEnoughTree plugin) {
        super(plugin, "spawnTree");
    }

    @Override
    public void execute(Player sender, String[] args) {
        if(args.length!=1){
            sender.sendMessage(colorParse("&cWrong usage: /spawnTree <key>"));
            return;
        }
        plugin.getTreeManager().spawnTree(plugin.getAreaStructManager().getFirstCorner(),args[0],-1,true);
    }

    @Override
    public List<String> tab(CommandSender player, String[] args) {
        if(args.length==1){
            return Utils.getMatchList(args[0],plugin.getTreeManager().getListTree());
        }
        return List.of();
    }
}
