package net.brian.islandcore.gui.objects;

import net.brian.islandcore.gui.interfaces.GuiAction;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public abstract class GuiItem {

    private final ItemStack itemStack;
    private final GuiAction guiAction;
    private final String name;

    public GuiItem(String name, Material material, String[] lore, GuiAction guiAction){
        this.name = name;
        this.guiAction = guiAction;
        itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(Arrays.stream(lore).toList());
        itemStack.setItemMeta(itemMeta);
    }



    public void process(ItemStack itemStack, HumanEntity humanEntity, String islandUUID){
        guiAction.process(itemStack,humanEntity,islandUUID);
    }

    public ItemStack getItemStack() {
        return itemStack.clone();
    }

    public String getName() {
        return name;
    }
}
