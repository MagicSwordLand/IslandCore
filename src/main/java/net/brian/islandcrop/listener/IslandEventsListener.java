package net.brian.islandcrop.listener;

import net.brian.islandcrop.IslandCrop;
import net.brian.islandcrop.events.IslandLoadEvent;
import net.brian.islandcrop.events.IslandUnloadEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import world.bentobox.bentobox.BentoBox;
import world.bentobox.bentobox.api.events.island.IslandEnterEvent;
import world.bentobox.bentobox.api.events.island.IslandExitEvent;
import world.bentobox.bentobox.api.events.island.IslandRegisteredEvent;
import world.bentobox.bentobox.api.events.island.IslandUnregisteredEvent;
import world.bentobox.bentobox.database.objects.Island;
import world.bentobox.bentobox.managers.PlayersManager;

import java.util.Optional;

public class IslandEventsListener implements Listener {

    private final BentoBox bentoBox;
    private final PlayersManager playersManager;

    public IslandEventsListener(BentoBox bentoBox){
        this.bentoBox = bentoBox;
        this.playersManager = bentoBox.getPlayersManager();
    }


    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event){
       Optional<Island> island = bentoBox.getIslands().getIslandAt(event.getPlayer().getLocation());
        island.ifPresent(value -> {
            if(value.getPlayersOnIsland().size() == 1){
                IslandLoadEvent islandLoadEvent = new IslandLoadEvent(value,event.getPlayer());
                islandLoadEvent.callEvent();
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
            }
        });
    }


    @EventHandler
    public void onEnter(IslandEnterEvent event){
        Island island = event.getIsland();
        if(island.getPlayersOnIsland().size() == 0){
            IslandLoadEvent islandLoadEvent = new IslandLoadEvent(island, Bukkit.getPlayer(event.getPlayerUUID()));
            islandLoadEvent.callEvent();
        }
    }

    @EventHandler
    public void onExit(IslandExitEvent event){
        Island island = event.getIsland();
        if(island.getPlayersOnIsland().size() == 1){
            IslandUnloadEvent islandUnloadEvent = new IslandUnloadEvent(island,Bukkit.getPlayer(event.getPlayerUUID()));
            islandUnloadEvent.callEvent();
        }
    }

}
