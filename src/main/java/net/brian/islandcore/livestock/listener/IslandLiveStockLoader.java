package net.brian.islandcore.livestock.listener;

import dev.reactant.reactant.core.component.Component;
import dev.reactant.reactant.core.dependency.injection.Inject;
import net.brian.islandcore.crop.events.IslandUnloadEvent;
import net.brian.islandcore.data.IslandDataService;
import net.brian.islandcore.data.events.IslandDataLoadCompleteEvent;
import net.brian.islandcore.data.events.IslandDataPrepareSaveEvent;
import net.brian.islandcore.livestock.objects.IslandLiveStockProfile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import world.bentobox.bentobox.database.objects.Island;

@Component
public class IslandLiveStockLoader implements Listener {

    @Inject
    IslandDataService dataService;

    @EventHandler
    public void onLoad(IslandDataLoadCompleteEvent event){
        Island island = event.getIsland();
        IslandLiveStockProfile profile = dataService.getData(island.getUniqueId(),IslandLiveStockProfile.class);
        profile.setUuid(island.getUniqueId());
    }

    @EventHandler
    public void onSave(IslandDataPrepareSaveEvent event){
        Island island = event.getIsland();
        IslandLiveStockProfile profile = dataService.getData(island.getUniqueId(),IslandLiveStockProfile.class);
        profile.onSave();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onQuit(IslandUnloadEvent event){
        Island island = event.getIsland();
        IslandLiveStockProfile profile = dataService.getData(island.getUniqueId(),IslandLiveStockProfile.class);
        profile.onQuit();
    }

}
