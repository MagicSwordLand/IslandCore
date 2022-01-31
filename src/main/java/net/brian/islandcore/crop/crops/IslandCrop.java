package net.brian.islandcore.crop.crops;

import dev.reactant.reactant.core.component.lifecycle.LifeCycleHook;
import dev.reactant.reactant.core.dependency.injection.Inject;
import me.casperge.realisticseasons.season.Season;
import net.brian.islandcore.crop.IslandCropService;
import net.brian.islandcore.crop.config.CropLevelConfig;
import net.brian.islandcore.crop.objects.ActiveCrop;
import net.brian.islandcore.crop.config.CropDrop;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.ItemFrame;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public abstract class IslandCrop implements LifeCycleHook {

    @Inject
    IslandCropService cropService;


    protected CropDrop seed;
    protected CropDrop drop;
    protected final String id;
    protected String name = "";
    protected int grow_time = 100;
    protected int max_stage = 5;

    protected Season mainSeason = Season.SPRING;
    protected Season weakSeason = Season.WINTER;

    protected HashMap<Integer, Integer> stageMap = new HashMap<>();
    protected CropLevelConfig cropLevelConfig;

    protected IslandCrop(String id){
        this.id = id;
    }

    @Override
    public void onEnable(){
        cropService.registerCrop(name,this);
    }


    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }


    public int getGrow_time() {
        return grow_time;
    }

    public void drop(ActiveCrop activeCrop, int stage, Location location){
        location.getWorld().dropItem(location,seed.getItem());
        if(stage>=getMaxStage()/2){
            location.getWorld().dropItem(location,seed.getItem());
        }
        if(stage == getMaxStage()){
            location.getWorld().dropItem(location,drop.getItem());
            activeCrop.getIsland().addCount(activeCrop.getCropType().id);
        }
    }

    public int getNextStageRequire(int stage) {
        return stageMap.getOrDefault(stage,Integer.MAX_VALUE);
    }

    public int getMaxStage(){
        return max_stage;
    }

    public abstract void updateAppearance(int stage, ItemFrame itemFrame);
    public abstract ItemFrame instantiate(Location location, String islandUUID);

    public Season getMainSeason() {
        return mainSeason;
    }
    public Season getWeakSeason() {
        return weakSeason;
    }

    public ItemStack getDrop(){
        return drop.getItem();
    }

    public CropLevelConfig getCropLevelConfig(){
        return cropLevelConfig;
    }

    protected void rightClickDrop(ActiveCrop crop, CropDrop cropDrop, int minusAmount){
        if(crop.getStage() >= getMaxStage()){
            crop.downAge(minusAmount);
            Location location = crop.getLocation().getBukkitLoc();;
            location.getWorld().dropItem(location, cropDrop.getItem());
            crop.getIsland().addCount(crop.getCropType().id);
            location.getWorld().playSound(location, Sound.BLOCK_CROP_BREAK,1,1);
        }
    }

    public abstract boolean checkLocation(Location loc);

}
