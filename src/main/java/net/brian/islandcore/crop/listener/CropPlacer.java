package net.brian.islandcore.crop.listener;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class CropPlacer implements Listener {

    @EventHandler
    public void onRightClick(PlayerInteractEvent event){
        Block block = event.getClickedBlock();
        if(block == null) return;



    }
}
