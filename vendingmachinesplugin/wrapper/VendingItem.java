package com.natsu.vendingmachinesplugin.wrapper;

import org.bukkit.inventory.ItemStack;

public class VendingItem {
    private final double cost;
    private final ItemStack displayItem;
    private final ItemStack givingItem;
    private long stock= 0;
    public VendingItem(double cost,ItemStack displayItem,ItemStack givingItem,long stock){
        this.cost=cost;
        this.displayItem=displayItem;
        this.givingItem=givingItem;
        this.stock=stock;
    }

    public double getCost() {
        return cost;
    }

    public ItemStack getDisplayItem() {
        return displayItem;
    }

    public ItemStack getGivingItem() {
        return givingItem;
    }

    public void removeStock(){
        if(stock==0)
            throw new RuntimeException("There is no stock left");
        stock--;
    }

    public boolean inStock(){
        return stock>0;
    }
}
