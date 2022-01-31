package net.brian.islandcore.gui.interfaces;

import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;

public interface GuiAction {
    void process(ItemStack itemStack, HumanEntity humanEntity, String islandUUID);
}
