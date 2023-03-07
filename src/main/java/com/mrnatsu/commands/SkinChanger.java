package com.mrnatsu.commands;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

@CommandInfo(name = "skin",permission = "random.skin",requirePlayer = true)
public class SkinChanger extends Command{
    public SkinChanger(Plugin plugin) {
        super(plugin);
    }
    @Override
    public void execute(Player sender, String[] args) {
        if(args.length!=1) return;
        changeSkin(sender,args[0]);
    }

    private void changeSkin(Player player,String skinName){
        //methods
    }
}
