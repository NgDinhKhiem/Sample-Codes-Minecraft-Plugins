package com.natsu.notenoughkatana.core.items;

import com.natsu.notenoughkatana.NotEnoughKatanaPlugin;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;
import java.util.UUID;

import static com.natsu.notenoughkatana.utils.Utils.colorParse;

public class Item {
    private final NotEnoughKatanaPlugin plugin;
    private ItemStack crown;
    private final NamespacedKey crownKey;
    private ItemStack doomScythe;
    private final NamespacedKey doomScytheKey;
    private ItemStack posTrident;
    private final NamespacedKey posTridentKey;
    private ItemStack dustBow;
    private final NamespacedKey dustBowKey;
    private ItemStack witheredAxe;
    private final NamespacedKey witheredAxeKey;

    public Item(NotEnoughKatanaPlugin plugin) {
        this.plugin = plugin;
        crownKey = new NamespacedKey(plugin, "crownItem");
        doomScytheKey = new NamespacedKey(plugin, "doomScythe");
        posTridentKey = new NamespacedKey(plugin, "posTrident");
        dustBowKey = new NamespacedKey(plugin, "dustBow");
        witheredAxeKey = new NamespacedKey(plugin, "witheredAxe");
        loadItems();
    }

    void loadItems(){
        ConfigurationSection section = this.plugin.getConfig().getConfigurationSection("Items.Crown");
        crown = new ItemStack(Material.valueOf(section.getString("material").toUpperCase()));
        ItemMeta meta = crown.getItemMeta();
        meta.setLore(colorParse(section.getStringList("lore")));
        meta.setDisplayName(colorParse(section.getString("name")));
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE,new AttributeModifier(UUID.randomUUID(), "generic.attack_damage",section.getInt("damage",10), AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,7,true);
        meta.setUnbreakable(true);
        meta.getPersistentDataContainer().set(crownKey, PersistentDataType.BOOLEAN,true);
        meta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH,new AttributeModifier(UUID.randomUUID(),"generic.max_health", 10,AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD));
        crown.setItemMeta(meta);

        section = this.plugin.getConfig().getConfigurationSection("Items.DoomScythe");
        doomScythe = new ItemStack(Material.valueOf(section.getString("material").toUpperCase()));
        meta = doomScythe.getItemMeta();
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE,new AttributeModifier(UUID.randomUUID(), "generic.attack_damage",section.getInt("damage",10), AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        meta.setLore(colorParse(section.getStringList("lore")));
        meta.setDisplayName(colorParse(section.getString("name")));
        meta.setUnbreakable(true);
        meta.getPersistentDataContainer().set(doomScytheKey, PersistentDataType.BOOLEAN,true);
        doomScythe.setItemMeta(meta);

        section = this.plugin.getConfig().getConfigurationSection("Items.PoseidonTrident");
        posTrident = new ItemStack(Material.valueOf(section.getString("material").toUpperCase()));
        meta = posTrident.getItemMeta();
        meta.setLore(colorParse(section.getStringList("lore")));
        meta.addEnchant(Enchantment.LOYALTY,10,true);
        meta.setUnbreakable(true);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE,new AttributeModifier(UUID.randomUUID(), "generic.attack_damage",section.getInt("damage",10), AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        meta.setDisplayName(colorParse(section.getString("name")));
        meta.getPersistentDataContainer().set(posTridentKey, PersistentDataType.BOOLEAN,true);
        posTrident.setItemMeta(meta);

        section = this.plugin.getConfig().getConfigurationSection("Items.DustBow");
        dustBow = new ItemStack(Material.valueOf(section.getString("material").toUpperCase()));
        meta = dustBow.getItemMeta();
        meta.setUnbreakable(true);
        meta.setLore(colorParse(section.getStringList("lore")));
        meta.setDisplayName(colorParse(section.getString("name")));
        meta.getPersistentDataContainer().set(dustBowKey, PersistentDataType.BOOLEAN,true);
        dustBow.setItemMeta(meta);

        section = this.plugin.getConfig().getConfigurationSection("Items.WitheredAxe");
        witheredAxe = new ItemStack(Material.valueOf(section.getString("material").toUpperCase()));
        meta = witheredAxe.getItemMeta();
        meta.setUnbreakable(true);
        meta.setLore(colorParse(section.getStringList("lore")));
        meta.setDisplayName(colorParse(section.getString("name")));
        meta.getPersistentDataContainer().set(witheredAxeKey, PersistentDataType.BOOLEAN,true);
        witheredAxe.setItemMeta(meta);
    }

    public ItemStack getCrown() {
        return crown.clone();
    }

    public ItemStack getDoomScythe(){
        return this.doomScythe.clone();
    }

    public ItemStack getPosTrident(){
        return this.posTrident.clone();
    }

    public ItemStack getDustBow(){
        return this.dustBow.clone();
    }
    public ItemStack getWitheredAxe(){return this.witheredAxe.clone();}

    public boolean isCrown(ItemStack item){
        if(item==null)
            return false;
        if(!item.hasItemMeta())
            return false;
        return Objects.requireNonNull(item.getItemMeta()).getPersistentDataContainer().has(crownKey, PersistentDataType.BOOLEAN);
    }

    public boolean isDoomScythe(ItemStack item){
        if(item==null)
            return false;
        if(!item.hasItemMeta())
            return false;
        return Objects.requireNonNull(item.getItemMeta()).getPersistentDataContainer().has(doomScytheKey, PersistentDataType.BOOLEAN);
    }

    public boolean isPosTrident(ItemStack item){
        return false;
        /*if(item==null)
            return false;
        if(!item.hasItemMeta())
            return false;
        return Objects.requireNonNull(item.getItemMeta()).getPersistentDataContainer().has(posTridentKey, PersistentDataType.BOOLEAN);*/
    }
    public boolean isDustBow(ItemStack item){
        if(item==null)
            return false;
        if(!item.hasItemMeta())
            return false;
        return Objects.requireNonNull(item.getItemMeta()).getPersistentDataContainer().has(dustBowKey, PersistentDataType.BOOLEAN);
    }

    public boolean isWitheredAxe(ItemStack item){
        if(item==null)
            return false;
        if(!item.hasItemMeta())
            return false;
        return Objects.requireNonNull(item.getItemMeta()).getPersistentDataContainer().has(witheredAxeKey, PersistentDataType.BOOLEAN);
    }
}
