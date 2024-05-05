package com.mrnatsu.notenoughtree.objects;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.inventory.ClickType;

public class ClickHolder {
    private final OfflinePlayer clicker;
    private final ClickType e;

    public ClickHolder(OfflinePlayer clicker, ClickType e) {
        this.clicker = clicker;
        this.e = e;
    }

    public OfflinePlayer getClicker(){return this.clicker;}
    public boolean isLeftClick(){return this.e.isLeftClick();}
}
