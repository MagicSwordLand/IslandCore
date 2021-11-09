package net.brian.islandcore.livestock.listener;

import dev.reactant.reactant.core.component.Component;
import dev.reactant.reactant.core.dependency.injection.Inject;
import net.brian.islandcore.livestock.LiveStockManager;
import net.brian.islandcore.livestock.objects.ActiveLiveStock;
import net.brian.islandcore.livestock.objects.IslandLiveStockProfile;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

@Component
public class IslandEntityListener implements Listener {

    @Inject
    LiveStockManager liveStockManager;

    @EventHandler
    public void onDeath(EntityDeathEvent event){
        Entity entity = event.getEntity();
        IslandLiveStockProfile profile = liveStockManager.getOwner(entity);
        if(profile != null){
            profile.remove(entity);
            ActiveLiveStock liveStock = profile.getLiveStock(entity);
            liveStock.drop();
        }
    }


}
