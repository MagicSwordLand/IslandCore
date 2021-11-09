package net.brian.islandcore.livestock.objects;

import net.brian.islandcore.IslandCore;
import net.brian.islandcore.common.persistent.Namespaces;
import net.brian.islandcore.data.gson.PostProcessable;
import net.brian.islandcore.livestock.livestocks.LiveStock;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class IslandLiveStockProfile implements PostProcessable {

    public final static NamespacedKey islandKey = Namespaces.islandID;
    public final static NamespacedKey stockKey = Namespaces.stock_type;

    private transient final HashMap<Entity,ActiveLiveStock> liveStockEntityMap = new HashMap<>();


    private final List<ActiveLiveStock> activeLiveStocks = new ArrayList<>();
    private String uuid;


    public IslandLiveStockProfile(){}

    public void onQuit(){
        activeLiveStocks.forEach(liveStock->{
            liveStock.getEntity().remove();
        });
    }

    public void spawn(LiveStock liveStock, Location location){
        ActiveLiveStock activeLiveStock = new ActiveLiveStock(liveStock,location);
        Entity liveStockEntity = activeLiveStock.getEntity();
        liveStockEntity.getPersistentDataContainer().set(islandKey, PersistentDataType.STRING,uuid);
        liveStockEntity.getPersistentDataContainer().set(stockKey,PersistentDataType.STRING,liveStock.getId());
        activeLiveStocks.add(activeLiveStock);
        liveStockEntityMap.put(liveStockEntity,activeLiveStock);
    }

    public void remove(Entity entity){
        ActiveLiveStock activeLiveStock = liveStockEntityMap.get(entity);
        liveStockEntityMap.remove(entity);
        activeLiveStocks.remove(activeLiveStock);
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public ActiveLiveStock getLiveStock(Entity entity){
        return liveStockEntityMap.get(entity);
    }

    @Override
    public void gsonPostDeserialize() {
        activeLiveStocks.forEach(liveStock->{
            liveStockEntityMap.put(liveStock.getEntity(),liveStock);
        });
    }

    @Override
    public void gsonPostSerialize() {

    }
}
