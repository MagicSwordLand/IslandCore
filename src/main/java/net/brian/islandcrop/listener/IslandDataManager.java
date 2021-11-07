package net.brian.islandcrop.listener;

import net.brian.islandcrop.IslandCrop;
import net.brian.islandcrop.events.IslandLoadEvent;
import net.brian.islandcrop.events.IslandUnloadEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class IslandDataManager implements Listener {
    @EventHandler
    public void onLoad(IslandLoadEvent event){
        IslandCrop.log("Island loaded");
    }

    @EventHandler
    public void onQuit(IslandUnloadEvent event){
        IslandCrop.log("Island unloaded");
    }
}
