package net.brian.islandcore.crop.listener;

import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import dev.reactant.reactant.core.component.Component;
import dev.reactant.reactant.core.dependency.injection.Inject;
import net.brian.islandcore.common.objects.IslandLogger;
import net.brian.islandcore.crop.IslandCropService;
import net.brian.islandcore.crop.objects.ActiveCrop;
import net.brian.islandcore.crop.objects.IslandCropProfile;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;

@Component
public class CropBreakListener implements Listener {

    @Inject
    IslandCropService cropService;


    @EventHandler(ignoreCancelled = true)
    public void onBreak(BlockDestroyEvent event){
        if(cropService.isCrop(event.getBlock())){
            IslandCropProfile profile = cropService.getProfileFromCrop(event.getBlock());
            ActiveCrop activeCrop = profile.getCrop(event.getBlock());
            if(activeCrop != null){
                profile.remove(event.getBlock());
                activeCrop.drop();
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerBreak(BlockBreakEvent event){
        if(cropService.isCrop(event.getBlock())){
            IslandCropProfile profile = cropService.getProfileFromCrop(event.getBlock());
            ActiveCrop activeCrop = profile.getCrop(event.getBlock());
            if(activeCrop != null) {
                profile.remove(event.getBlock());
                activeCrop.drop();
            }
        }
    }

    @EventHandler
    public void onWater(BlockFromToEvent event){
        if(cropService.isCrop(event.getToBlock())){
            Block block = event.getToBlock();
            IslandCropProfile profile = cropService.getProfileFromCrop(block);
            ActiveCrop activeCrop = profile.getCrop(block);
            if(activeCrop != null) {
                profile.remove(block);
                activeCrop.drop();
            }
        }
    }

    @EventHandler
    public void onPiston(BlockPistonExtendEvent event){
        for(Block block:event.getBlocks()){
            if(cropService.isCrop(block)){
                IslandCropProfile profile = cropService.getProfileFromCrop(block);
                ActiveCrop activeCrop = profile.getCrop(block);
                if(activeCrop != null) {
                    profile.remove(block);
                    activeCrop.drop();
                    if(activeCrop.getStage() == activeCrop.getCropType().getMaxStage()){
                        profile.addCount(activeCrop.getCropType().getId());
                    }
                }
            }
        }
    }

}
