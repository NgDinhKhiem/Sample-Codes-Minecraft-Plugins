package com.mrnatsu.notenoughtree.command.commands;

import com.mrnatsu.notenoughtree.NotEnoughTree;
import com.mrnatsu.notenoughtree.command.constructors.Command;
import com.mrnatsu.notenoughtree.command.constructors.CommandInfo;
import com.mrnatsu.notenoughtree.utils.Encoder;
import com.mrnatsu.notenoughtree.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.mrnatsu.notenoughtree.utils.Utils.colorParse;


@CommandInfo(name = "netgive", permission = "net.op",requirePlayer = true)
public class GetTreeCMD extends Command {
    public GetTreeCMD(NotEnoughTree plugin) {
        super(plugin, "netgive");
    }

    @Override
    public void execute(Player sender, String[] args) {
        if(args.length!=1){
            sender.sendMessage(colorParse("&cWrong usage: /netgive <key>"));
            return;
        }
        sender.getInventory().addItem(plugin.getTreeManager().getItem(args[0]));
        sender.sendMessage(colorParse("&aGiven an item to your inventory"));
    }

    @Override
    public List<String> tab(CommandSender player, String[] args) {
        if(args.length==1){
            return Utils.getMatchList(args[0],plugin.getTreeManager().getListTree());
        }
        return List.of();
    }
}
