package net.brian.islandcore.crop.objects;

import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CropDrop {
    private final String type;
    private final String id;

    public CropDrop(String type,String id){
        this.type = type;
        this.id = id;
    }

    public ItemStack getItem(){
        ItemStack item = MMOItems.plugin.getItem(type,id);
        return item == null ? new ItemStack(Material.AIR) : item;
    }
}
