package com.natsu.notenoughkatana.core.objects;

import org.bukkit.entity.LivingEntity;

public class ExtraData { //this should be record instead but I prefer traditional
    private final boolean isAnnouncement;
    private final LivingEntity target;
    private final boolean ignoreCoolDown;

    public ExtraData(boolean isAnnouncement, LivingEntity target) {
        this.isAnnouncement = isAnnouncement;
        this.target = target;
        this.ignoreCoolDown = false;
    }

    public ExtraData(boolean isAnnouncement, LivingEntity target, boolean ignoreCoolDown) {
        this.isAnnouncement = isAnnouncement;
        this.target = target;
        this.ignoreCoolDown = ignoreCoolDown;
    }

    public boolean isAnnouncement() {
        return isAnnouncement;
    }

    public LivingEntity getTarget() {
        return target;
    }

    public boolean isIgnoreCoolDown() {
        return ignoreCoolDown;
    }
}
