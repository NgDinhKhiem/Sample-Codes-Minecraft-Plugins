package com.natsu.notenoughkatana.core.gui;

import com.natsu.notenoughkatana.NotEnoughKatanaPlugin;
import com.natsu.notenoughkatana.core.objects.constructors.KatanaDeathPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

import static com.natsu.notenoughkatana.utils.Utils.colorParse;

public class RevivingGUI implements Listener {
    private final NotEnoughKatanaPlugin plugin;
    private final Player player;
    private final Inventory inventory;
    private static final String inventoryName = colorParse("&l&bRevive the chosen one");

    public RevivingGUI(NotEnoughKatanaPlugin plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.inventory = Bukkit.createInventory(null,54,inventoryName);
        this.player.closeInventory();
        this.player.openInventory(this.inventory);
        this.plugin.getServer().getPluginManager().registerEvents(this,this.plugin);
        buildInventory();
    }
    private static final ItemStack backGround = buildItem(Material.BLACK_STAINED_GLASS_PANE,"&b");
    private static final List<Integer> slotPlayerRevive = List.of(
            10,11,12,13,14,15,16,19,20,21,22,23,24,25,27,28,29,30,31,32,33,34,37,38,39,40,41,42,43);
    private void buildInventory(){
        List<Integer> slotBackGround = List.of(
                0,1,2,3,4,5,6,7,8,
                9,17,
                18,26,
                27,35,
                36,44,
                45,46,47,48,49,50,51,52,53);

        slotBackGround.forEach(s->inventory.setItem(s,backGround));

        List<KatanaDeathPlayer> listRevivePlayer = this.plugin.getGamePlayerManager().getListDeathPlayer();

        for(int i=0;i< listRevivePlayer.size()&&i<slotPlayerRevive.size();i++){
            ItemStack item = buildItem(listRevivePlayer.get(i).getSkull(),Bukkit.getOfflinePlayer(listRevivePlayer.get(i).getUuid()).getName(),colorParse("&eCLICK &fto revive this player"));
            this.inventory.setItem(slotPlayerRevive.get(i),item);
        }
    }
    @EventHandler
    private void onInventoryClose(InventoryCloseEvent event){
        Player clicker = (Player) event.getPlayer();
        if(!clicker.getUniqueId().equals(player.getUniqueId()))
            return;
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    private void onInventoryClick(InventoryClickEvent event){
        Player clicker = (Player) event.getWhoClicked();
        if(!event.getView().getTitle().equals(inventoryName)){
            return;
        }
        if(!clicker.getUniqueId().equals(player.getUniqueId()))
            return;
        event.setCancelled(true);
        if(!event.getClickedInventory().equals(event.getView().getTopInventory()))
            return;
        int slot = event.getSlot();
        if(!slotPlayerRevive.contains(slot))
            return;
        if(inventory.getItem(slot)==null)
            return;
        ItemStack item = inventory.getItem(slot);
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(item.getItemMeta().getDisplayName());
        this.plugin.getGamePlayerManager().revivePlayer(offlinePlayer);
        for(ItemStack index:player.getInventory()){
            if(item==null)
                continue;
            if(!this.plugin.getReviverManager().getReviver().isSimilar(index))
                continue;
            index.setAmount(index.getAmount()-1);
            break;
        }
        this.player.closeInventory();
    }

    private static ItemStack buildItem(Material material, String name, String... lore){
        return buildItem(new ItemStack(material), name, lore);
    }

    private static ItemStack buildItem(ItemStack item, String name, String... lore_){
        name = colorParse(name);
        List<String> lore = colorParse(lore_);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}
