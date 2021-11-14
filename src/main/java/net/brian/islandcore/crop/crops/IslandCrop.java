package net.brian.islandcore.crop.crops;

import io.github.clayclaw.islandcore.season.SeasonType;
import net.brian.islandcore.IslandCropsAndLiveStocks;
import net.brian.islandcore.common.objects.IslandLocation;
import net.brian.islandcore.crop.events.IslandLoadEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.event.Listener;
import org.checkerframework.common.value.qual.IntRangeFromGTENegativeOne;

import java.util.HashMap;

public abstract class IslandCrop implements Listener {

    protected String id;
    protected String name;
    protected int grow_time;
    protected transient Material material;
    protected HarvestType harvestType;
    protected SeasonType mainSeason;
    protected SeasonType weakSeason;

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

    public abstract void drop(int age,Location location);

    public int getNextStageRequire(int stage) {
        return stageMap.getOrDefault(stage,grow_time);
    }

    public abstract int getMaxStage();

    public abstract void updateAppearance(int stage,Block block);

    public SeasonType getMainSeason() {
        return mainSeason;
    }
    public SeasonType getWeakSeason() {
        return weakSeason;
    }
}
