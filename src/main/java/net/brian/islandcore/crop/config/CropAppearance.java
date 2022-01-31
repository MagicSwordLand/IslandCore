package net.brian.islandcore.crop.config;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Locale;

public class CropAppearance {

    private final ItemStack itemStack;

    public CropAppearance(String mat,int model){
        itemStack = new ItemStack(Material.valueOf(mat.toUpperCase()));
        ItemMeta meta = itemStack.getItemMeta();
        meta.setCustomModelData(model);
        itemStack.setItemMeta(meta);
    }

    public ItemStack getItemStack(){
        return itemStack;
    }

}
