package net.brian.islandcore.gui.guis.cropsgui;

import dev.reactant.reactant.core.component.lifecycle.LifeCycleHook;
import dev.reactant.reactant.core.dependency.injection.Inject;
import net.brian.islandcore.crop.IslandCropService;
import net.brian.islandcore.crop.config.CropLevelConfig;
import net.brian.islandcore.crop.crops.IslandCrop;
import net.brian.islandcore.crop.objects.IslandCropProfile;
import net.brian.islandcore.gui.objects.ActiveGui;
import net.brian.islandcore.gui.objects.Gui;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class  CropLevelGUI extends Gui implements LifeCycleHook {

    @Inject
    IslandCropService cropService;

    protected IslandCrop islandCrop;
    protected final String crop;
    protected HashMap<Integer,ItemStack> itemStackHashMap = new HashMap<>();

    public CropLevelGUI(String id) {
        super(id);
        crop = id;
    }



    @Override
    public Inventory createGui(Player player, String islandUUID) {
        IslandCropProfile cropProfile = cropService.getProfile(islandUUID);
        CropLevelConfig cropLevelConfig = new CropLevelConfig();
        long harvestCount = cropProfile.getHarvestCount(crop);
        int cropLevel = cropLevelConfig.getLevel(harvestCount);
        Inventory inv = Bukkit.createInventory(null,9,"");
        itemStackHashMap.forEach((level,item)->{
            ItemStack itemStack = item.clone();
            if(level>cropLevel){
                inv.setItem(level-1,setAchieved(itemStack));
            }
            if(level == cropLevel){
                inv.setItem(level-1,setInProcess(itemStack,harvestCount,cropLevelConfig.getLevelRequire(level)));
            }
            else{
                inv.setItem(level-1,setUnAchieved(itemStack));
            }
        });

        return inv;
    }

    protected ItemStack setInProcess(ItemStack itemStack,long current,long total){
        itemStack.setType(Material.YELLOW_STAINED_GLASS_PANE);
        double progress = current*1.0/total;
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = itemMeta.getLore() == null ? new ArrayList<>() : itemMeta.getLore();
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<=1;i+=0.1){
            if(i<progress){
                builder.append("§a-");
            }
            else{
                builder.append("§c-");
            }
        }
        lore.add("§7進度: §e"+Math.round(progress*10)/10+"§6%");
        lore.add(builder+" §e"+current+"§6/§e"+total);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    protected ItemStack setAchieved(ItemStack itemStack){
        itemStack.setType(Material.GREEN_STAINED_GLASS_PANE);
        return itemStack;
    }

    protected ItemStack setUnAchieved(ItemStack itemStack){
        itemStack.setType(Material.RED_STAINED_GLASS_PANE);
        return itemStack;
    }

}
