package net.brian.islandcore.livestock.objects;

import net.brian.islandcore.data.gson.PostProcessable;
import net.brian.islandcore.livestock.LiveStockService;
import net.brian.islandcore.livestock.livestocks.LiveStock;
import net.brian.islandcore.common.objects.IslandLocation;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class ActiveLiveStock implements PostProcessable {
    public static LiveStockService liveStockManager;

    private transient Entity entity;
    private transient LiveStock liveStock;

    String type;
    IslandLocation location;
    int age;
    int food;

    public ActiveLiveStock(LiveStock liveStock, Location location){
        this.location = new IslandLocation(location);
        type = liveStock.getId();
        this.liveStock = liveStock;
        spawn();
    }


    public Entity getEntity(){
        return entity;
    }

    public void drop(){

    }

    public void spawn() {
        if(entity != null){
            entity.remove();
        }
        liveStock = liveStockManager.getLiveStock(type);
        entity = liveStock.instantiate(location.getLocation());
    }

    @Override
    public void gsonPostDeserialize() {
        liveStock = liveStockManager.getLiveStock(type);
        entity = liveStock.instantiate(location.getLocation());
    }

    @Override
    public void gsonPostSerialize() {
        location = new IslandLocation(entity.getLocation());
    }
}
