package net.brian.islandcore.livestock;

import dev.reactant.reactant.core.component.Component;
import dev.reactant.reactant.core.component.lifecycle.LifeCycleHook;
import dev.reactant.reactant.core.dependency.injection.Inject;
import net.brian.islandcore.common.persistent.Namespaces;
import net.brian.islandcore.data.IslandDataService;
import net.brian.islandcore.livestock.livestocks.GoldenCow;
import net.brian.islandcore.livestock.livestocks.LiveStock;
import net.brian.islandcore.livestock.objects.ActiveLiveStock;
import net.brian.islandcore.livestock.objects.IslandLiveStockProfile;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;

@Component
public class LiveStockManager implements LifeCycleHook {

    @Inject IslandDataService dataService;

    private final HashMap<String, LiveStock> liveStockHashMap = new HashMap<>();


    @Override
    public void onEnable(){
        ActiveLiveStock.liveStockManager = this;
        dataService.register("livestock", IslandLiveStockProfile.class);
        liveStockHashMap.put("Golden_Cow",new GoldenCow());
    }

    public LiveStock getLiveStock(String id){
        return liveStockHashMap.get(id);
    }

    public ActiveLiveStock getActiveLiveStock(Entity entity){
        IslandLiveStockProfile profile = getOwner(entity);
        return profile.getLiveStock(entity);
    }

    public boolean isLiveStock(Entity entity){
        return entity.getPersistentDataContainer().has(Namespaces.islandID,PersistentDataType.STRING);
    }

    public IslandLiveStockProfile getOwner(Entity entity){
        String islandID = entity.getPersistentDataContainer().get(Namespaces.islandID, PersistentDataType.STRING);
        if(islandID == null) return null;
        return getData(islandID);
    }

    public IslandLiveStockProfile getData(String uuid){
        return dataService.getData(uuid,IslandLiveStockProfile.class);
    }

}
