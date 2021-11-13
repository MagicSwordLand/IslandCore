package net.brian.islandcore.crop.listener;

import dev.reactant.reactant.core.component.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;

@Component
public class BlockValinaGrowing implements Listener {



    @EventHandler
    public void onGrow(BlockGrowEvent event){
        if (event.getBlock().hasMetadata("island_uuid")){
            event.setCancelled(true);
            return;
        }
    }

}
