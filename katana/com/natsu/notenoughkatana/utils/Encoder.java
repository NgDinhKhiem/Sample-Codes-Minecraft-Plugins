package com.natsu.notenoughkatana.utils;

import org.bukkit.Material;
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
        if(EncodedString==null)
            return new ItemStack(Material.STONE);
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Base64Coder.decodeLines(EncodedString.replaceAll("%space","\n")));
            BukkitObjectInputStream inputStream = new BukkitObjectInputStream(byteArrayInputStream);
            return (ItemStack) inputStream.readObject();
        }catch (IOException | ClassNotFoundException e) {

            return new ItemStack(Material.STONE);
        }
    }

}