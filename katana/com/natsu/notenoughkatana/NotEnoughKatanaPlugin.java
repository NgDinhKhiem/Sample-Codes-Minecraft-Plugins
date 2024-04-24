package com.natsu.notenoughkatana;

import com.google.gson.Gson;
import com.natsu.notenoughkatana.commands.GetItem;
import com.natsu.notenoughkatana.commands.SpigotCommand;
import com.natsu.notenoughkatana.core.items.Item;
import com.natsu.notenoughkatana.core.items.ItemHandler;
import com.natsu.notenoughkatana.core.repcipe.DragonSword;
import com.natsu.notenoughkatana.core.repcipe.Reviver;
import com.natsu.notenoughkatana.core.repcipe.Trader;
import com.natsu.notenoughkatana.core.repcipe.Upgrader;
import com.natsu.notenoughkatana.core.tasks.CDManager;
import com.natsu.notenoughkatana.core.tasks.GamePlayManager;
import com.natsu.notenoughkatana.eventlisteners.BlockDamageProtectionEvent;
import com.natsu.notenoughkatana.eventlisteners.EntityTargetEvent;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.LocalDate;

public final class NotEnoughKatanaPlugin extends JavaPlugin {
    private final GamePlayManager gamePlayManager = new GamePlayManager(this);
    private final CDManager cdManager = new CDManager(this);
    private final NamespacedKey namespacedKey = new NamespacedKey(this,"KATANA");
    private ItemHandler itemHandler;
    private final Gson gson = new Gson();

    @Override
    public void onEnable() {
        loadCommands();
        saveDefaultConfig();
        gamePlayManager.register();
        gamePlayManager.run();
        upgrader.register();
        trader.register();
        reviver.register();
        dragonSword.register();
        itemHandler = new ItemHandler(this,item);
        registerListeners();
        LocalDate targetDate = LocalDate.of(2025, 4, 25);

        LocalDate currentDate = LocalDate.now();

        if (!currentDate.isBefore(targetDate)) {
            this.getServer().getPluginManager().disablePlugin(this);
        }
    }

    private void loadCommands(){
        registerCommands("getitem",new GetItem(this));
    }
    private void registerListeners(){
        fireballBlockDamageProtection.register();
        entityTargetEvent.register();
    }

    private void registerCommands(String name, SpigotCommand command){
        if(this.getCommand(name)==null){
            System.out.println(name+" is not defined in the config (ERR666)");
            return;
        }
        this.getCommand(name).setExecutor(command);
        this.getCommand(name).setTabCompleter(command);
    }

    @Override
    public void onDisable() {
        entityTargetEvent.onServerReload();
        gamePlayManager.save();
    }

    public GamePlayManager getGamePlayerManager() {
        return gamePlayManager;
    }

    public CDManager getCdManager() {
        return cdManager;
    }

    public NamespacedKey getNamespacedKey() {
        return namespacedKey;
    }

    public Gson getGson() {
        return gson;
    }

    public ItemHandler getItemHandler() {
        return itemHandler;
    }
}
