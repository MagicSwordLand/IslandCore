package net.brian.islandcore.data.listener;

import dev.reactant.reactant.core.component.Component;
import net.brian.islandcore.crop.events.IslandLoadEvent;
import net.brian.islandcore.crop.events.IslandUnloadEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import world.bentobox.bentobox.BentoBox;
import world.bentobox.bentobox.api.events.island.IslandEnterEvent;
import world.bentobox.bentobox.api.events.island.IslandExitEvent;
import world.bentobox.bentobox.database.objects.Island;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;


@Component
public class IslandEventsListener implements Listener {

    private final BentoBox bentoBox = BentoBox.getInstance();
    private final Logger logger = Logger.getLogger("IslandData");

    private void log(String msg){
        logger.log(Level.INFO,msg);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event){
        Optional<Island> island = bentoBox.getIslands().getIslandAt(event.getPlayer().getLocation());
        island.ifPresent(value -> {
            if(value.getPlayersOnIsland().size() == 1){
                IslandLoadEvent islandLoadEvent = new IslandLoadEvent(value,event.getPlayer());
                islandLoadEvent.callEvent();
                logger.log(Level.INFO,"Join event");
            }
        });
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event){
        Optional<Island> island = bentoBox.getIslands().getIslandAt(event.getPlayer().getLocation());
        island.ifPresent(value -> {
            if(value.getPlayersOnIsland().size() == 1){
                IslandUnloadEvent islandEvent = new IslandUnloadEvent(value,event.getPlayer());
                islandEvent.callEvent();
                log("quit event");
            }
        });
    }

    @EventHandler
    public void onEnter(IslandEnterEvent event){
        Island island = event.getIsland();
        if(island.getPlayersOnIsland().size() == 0){
            IslandLoadEvent islandLoadEvent = new IslandLoadEvent(island, Bukkit.getPlayer(event.getPlayerUUID()));
            islandLoadEvent.callEvent();
            log("enter event");
        }
    }

    @EventHandler
    public void onExit(IslandExitEvent event){
        Island island = event.getIsland();
        if(island.getPlayersOnIsland().size() == 1){
            IslandUnloadEvent islandUnloadEvent = new IslandUnloadEvent(island,Bukkit.getPlayer(event.getPlayerUUID()));
            islandUnloadEvent.callEvent();
            log("on Exit");
        }
    }

}
