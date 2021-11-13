package net.brian.islandcore.livestock.objects;

import net.brian.islandcore.common.persistent.Namespaces;
import net.brian.islandcore.data.gson.PostProcessable;
import net.brian.islandcore.data.gson.PostQuitProcessable;
import net.brian.islandcore.data.objects.IslandData;
import net.brian.islandcore.livestock.livestocks.LiveStock;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class IslandLiveStockProfile extends IslandData implements PostProcessable, PostQuitProcessable {

    public final static NamespacedKey islandKey = Namespaces.islandID;
    public final static NamespacedKey stockKey = Namespaces.stock_type;

    private transient final HashMap<Entity,ActiveLiveStock> liveStockEntityMap;


    private final List<ActiveLiveStock> activeLiveStocks = new ArrayList<>();
    private Long lastSave;

    public IslandLiveStockProfile(){
        liveStockEntityMap = new HashMap<>();
    }

    public IslandLiveStockProfile(String uuid){
        super(uuid);
        liveStockEntityMap = new HashMap<>();
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


    public ActiveLiveStock getLiveStock(Entity entity){
        return liveStockEntityMap.get(entity);
    }


    @Override
    public void gsonPostDeserialize() {
        int ageAmount = (int) ((System.currentTimeMillis()-lastSave)/1000);
        activeLiveStocks.forEach(liveStock->{
            liveStockEntityMap.put(liveStock.getEntity(),liveStock);
            liveStock.age(ageAmount);
        });
    }


    @Override
    public void gsonPostSerialize() {
        lastSave = System.currentTimeMillis();
        activeLiveStocks.removeIf(liveStock -> {
            Entity entity = liveStock.getEntity();
            if(entity == null){
                return true;
            }
            return entity.isDead();
        });
    }


    @Override
    public void onQuit() {
        activeLiveStocks.forEach(liveStock->{
            liveStock.getEntity().remove();
        });
    }

}
