package net.brian.islandcore.crop.listener;

import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import dev.reactant.reactant.core.component.Component;
import dev.reactant.reactant.core.dependency.injection.Inject;
import io.github.clayclaw.islandcore.IslandCore;
import net.brian.islandcore.crop.IslandCropService;
import net.brian.islandcore.crop.objects.ActiveCrop;
import net.brian.islandcore.crop.objects.IslandCropProfile;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

@Component
public class CropBreakListener implements Listener {

    @Inject
    IslandCropService cropService;

    IslandCore plugin = IslandCore.getInstance();


    @EventHandler(ignoreCancelled = true,priority = EventPriority.HIGHEST)
    public void onItemFrameRemove(HangingBreakEvent event){
        Entity entity = event.getEntity();
        if(entity instanceof ItemFrame itemFrame){
            if(IslandCropService.isCrop(entity)){
                IslandCropProfile cropProfile = cropService.getProfileFromCrop(itemFrame);
                event.setCancelled(true);
                if(cropProfile != null){
                    ActiveCrop activeCrop = cropProfile.getCrop(itemFrame);
                    if(activeCrop != null){
                        cropProfile.getCrop(itemFrame).drop();
                        cropProfile.remove(itemFrame);
                    }
                    itemFrame.remove();
                    itemFrame.getWorld().playSound(itemFrame.getLocation(), Sound.BLOCK_GRASS_BREAK,1,1);
                }
            }
        }
    }

    @EventHandler
    public void onItemFrameDamage(EntityDamageByEntityEvent event){
        if(event.getEntity() instanceof ItemFrame itemFrame){
            if(IslandCropService.isCrop(event.getEntity())){
                IslandCropProfile cropProfile = cropService.getProfileFromCrop(event.getEntity());
                event.setCancelled(true);
                if(cropProfile != null){
                    ActiveCrop activeCrop = cropProfile.getCrop((ItemFrame) event.getEntity());
                    if(activeCrop != null){
                        cropProfile.getCrop((ItemFrame) event.getEntity()).drop();
                        cropProfile.remove(itemFrame);
                        itemFrame.getWorld().playSound(itemFrame.getLocation(), Sound.BLOCK_GRASS_BREAK,1,1);
                    }
                    event.getEntity().remove();
                }
            }
        }
    }

}
