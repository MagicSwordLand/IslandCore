package net.brian.islandcore.crop.listener;

import dev.reactant.reactant.core.component.Component;
import io.github.clayclaw.islandcore.IslandCore;
import net.brian.islandcore.crop.events.IslandLoadEvent;
import net.brian.islandcore.crop.events.IslandUnloadEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@Component
public class IslandDataManager implements Listener {
    @EventHandler
    public void onLoad(IslandLoadEvent event){
        IslandCore.getLog().info("Island loaded");
    }

    @EventHandler
    public void onQuit(IslandUnloadEvent event){
        IslandCore.getLog().info("Island unloaded");
    }
}
