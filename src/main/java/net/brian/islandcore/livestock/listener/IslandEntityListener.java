package net.brian.islandcore.livestock.listener;

import dev.reactant.reactant.core.component.Component;
import dev.reactant.reactant.core.dependency.injection.Inject;
import net.brian.islandcore.livestock.LiveStockService;
import net.brian.islandcore.livestock.objects.ActiveLiveStock;
import net.brian.islandcore.livestock.objects.IslandLiveStockProfile;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

@Component
public class IslandEntityListener implements Listener {

    @Inject
    LiveStockService liveStockManager;

    @EventHandler
    public void onDeath(EntityDeathEvent event){
        Entity entity = event.getEntity();
        IslandLiveStockProfile profile = liveStockManager.getFromLiveStock(entity);
        if(profile != null){
            profile.remove(entity);
            ActiveLiveStock liveStock = profile.getLiveStock(entity);
            if(liveStock != null){
                liveStock.drop();
            }
        }
    }


}
