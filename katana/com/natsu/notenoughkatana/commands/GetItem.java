package com.natsu.notenoughkatana.commands;

import com.natsu.notenoughkatana.NotEnoughKatanaPlugin;
import com.natsu.notenoughkatana.core.objects.constructors.KatanaData;
import com.natsu.notenoughkatana.core.objects.constructors.KatanaType;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static com.natsu.notenoughkatana.utils.Utils.getMatchList;

public class GetItem extends SpigotCommand {

    public GetItem(NotEnoughKatanaPlugin plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(args.length!=2)
            return true;
        if(!(commandSender instanceof Player))
            return true;
        switch (args[0].toLowerCase()){
            case "katana":{
                try {
                    KatanaType katanaType = KatanaType.valueOf(args[1].toUpperCase());
                    ((Player)commandSender).getInventory().addItem(katanaType.getKatanaObject(this.plugin, new KatanaData(katanaType)).getItem());
                }catch (IllegalArgumentException e){
                    ((Player)commandSender).sendMessage("No Katana founds");
                }
                return true;
            }
            case "item":{
                switch (args[1]){
                    case "upgrader": {
                        ((Player)commandSender).getInventory().addItem(this.plugin.getUpgrader());
                        return true;
                    }
                    case "trader":{
                        ((Player)commandSender).getInventory().addItem(this.plugin.getTrader());
                        return true;
                    }
                    case "reviver":{
                        ((Player)commandSender).getInventory().addItem(this.plugin.getReviverManager().getReviver());
                        return true;
                    }
                    case "crown":{
                        ((Player)commandSender).getInventory().addItem(this.plugin.getItem().getCrown());
                        return true;
                    }
                    case "doom_scythe":{
                        ((Player)commandSender).getInventory().addItem(this.plugin.getItem().getDoomScythe());
                        return true;
                    }
                    case "poseidon_trident":{
                        ((Player)commandSender).getInventory().addItem(this.plugin.getItem().getPosTrident());
                        return true;
                    }
                    case "dust_bow":{
                        ((Player)commandSender).getInventory().addItem(this.plugin.getItem().getDustBow());
                        return true;
                    }
                    default:{
                        ((Player)commandSender).sendMessage("No item founds");
                    }

                }
                return true;
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        switch (args.length){
            case 1:
                return getMatchList(args[0], "katana", "item");
            case 2:{
                switch (args[0].toLowerCase()){
                    case "katana":{
                        List<String> listKatana = new ArrayList<>();
                        for(KatanaType katanaType:KatanaType.values()){
                            listKatana.add(katanaType.name());
                        }
                        return getMatchList(args[1], listKatana);
                    }
                    case "item":{
                        return getMatchList(args[1],"upgrader", "trader","reviver","crown","doom_scythe","poseidon_trident","dust_bow");
                    }
                }
            }
        }
        return null;
    }
}
