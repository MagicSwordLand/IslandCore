package net.brian.islandcore.crop.crops;

import net.brian.islandcore.IslandCropsAndLiveStocks;
import net.brian.islandcore.common.objects.IslandLocation;
import net.brian.islandcore.crop.events.IslandLoadEvent;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.event.Listener;

public abstract class IslandCrop implements Listener {

    private String id;

    protected int grow_time;
    protected String material;
    protected HarvestType harvestType;



    public String getId() {
        return id;
    }

    public abstract Block instantiate(IslandLocation location, long age);


    public NamespacedKey getKey(){
        return new NamespacedKey(IslandCropsAndLiveStocks.getInstance(),id);
    }
}
