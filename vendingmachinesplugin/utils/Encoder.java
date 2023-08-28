package com.natsu.vendingmachinesplugin.utils;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public final class Encoder {
    public static String encodeItem(ItemStack itemStack){
        String EncodedString = "";
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream outputStream = new BukkitObjectOutputStream(byteArrayOutputStream);
            outputStream.writeObject(itemStack);
            outputStream.close();
            EncodedString= Base64Coder.encodeLines(byteArrayOutputStream.toByteArray());
        }catch (Exception e){
            throw new IllegalStateException(e);
        }
        return EncodedString.replaceAll("\\r\\n|\\r|\\n","%space");
    }

    public static ItemStack decodeItem(String EncodedString){
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Base64Coder.decodeLines(EncodedString.replaceAll("%space","\n")));
            BukkitObjectInputStream inputStream = new BukkitObjectInputStream(byteArrayInputStream);
            ItemStack itemStack = (ItemStack) inputStream.readObject();
            return itemStack;
        }catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
