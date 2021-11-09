package net.brian.islandcore.crop.listener;

import dev.reactant.reactant.core.component.Component;
import dev.reactant.reactant.core.dependency.injection.Inject;
import net.brian.islandcore.crop.objects.IslandCropProfile;
import net.brian.islandcore.data.IslandDataService;
import net.brian.islandcore.data.events.IslandDataLoadCompleteEvent;
import net.brian.islandcore.data.events.IslandDataPrepareSaveEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import world.bentobox.bentobox.database.objects.Island;

@Component
public class IslandCropSetup implements Listener {

    @Inject
    IslandDataService dataService;

    @EventHandler
    public void onPrepareSave(IslandDataPrepareSaveEvent event){
        Island island = event.getIsland();
        IslandCropProfile cropProfile = dataService.getData(island.getUniqueId(), IslandCropProfile.class);
        cropProfile.setLastSaved(System.currentTimeMillis());
    }


}
