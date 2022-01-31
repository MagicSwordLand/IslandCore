package net.brian.islandcore.gui.guis.cropsgui;

import dev.reactant.reactant.core.component.Component;
import dev.reactant.reactant.core.dependency.injection.Inject;
import net.brian.islandcore.crop.crops.Tomato;
import net.brian.islandcore.gui.objects.ActiveGui;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;


public class TomatoGUI extends CropLevelGUI{

    @Inject
    Tomato tomato;

    @Override
    public void onEnable(){
        guiManager.registerShop(this);
        islandCrop = tomato;
    }

    public TomatoGUI() {
        super("tomato");
        itemStackHashMap.put(1,createItem("§c番茄 I", Material.BLUE_DYE,new String[]{},1));
        itemStackHashMap.put(2,createItem("§c番茄 II",Material.RED_STAINED_GLASS_PANE,new String[]{}));
        itemStackHashMap.put(3,createItem("§c番茄 III",Material.RED_STAINED_GLASS_PANE,new String[]{}));
        itemStackHashMap.put(4,createItem("§c番茄 IV",Material.RED_STAINED_GLASS_PANE,new String[]{}));
        itemStackHashMap.put(5,createItem("§c番茄 V",Material.RED_STAINED_GLASS_PANE,new String[]{}));
        itemStackHashMap.put(6,createItem("§c番茄 VI",Material.RED_STAINED_GLASS_PANE,new String[]{}));
        itemStackHashMap.put(7,createItem("§c番茄 VII",Material.RED_STAINED_GLASS_PANE,new String[]{}));
        itemStackHashMap.put(8,createItem("§c番茄 VIII",Material.RED_STAINED_GLASS_PANE,new String[]{}));
        itemStackHashMap.put(9,createItem("§c番茄 IX",Material.RED_STAINED_GLASS_PANE,new String[]{}));
    }

    @Override
    public void clicked(HumanEntity player, ActiveGui activeGui, int slot) {

    }


}
