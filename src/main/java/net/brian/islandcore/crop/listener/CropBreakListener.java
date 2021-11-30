package net.brian.islandcore.crop.listener;

import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import dev.reactant.reactant.core.component.Component;
import dev.reactant.reactant.core.dependency.injection.Inject;
import io.github.clayclaw.islandcore.IslandCore;
import net.brian.customblocks.CustomBlocks;
import net.brian.customblocks.blocks.DataBlock;
import net.brian.customblocks.events.CustomBlockBreakEvent;
import net.brian.islandcore.common.objects.IslandLogger;
import net.brian.islandcore.common.persistent.BlockMeta;
import net.brian.islandcore.crop.IslandCropService;
import net.brian.islandcore.crop.crops.RightClickCrop;
import net.brian.islandcore.crop.objects.ActiveCrop;
import net.brian.islandcore.crop.objects.IslandCropProfile;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.player.PlayerInteractEvent;

@Component
public class CropBreakListener implements Listener {

    @Inject
    IslandCropService cropService;

    IslandCore plugin = IslandCore.getInstance();

    @EventHandler
    public void onBreak(CustomBlockBreakEvent event){

        if(cropService.isCrop(event.getBlock())){
            IslandCropProfile profile = cropService.getProfileFromCrop(event.getBlock());
            ActiveCrop activeCrop = profile.getCrop(event.getBlock());
            if(activeCrop != null) {
                profile.remove(event.getBlock());
                activeCrop.drop();
            }
            event.getBlock().removeMetadata("island_uuid",plugin);
        }
    }

    @EventHandler
    public void onHarvest(PlayerInteractEvent event){
        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && !event.getPlayer().isSneaking()){
            if(cropService.isCrop(event.getClickedBlock())){
                IslandCropProfile profile = cropService.getProfileFromCrop(event.getClickedBlock());
                ActiveCrop activeCrop = profile.getCrop(event.getClickedBlock());
                if(activeCrop.getCropType() instanceof RightClickCrop){
                    ((RightClickCrop) activeCrop.getCropType()).onRightClick(activeCrop);
                }
            }
        }
    }

    @EventHandler
    public void onBlockUnderBreak(BlockBreakEvent event){
        Block cropBlock = event.getBlock().getLocation().add(0,1,0).getBlock();
        if(cropBlock.hasMetadata(DataBlock.blockKey)){
            CustomBlocks.getInstance().getBlockManager().breakBlock(cropBlock);
        }
    }

    @EventHandler
    public void onPistomMoveUnderBlock(BlockPistonExtendEvent event){
        for(Block block : event.getBlocks()){
            Block cropBlock = block.getLocation().add(0,1,0).getBlock();
            if(cropBlock.hasMetadata(DataBlock.blockKey)){
                CustomBlocks.getInstance().getBlockManager().breakBlock(cropBlock);
            }
        }
    }



    /*
    @EventHandler(ignoreCancelled = true)
    public void onBreak(BlockDestroyEvent event){
        if(cropService.isCrop(event.getBlock())){
            event.getBlock().removeMetadata("island_uuid",plugin);
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
            event.getBlock().removeMetadata("island_uuid",plugin);
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
            event.getBlock().removeMetadata("island_uuid",plugin);
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
                event.getBlock().removeMetadata("island_uuid",plugin);
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
    }*/

}
