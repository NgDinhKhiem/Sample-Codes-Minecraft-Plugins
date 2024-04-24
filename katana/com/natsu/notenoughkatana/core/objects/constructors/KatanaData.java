package com.natsu.notenoughkatana.core.objects.constructors;

import com.natsu.notenoughkatana.core.objects.State;
import com.natsu.notenoughkatana.utils.Utils;
import org.bukkit.attribute.Attribute;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class KatanaData {
    private KatanaType type;
    private KatanaType originalType;
    private State state = State.GOOD;

    public KatanaData(KatanaType type) {
        this.type = type;
        this.originalType = type;
    }

    public void reduce1State(){
        this.state = state.next();
    }

    public void upgrade(ItemStack item){
        this.state = state.repair();
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        if(meta.getAttributeModifiers()!=null)
            if(meta.getAttributeModifiers(Attribute.GENERIC_MAX_HEALTH)!=null)
                meta.removeAttributeModifier(Attribute.GENERIC_MAX_HEALTH);
        meta.setDisplayName(Utils.normalize(this.state.name())+" "+Utils.removeAllPrefixes(meta.getDisplayName()));
        while (meta.getDisplayName().startsWith(" ")){
            if(meta.getDisplayName().length()<=1)
                break;
            meta.setDisplayName(meta.getDisplayName().substring(1));
        }
        item.setItemMeta(meta);
    }

    public void upgrade(){
        this.state = state.repair();
    }

    public void downgrade(ItemStack item){
        this.state = state.next();
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        if(meta.getAttributeModifiers()!=null)
            if(meta.getAttributeModifiers(Attribute.GENERIC_MAX_HEALTH)!=null)
                meta.removeAttributeModifier(Attribute.GENERIC_MAX_HEALTH);
        meta.setDisplayName(Utils.normalize(this.state.name())+" "+Utils.removeAllPrefixes(meta.getDisplayName()));
        while (meta.getDisplayName().startsWith(" ")){
            if(meta.getDisplayName().length()<=1)
                break;
            meta.setDisplayName(meta.getDisplayName().substring(1));
        }
        item.setItemMeta(meta);
    }
    public boolean isBroken(){
        return this.state==State.BROKEN;
    }

    public State getState() {
        return state;
    }

    public KatanaType getType() {
        return type;
    }
    public void setType(KatanaType type){this.type = type;}

    public int getLevel(){
        switch (state){
            case GOOD:
                return 1;
            case SHINY:
                return 2;
            case PRISTINE:
                return 3;
            default:
                return 0;
        }
    }

    public void repair() {
        this.state=State.GOOD;
    }

    public KatanaType getOriginalType() {
        return originalType;
    }

    public void setOriginalType(KatanaType originalType) {
        this.originalType = originalType;
    }
}
