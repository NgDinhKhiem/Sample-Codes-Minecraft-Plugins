package com.mrnatsu.notenoughtree.utils;

import com.mrnatsu.notenoughtree.NotEnoughTree;
import com.mrnatsu.notenoughtree.listener.EventListener;
import com.mrnatsu.notenoughtree.manager.tree.StructurePattern;
import com.mrnatsu.notenoughtree.objects.Position;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static com.mrnatsu.notenoughtree.utils.Utils.colorParse;
import static com.mrnatsu.notenoughtree.utils.Utils.gson;

public class AreaStructManager extends EventListener {
    private Location firstCorner;
    private Location secondCorner;
    private StructurePattern loadedScheme = null;
    public AreaStructManager(NotEnoughTree plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onUsingSetPos(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(!isHoldingStructureAxe(player))
            return;
        Action action = event.getAction();
        if(event.getClickedBlock()==null)
            return;
        if(action.equals(Action.RIGHT_CLICK_BLOCK)){
            if(plugin.getCoolDownManager().isOnCoolDown(player.getUniqueId(),"RIGHTACTION"))
                return;
            plugin.getCoolDownManager().addCoolDown(player.getUniqueId(),"RIGHTACTION",5L);
            setSecondCorner(player,event.getClickedBlock().getLocation());
            event.setCancelled(true);
            return;
        }
        if(action.equals(Action.LEFT_CLICK_BLOCK)){
            setFirstCorner(player,event.getClickedBlock().getLocation());
            event.setCancelled(true);
            return;
        }
    }

    private boolean isStructureAxe(ItemStack item){
        if(item==null)
            return false;
        if(!item.hasItemMeta())
            return false;
        ItemMeta meta = item.getItemMeta();
        if(!meta.hasDisplayName())
            return false;
        return meta.getDisplayName().equals("Structure Axe");
    }

    private boolean isHoldingStructureAxe(Player player){
        return isStructureAxe(player.getInventory().getItemInMainHand());
    }

    private void setFirstCorner(Player player, Location location){
        this.firstCorner = location;
        player.sendMessage(colorParse("&aSet the first corner to: ("+location.getBlockX()+" "+location.getBlockY()+" "+location.getBlockZ()+")"));
        if(this.secondCorner!=null) {
            if (!secondCorner.getWorld().equals(firstCorner.getWorld())) {
                secondCorner = null;
                player.sendMessage(colorParse("&aSet the second corner to: &cNULL&f it is in another dimension"));
            }
        }
    }

    private void setSecondCorner(Player player, Location location){
        this.secondCorner = location;
        player.sendMessage(colorParse("&aSet the second corner to: ("+location.getBlockX()+" "+location.getBlockY()+" "+location.getBlockZ()+")"));
        if(this.firstCorner!=null) {
            if (!secondCorner.getWorld().equals(firstCorner.getWorld())) {
                firstCorner = null;
                player.sendMessage(colorParse("&aSet the first corner to: &cNULL&f it is in another dimension"));
            }
        }
    }

    public boolean hasFirstCorner(){
        return this.firstCorner!=null;
    }

    public boolean hasSecondCorner(){
        return this.secondCorner!=null;
    }
    public void saveScheme(String name){
        Location lowestCorner = new Location(
                firstCorner.getWorld(),
                Math.min(firstCorner.getBlockX(),secondCorner.getBlockX()),
                Math.min(firstCorner.getBlockY(),secondCorner.getBlockY()),
                Math.min(firstCorner.getBlockZ(),secondCorner.getBlockZ()));
        StructurePattern structurePattern = new StructurePattern();
        for(int i = Math.min(firstCorner.getBlockY(),secondCorner.getBlockY());i<=Math.max(firstCorner.getBlockY(),secondCorner.getBlockY());i++){
            for(int j = Math.min(firstCorner.getBlockX(),secondCorner.getBlockX());j<=Math.max(firstCorner.getBlockX(),secondCorner.getBlockX());j++){
                for(int k = Math.min(firstCorner.getBlockZ(),secondCorner.getBlockZ());k<=Math.max(firstCorner.getBlockZ(),secondCorner.getBlockZ());k++){
                    Location location = new Location(firstCorner.getWorld(), j,i,k);
                    if(location.getBlock().getType().isAir())
                        continue;
                    structurePattern.addBlock(
                            new Position(
                                    location.getBlockX()-lowestCorner.getBlockX(),
                                    location.getBlockY()-lowestCorner.getBlockY(),
                                    location.getBlockZ()-lowestCorner.getBlockZ()),
                            location.getBlock().getBlockData().getAsString()
                    );
                }
            }
        }
        File directory = new File(plugin.getDataFolder().getPath()+File.separator+"struct");
        if(!directory.exists()){
            if(directory.mkdir())
                Logger.log("Created new directory for schematics!");
        }

        File scheme = new File(directory.getPath()+File.separator+name+".struct");
        try {
            if (!scheme.exists()) {
                if (scheme.createNewFile()) {
                    Logger.log("Created new schematics file!");
                }
            }

            FileWriter fileWriter = new FileWriter(scheme);
            fileWriter.write(structurePattern.serialize());
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public StructurePattern loadScheme(String name, boolean... notLoad){
        File directory = new File(plugin.getDataFolder().getPath()+File.separator+"struct");
        if(!directory.exists()){
            if(directory.mkdir())
                Logger.log("Created new directory for schematics!");
        }

        File scheme = new File(directory.getPath()+File.separator+name+".struct");
        try {
            if (!scheme.exists()) {
                return new StructurePattern();
            }

            StringBuilder stringBuilder = new StringBuilder();
            Files.readAllLines(scheme.toPath(), StandardCharsets.UTF_8).forEach(stringBuilder::append);
            StructurePattern pattern = StructurePattern.deserialize(stringBuilder.toString());
            if(notLoad==null)
                loadedScheme = pattern;
            return pattern;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void pasteScheme(){
        if(loadedScheme==null)
            return;
        Location lowestCorner = new Location(
                firstCorner.getWorld(),
                firstCorner.getBlockX(),
                firstCorner.getBlockY(),
                firstCorner.getBlockZ());
        loadedScheme.placeBlock(lowestCorner);
    }

    public Location getFirstCorner() {
        return firstCorner;
    }

    public Location getSecondCorner() {
        return secondCorner;
    }
}
