package net.brian.islandcore.crop.crops;

import io.github.clayclaw.islandcore.season.SeasonType;
import net.Indyuce.mmoitems.MMOItems;
import net.brian.islandcore.common.objects.IslandLocation;
import net.brian.islandcore.crop.objects.ActiveCrop;
import net.brian.islandcore.crop.objects.CropDrop;
import net.brian.islandcore.crop.objects.IslandCropProfile;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public abstract class IslandCrop {

    protected CropDrop seed;
    protected CropDrop drop;
    protected String id = "";
    protected String name = "";
    protected int grow_time = 100;
    protected int max_stage = 5;

    protected SeasonType mainSeason = SeasonType.SPRING;
    protected SeasonType weakSeason = SeasonType.WINTER;

    protected HashMap<Integer, Integer> stageMap = new HashMap<>();


    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public abstract Block instantiate(IslandLocation location, long age);

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
        return stageMap.getOrDefault(stage,grow_time);
    }

    public int getMaxStage(){
        return max_stage;
    }

    public abstract void updateAppearance(int stage,Block block);

    public SeasonType getMainSeason() {
        return mainSeason;
    }
    public SeasonType getWeakSeason() {
        return weakSeason;
    }

    public ItemStack getDrop(){
        return drop.getItem();
    }
}
