package com.natsu.notenoughkatana.core.objects.constructors;

import com.natsu.notenoughkatana.NotEnoughKatanaPlugin;
import com.natsu.notenoughkatana.utils.Encoder;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;

public class KatanaDeathPlayer {
    private final String skull;
    private UUID uuid;
    private final KatanaData katanaData;
    private final String katanaString;
    private final boolean isDeath;
    private boolean revived = false;
    public KatanaDeathPlayer(OfflinePlayer player, UUID uuid, KatanaData katanaData, String katanaString, boolean isDeath) {
        this.uuid = uuid;
        this.katanaData = katanaData;
        this.isDeath = isDeath;
        this.katanaString = katanaString;
        ItemStack item = new ItemStack(Material.PLAYER_HEAD,1);
        SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
        assert skullMeta != null;
        skullMeta.setOwningPlayer(player);
        item.setItemMeta(skullMeta);
        this.skull = Encoder.encodeItem(item);
    }

    public void corr(UUID uuid){
        this.uuid = uuid;
    }

    public KatanaType getKatana() {
        return katanaData.getType();
    }

    public boolean isRevived() {
        return revived;
    }

    public void revive() {
        this.revived = true;
    }

    public ItemStack getSkull() {
        return Encoder.decodeItem(skull);
    }

    public KatanaData getKatanaData() {
        return katanaData;
    }

    public ItemStack getItem(NotEnoughKatanaPlugin plugin){
        if(this.katanaString==null)
            return katanaData.getType().getKatanaObject(plugin,katanaData).getItem();
        return Encoder.decodeItem(this.katanaString);
    }

    public boolean isDeath() {
        return isDeath;
    }

    public UUID getUuid() {
        return uuid;
    }
}
