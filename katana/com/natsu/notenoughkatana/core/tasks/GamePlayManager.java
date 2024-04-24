package com.natsu.notenoughkatana.core.tasks;

import com.natsu.notenoughkatana.NotEnoughKatanaPlugin;
import com.natsu.notenoughkatana.core.objects.constructors.KatanaData;
import com.natsu.notenoughkatana.core.objects.constructors.KatanaDeathPlayer;
import com.natsu.notenoughkatana.core.objects.constructors.KatanaObject;
import com.natsu.notenoughkatana.core.objects.constructors.KatanaType;
import com.natsu.notenoughkatana.utils.Encoder;
import com.natsu.notenoughkatana.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReference;

import static com.natsu.notenoughkatana.utils.Utils.colorParse;

public class GamePlayManager implements Listener {
    private final NotEnoughKatanaPlugin plugin;
    private final File dataFolder;
    private final File playerKatanaFolder;
    private final Map<UUID, KatanaDeathPlayer> playerDeathKatanaHolder = new HashMap<>();
    private final Map<UUID, KatanaData> playerKatanaMap = new HashMap<>();

    public GamePlayManager(NotEnoughKatanaPlugin plugin) {
        this.plugin = plugin;
        dataFolder = new File(this.plugin.getDataFolder().getPath()+File.separator+"banData");
        playerKatanaFolder = new File(this.plugin.getDataFolder().getPath()+File.separator+"playerKatanas");
    }

    public void register(){
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
        //File reading
    }
    public void run(){
        new BukkitRunnable() {
            @Override
            public void run() {
                for(Player player: Bukkit.getOnlinePlayers()){
                    processPlayer(player);
                    processPlayerOffHand(player);
                }
            }
        }.runTaskTimer(plugin,0,0);
    }
    private void processPlayer(Player player){
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if(itemStack.getType().isAir())
            return;
        if(!isKatana(itemStack))
            return;
        getKatana(itemStack).holding(player);
    }

    private void processPlayerOffHand(Player player){
        ItemStack itemStack = player.getInventory().getItemInOffHand();
        if(itemStack.getType().isAir())
            return;
        if(!isKatana(itemStack))
            return;
        getKatana(itemStack).holding(player);
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    private void onTriggerClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if(itemStack.getType().isAir())
            return;
        if(!isKatana(itemStack))
            return;
        if(!this.plugin.getCdManager().addCoolDown(player.getUniqueId(),"PLAYERINTERACT",5L))
            return;
        getKatana(itemStack).triggerAbility(player,getClickType(event));
    }
    //Removed lots of codes

    public boolean isKatana(ItemStack item){
        if(item == null)
            return false;
        if(!item.hasItemMeta())
            return false;
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
        if(dataContainer.isEmpty())
            return false;
        if(!dataContainer.has(this.plugin.getNamespacedKey(), PersistentDataType.STRING))
            return false;
        return true;
    }

    public boolean isHoldingKatana(Player player){
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if(itemStack.getType().isAir())
            return false;
        if(!isKatana(itemStack))
            return false;
        return true;
    }

    public KatanaObject getKatana(ItemStack itemStack){
        if (!isKatana(itemStack))
            return null;
        String data = Objects.requireNonNull(itemStack.getItemMeta()).getPersistentDataContainer().get(this.plugin.getNamespacedKey(),PersistentDataType.STRING);
        KatanaData katanaData = this.plugin.getGson().fromJson(data, KatanaData.class);
        return katanaData.getType().getKatanaObject(this.plugin,katanaData);
    }

    public ItemStack setKatana(ItemStack itemStack, KatanaObject katanaObject){
        if (!isKatana(itemStack))
            return null;
        ItemMeta meta = itemStack.getItemMeta();
        assert meta != null;
        meta.getPersistentDataContainer().set(this.plugin.getNamespacedKey(), PersistentDataType.STRING,this.plugin.getGson().toJson(katanaObject.getKatanaData()));
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public void save() {
        //save files
    }

    private KatanaObject getPlayerInitialKatana(UUID player){
        if(!this.playerKatanaMap.containsKey(player)){
            this.playerKatanaMap.put(player, new KatanaData(KatanaType.random()));
        }
        return this.playerKatanaMap.get(player).getType().getKatanaObject(plugin,this.playerKatanaMap.get(player));
    }

    private void givePlayerKatana(Player player, KatanaObject katana){
        for(ItemStack itemStack:player.getInventory()){
            if(isKatana(itemStack)){
                updatePlayerKatana(player);
                return;
            }
        }
    }

    private void forceGivePlayerKatana(Player player, KatanaObject katana){
        player.getInventory().addItem(katana.getItem());
    }

    private void isFullInventory(Player player){

    }

    private void updatePlayerKatana(Player player){

    }

    private void changePlayerKatanaType(Player player, KatanaType katanaType){
        KatanaObject katanaObject = getPlayerInitialKatana(player.getUniqueId());
        katanaObject.getKatanaData().setType(katanaType);
        this.playerKatanaMap.put(player.getUniqueId(),katanaObject.getKatanaData());
        updatePlayerKatana(player);
    }

    public void forceChangePlayerKatanaType(Player player, KatanaType katanaType){
        KatanaObject katanaObject = getPlayerInitialKatana(player.getUniqueId());
        katanaObject.getKatanaData().setOriginalType(katanaType);
        katanaObject.getKatanaData().setType(katanaType);
        this.playerKatanaMap.put(player.getUniqueId(),katanaObject.getKatanaData());
        updatePlayerKatana(player);
    }

    public void upgradePlayerKatana(Player player){
        KatanaObject katanaObject = getPlayerInitialKatana(player.getUniqueId());
        katanaObject.getKatanaData().upgrade();
        this.playerKatanaMap.put(player.getUniqueId(),katanaObject.getKatanaData());
        updatePlayerKatana(player);
    }
}
