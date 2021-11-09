package net.brian.islandcore.livestock.objects;

import net.brian.islandcore.livestock.LiveStockService;
import net.brian.islandcore.livestock.livestocks.LiveStock;
import net.brian.islandcore.common.objects.IslandLocation;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class ActiveLiveStock {
    public static LiveStockService liveStockManager;

    transient Entity entity;
    transient LiveStock liveStock;

    String type;
    IslandLocation location;
    int age;
    int food;

    public ActiveLiveStock(LiveStock liveStock, Location location){
        this.location = new IslandLocation(location);
        type = liveStock.getId();
        this.liveStock = liveStock;
    }

    public Entity instantiate(){
        liveStock = liveStockManager.getLiveStock(type);
        entity = liveStock.instantiate(location.getLocation());
        return entity;
    }

    public void onSave(){
        location = new IslandLocation(entity.getLocation());
    }

    public Entity getEntity(){
        return entity;
    }

    public void drop(){

    }

}
