package net.brian.islandcore.crop.crops;

import dev.reactant.reactant.core.component.Component;
import dev.reactant.reactant.core.component.lifecycle.LifeCycleHook;
import dev.reactant.reactant.core.dependency.injection.Inject;
import dev.reactant.reactant.service.spec.config.Config;
import io.github.clayclaw.islandcore.IslandCore;
import me.casperge.realisticseasons.season.Season;
import net.brian.islandcore.common.objects.IslandLogger;
import net.brian.islandcore.crop.config.CropAppearance;
import net.brian.islandcore.crop.config.CropLevelConfig;
import net.brian.islandcore.crop.objects.ActiveCrop;
import net.brian.islandcore.crop.config.CropDrop;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ItemFrame;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

@Component
public class Tomato extends IslandCrop implements RightClickCrop, LifeCycleHook {

    ItemStack stage0,stage1,stage2,stage3,stage4;

    @Inject(name = "plugins/IslandCrop/CropLevel/tomato.json")
    Config<CropLevelConfig> cropLevelConfigConfig;


    @Override
    public void onEnable(){
        cropLevelConfig = cropLevelConfigConfig.getContent();
        cropService.registerCrop("tomato",this);
    }

    public Tomato(){
        super("tomato");
        seed = new CropDrop("SEED","TOMATO_SEED");
        drop = new CropDrop("CROP","TOMATO");
        this.grow_time = 2401;
        this.name = "§c番茄";
        stageMap.put(0,400);
        stageMap.put(1,900);
        stageMap.put(2,1400);
        stageMap.put(3,2401);
        max_stage = 4;
        weakSeason = Season.WINTER;
        mainSeason = Season.SUMMER;
        stage0 = new CropAppearance(Material.BLUE_DYE.name(),3).getItemStack();
        stage1 = new CropAppearance(Material.BLUE_DYE.name(),4).getItemStack();
        stage2 = new CropAppearance(Material.BLUE_DYE.name(),5).getItemStack();
        stage3 = new CropAppearance(Material.BLUE_DYE.name(),6).getItemStack();
        stage4 = new CropAppearance(Material.BLUE_DYE.name(),7).getItemStack();
    }


    @Override
    public void updateAppearance(int stage, ItemFrame itemFrame) {
        switch (stage){
            case 1: {
                itemFrame.setItem(stage1);
                break;
            }
            case 2: {
                itemFrame.setItem(stage2);
                break;
            }
            case 3: {
                itemFrame.setItem(stage3);
                break;
            }
            case 4: {
                itemFrame.setItem(stage4);
                break;
            }
        }
    }

    @Override
    public ItemFrame instantiate(Location location,String islandUUID) {
        ItemFrame frame;
        try {
            frame=location.getWorld().spawn(location,ItemFrame.class,itemFrame->{
                itemFrame.getPersistentDataContainer().set(ActiveCrop.cropTypeKey,PersistentDataType.STRING,id);
                itemFrame.setVisible(false);
                itemFrame.setItem(stage0);
                itemFrame.setFacingDirection(BlockFace.UP);
                itemFrame.getPersistentDataContainer().set(IslandCore.islandKey, PersistentDataType.STRING,islandUUID);
            });
        }catch (IllegalArgumentException e){
            IslandLogger.logInfo("Failed to spawn crop ");
            return null;
        }
        return frame;
    }

    @Override
    public boolean checkLocation(Location loc) {
        if(loc.getBlock().getType().equals(Material.AIR)){
            Material mat = loc.add(0,-1,0).getBlock().getType();
            return mat.equals(Material.GRASS_BLOCK) || mat.equals(Material.DIRT) || mat.equals(Material.FARMLAND);
        }
        return false;
    }


    @Override
    public void onRightClick(ActiveCrop activeCrop) {
        rightClickDrop(activeCrop,drop,900);
    }

}
