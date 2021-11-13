package net.brian.islandcore.crop.listener;

import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import dev.reactant.reactant.core.component.Component;
import dev.reactant.reactant.core.dependency.injection.Inject;
import net.brian.islandcore.common.objects.IslandLogger;
import net.brian.islandcore.crop.IslandCropService;
import net.brian.islandcore.crop.objects.ActiveCrop;
import net.brian.islandcore.crop.objects.IslandCropProfile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

@Component
public class CropBreakListener implements Listener {

    @Inject
    IslandCropService cropService;


    @EventHandler(ignoreCancelled = true)
    public void onBreak(BlockDestroyEvent event){
        if(cropService.isCrop(event.getBlock())){
            IslandCropProfile profile = cropService.getProfileFromCrop(event.getBlock());
            ActiveCrop activeCrop = profile.getCrop(event.getBlock());
            profile.remove(event.getBlock());
            activeCrop.drop();
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerBreak(BlockBreakEvent event){
        if(cropService.isCrop(event.getBlock())){
            IslandCropProfile profile = cropService.getProfileFromCrop(event.getBlock());
            ActiveCrop activeCrop = profile.getCrop(event.getBlock());
            profile.remove(event.getBlock());
            activeCrop.drop();
        }
    }
}
