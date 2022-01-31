package net.brian.islandcore.crop.config;

import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public record CropDrop(String type, String id) {

    public ItemStack getItem() {
        ItemStack item = MMOItems.plugin.getItem(type, id);
        return item == null ? new ItemStack(Material.AIR) : item;
    }

}
