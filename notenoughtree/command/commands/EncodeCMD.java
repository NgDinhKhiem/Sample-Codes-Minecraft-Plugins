package com.mrnatsu.notenoughtree.command.commands;

import com.mrnatsu.notenoughtree.NotEnoughTree;
import com.mrnatsu.notenoughtree.command.constructors.Command;
import com.mrnatsu.notenoughtree.command.constructors.CommandInfo;
import com.mrnatsu.notenoughtree.utils.Encoder;
import com.mrnatsu.notenoughtree.utils.Utils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.mrnatsu.notenoughtree.utils.Utils.colorParse;


@CommandInfo(name = "encode", permission = "net.op",requirePlayer = true)
public class EncodeCMD extends Command {
    public EncodeCMD(NotEnoughTree plugin) {
        super(plugin, "encode");
    }

    @Override
    public void execute(Player sender, String[] args) {
        ItemStack item = sender.getInventory().getItemInMainHand();
        if(item.getType().isAir()){
            sender.sendMessage("&cYou need to hold an item");
            return;
        }
        if(args.length!=1){
            sender.sendMessage(colorParse("&cWrong usage: /encode <key>"));
            return;
        }

        YamlConfiguration config = new YamlConfiguration();
        File file = new File(plugin.getDataFolder()+File.separator+"items.yml");
        if(file.exists()){
            try {
                config.load(file);
            } catch (IOException | InvalidConfigurationException e) {
                throw new RuntimeException(e);
            }
        }
        config.set(args[0],Encoder.encodeItem(item));
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> tab(CommandSender player, String[] args) {
        return List.of();
    }
}
