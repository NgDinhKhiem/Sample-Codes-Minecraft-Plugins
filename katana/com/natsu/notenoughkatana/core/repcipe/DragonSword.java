package com.natsu.notenoughkatana.core.repcipe;

import com.natsu.notenoughkatana.NotEnoughKatanaPlugin;
import com.natsu.notenoughkatana.core.katanas.DragonKatana;
import com.natsu.notenoughkatana.core.objects.constructors.KatanaData;
import com.natsu.notenoughkatana.core.objects.constructors.KatanaType;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ShapedRecipe;

public class DragonSword implements Listener {
    private final NotEnoughKatanaPlugin plugin;

    public DragonSword(NotEnoughKatanaPlugin plugin) {
        this.plugin = plugin;
    }
    public void register(){
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
        ShapedRecipe upgraderRecipe = new ShapedRecipe(new NamespacedKey(this.plugin,"DRAGON_KATANA_RECIPE"), new DragonKatana(this.plugin, new KatanaData(KatanaType.DRAGON_KATANA)).getItem());
        upgraderRecipe.shape(" X "," Y "," Z ");
        upgraderRecipe.setIngredient('X', Material.NETHER_STAR);
        upgraderRecipe.setIngredient('Y', Material.DRAGON_EGG);
        upgraderRecipe.setIngredient('Z', Material.STICK);
        this.plugin.getServer().addRecipe(upgraderRecipe);
    }
}
