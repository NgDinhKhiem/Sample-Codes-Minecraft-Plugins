package com.natsu.vendingmachinesplugin.command.commands;

import com.natsu.vendingmachinesplugin.VendingMachinesPlugin;
import com.natsu.vendingmachinesplugin.command.constructors.Command;
import com.natsu.vendingmachinesplugin.command.constructors.CommandInfo;
import com.natsu.vendingmachinesplugin.utils.GUIController;
import com.natsu.vendingmachinesplugin.utils.ItemDataContainer;
import com.natsu.vendingmachinesplugin.wrapper.InventoryContainer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@CommandInfo(name = "vmp",permission = "vmp.op",requirePlayer = true)
public class VMP extends Command {
    public VMP(VendingMachinesPlugin plugin) {
        super(plugin,"vmp");
    }

    @Override
    public void execute(Player sender, String[] args) {

        if(args.length==1){
            if(args[0].equalsIgnoreCase("restock")){
                this.plugin.getGuiManager().restock();
                sender.sendMessage(ChatColor.GREEN+"Restocked every machines");
                return;
            }
            if(args[0].equalsIgnoreCase("reload")){
                this.plugin.reload();
                sender.sendMessage(ChatColor.GREEN+"Reloaded config");
                return;
            }
            if(args[0].equalsIgnoreCase("listitem")){
                sender.sendMessage("[Items]");
                this.plugin.getServerData().getListItem().forEach(key->{
                    //sender.sendMessage("- "+key);
                    TextComponent get = new TextComponent(ChatColor.GREEN+"[GET]"+ChatColor.RESET);
                    get.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.YELLOW+"click to get this item")));
                    get.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/vmp getitem "+key));
                    sender.spigot().sendMessage(new TextComponent("- "+key+ " "),get);
                });
                sender.sendMessage("[Items]");
                return;
            }
            if(args[0].equalsIgnoreCase("help")){
                sender.sendMessage(ChatColor.GOLD+"[VENDING MACHINE HELPER]");
                sender.sendMessage(ChatColor.WHITE+"- /vpm help "+ChatColor.WHITE+": "+ChatColor.AQUA+"display this");
                sender.sendMessage(ChatColor.WHITE+"- /vpm reload "+ChatColor.WHITE+": "+ChatColor.AQUA+"reload config");
                sender.sendMessage(ChatColor.WHITE+"- /vpm listitem "+ChatColor.WHITE+": "+ChatColor.AQUA+"display list of items");
                sender.sendMessage(ChatColor.WHITE+"- /vpm open <gui-key> "+ChatColor.WHITE+": "+ChatColor.AQUA+"open chest ui");
                sender.sendMessage(ChatColor.WHITE+"- /vpm setgui <gui-key> "+ChatColor.WHITE+": "+ChatColor.AQUA+"add gui to holding block which can be placed to create a vending machine");
                sender.sendMessage(ChatColor.WHITE+"- /vpm additem <item-key> "+ChatColor.WHITE+": "+ChatColor.AQUA+"add item to the storage with the key to save all of its data");
                sender.sendMessage(ChatColor.WHITE+"- /vpm removeitem <item-key> "+ChatColor.WHITE+": "+ChatColor.AQUA+"remove item from the storage");
                sender.sendMessage(ChatColor.WHITE+"- /vpm getitem <item-key> "+ChatColor.WHITE+": "+ChatColor.AQUA+"get item with key");
                sender.sendMessage(ChatColor.GOLD+"[VENDING MACHINE HELPER]");
                return;
            }
        }

        if(args.length!=2) {
            sender.sendMessage(ChatColor.RED+"Wrong syntax");
            return;
        }

        switch (args[0].toLowerCase()){
            case "open":
                if(!this.plugin.getGuiManager().getListKeys().contains(args[1])) {
                    sender.sendMessage(ChatColor.RED+"There is no gui with that key");
                    return;
                }

                InventoryContainer inventoryContainer = this.plugin.getGuiManager().getGUI(args[1]);
                assert inventoryContainer != null;
                new GUIController(inventoryContainer,this.plugin,sender);
                return;
            case "setgui":
                ItemStack itemStack = sender.getInventory().getItemInMainHand();
                if(itemStack.getType().equals(Material.AIR)){
                    sender.sendMessage(ChatColor.RED+"You need to hold a block to use it");
                    return;
                }
                if(!itemStack.getType().isBlock()){
                    sender.sendMessage(ChatColor.RED+"You need to hold a block to use it");
                    return;
                }
                if(!this.plugin.getGuiManager().getListKeys().contains(args[1])) {
                    sender.sendMessage(ChatColor.RED+"There is no gui with that key");
                    return;
                }
                ItemDataContainer.addDataToItem(itemStack,args[1]);
                ItemMeta meta = itemStack.getItemMeta();
                assert meta != null;
                if(meta.getLore()!=null)
                    meta.getLore().add(ChatColor.YELLOW+"GUI-link: "+ChatColor.WHITE+args[1]);
                else meta.setLore(Collections.singletonList(ChatColor.YELLOW + "GUI-link: "+ChatColor.WHITE + args[1]));
                itemStack.setItemMeta(meta);
                sender.updateInventory();
                sender.sendMessage(ChatColor.GREEN+"Linked "+args[1]+" to the holding item");
                return;
            case "additem":
                ItemStack item = sender.getInventory().getItemInMainHand();
                if(item.getType().equals(Material.AIR)){
                    sender.sendMessage(ChatColor.RED+"You need to hold an item to use this");
                    return;
                }
                if(this.plugin.getServerData().getListItem().contains(args[1])){
                    sender.sendMessage(ChatColor.RED+"There is already item with that key");
                    return;
                }

                this.plugin.getServerData().addItem(args[1],item.clone());
                sender.sendMessage(ChatColor.GREEN+"Added "+args[1]+" to the storage");
                return;

            case "removeitem":
                if(!this.plugin.getServerData().getListItem().contains(args[1])){
                    sender.sendMessage(ChatColor.RED+"There is no item with that key");
                    return;
                }
                this.plugin.getServerData().removeItem(args[1]);
                sender.sendMessage(ChatColor.GREEN+"Remove "+args[1]+" from the storage");
                return;
            case "getitem":
                if(!this.plugin.getServerData().getListItem().contains(args[1])){
                    sender.sendMessage(ChatColor.RED+"There is no item with that key");
                    return;
                }
                sender.getInventory().addItem(this.plugin.getServerData().getItem(args[1]));
                sender.sendMessage(ChatColor.GREEN+"Given "+args[1]+" to "+sender.getName());
                return;
            default:
                sender.sendMessage(ChatColor.RED+"Wrong syntax");
                return;
        }
    }

    @Override
    public List<String> tab(CommandSender player, String[] args) {
        switch (args.length) {
            case 1:
                return new ArrayList<>(Arrays.asList("open","setgui","additem","listitem","removeitem","getitem","help","reload","restock"));
            case 2:
                if(args[0].equalsIgnoreCase("listitem")||
                        args[0].equalsIgnoreCase("help")||
                        args[0].equalsIgnoreCase("reload")||
                        args[0].equalsIgnoreCase("restock"))
                    return null;
                if(args[0].equalsIgnoreCase("additem"))
                    return new  ArrayList<>(Collections.singletonList("<key>"));
                if(args[0].equalsIgnoreCase("removeitem")||args[0].equalsIgnoreCase("getitem"))
                    return new ArrayList<>(this.plugin.getServerData().getListItem());
                return new ArrayList<>(this.plugin.getGuiManager().getListKeys());
        }
        return null;
    }
}
