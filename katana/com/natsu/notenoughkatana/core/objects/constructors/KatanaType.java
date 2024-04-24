package com.natsu.notenoughkatana.core.objects.constructors;

import com.natsu.notenoughkatana.NotEnoughKatanaPlugin;
import com.natsu.notenoughkatana.core.katanas.*;

import java.util.Random;

public enum KatanaType {
    FIRE_KATANA,
    ENDER_KATANA,
    WEALTH_KATANA,
    LIFE_KATANA,
    STRENGTH_KATANA,
    SPEED_KATANA,
    MOB_KATANA,
    WIND_KATANA,
    ENERGY_KATANA,
    SPACE_KATANA,
    DRAGON_KATANA;

    public static KatanaType random() {
        KatanaType katanaType = DRAGON_KATANA;
        while (katanaType==null||katanaType==DRAGON_KATANA){
            katanaType = KatanaType.values()[new Random().nextInt(0,1000)%KatanaType.values().length];
        }
        return katanaType;
    }

    public KatanaObject getKatanaObject(NotEnoughKatanaPlugin plugin, KatanaData data) {
        return Somethings:d
    }
}
