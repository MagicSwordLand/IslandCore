package net.brian.islandcore.data.listener;

import dev.reactant.reactant.core.component.Component;
import dev.reactant.reactant.core.dependency.injection.Inject;
import io.github.clayclaw.islandcore.IslandCore;
import net.brian.islandcore.common.objects.IslandLogger;
import net.brian.islandcore.data.IslandDataHandlerImpl;
import net.brian.islandcore.data.IslandDataService;
import net.brian.islandcore.data.events.IslandActiveEvent;
import net.brian.islandcore.data.events.IslandDeactivateEvent;
import net.brian.islandcore.data.events.IslandLoadEvent;
import net.brian.islandcore.data.events.IslandUnloadEvent;
import net.brian.islandcore.data.gson.PostActivate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import world.bentobox.bentobox.BentoBox;
import world.bentobox.bentobox.api.events.island.IslandEnterEvent;
import world.bentobox.bentobox.api.events.island.IslandExitEvent;
import world.bentobox.bentobox.api.user.User;
import world.bentobox.bentobox.database.objects.Island;

import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;


@Component
public class IslandEventsListener implements Listener {

    @Inject
    IslandDataService dataService;

    @Inject
    IslandDataHandlerImpl dataHandler;

    private final BentoBox bentoBox = BentoBox.getInstance();
    private final Logger logger = Logger.getLogger("IslandData");

    private void log(String msg){
        logger.log(Level.INFO,msg);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event){

        Island islandBelong = bentoBox.getIslandsManager().getIsland(dataService.getWorld(), User.getInstance(event.getPlayer()));
        if(islandBelong != null){
            for (UUID uuid : islandBelong.getMembers().keySet()){
                if(!uuid.equals(event.getPlayer().getUniqueId())){
                    if(Bukkit.getOfflinePlayer(uuid).isOnline()){
                        return;
                    }
                }
            }
            new IslandLoadEvent(islandBelong,event.getPlayer()).callEvent();
        }


    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event){
        Island islandBelong = bentoBox.getIslandsManager().getIsland(dataService.getWorld(), User.getInstance(event.getPlayer()));
        if(islandBelong != null){
            for (UUID uuid : islandBelong.getMembers().keySet()){
                if(!uuid.equals(event.getPlayer().getUniqueId())){
                    if(Bukkit.getOfflinePlayer(uuid).isOnline()){
                        return;
                    }
                }
            }
            new IslandUnloadEvent(islandBelong,event.getPlayer()).callEvent();
        }
    }

    @EventHandler
    public void onEnter(IslandEnterEvent event){
        Island island = event.getIsland();
        Player player = Bukkit.getPlayer(event.getPlayerUUID());
        if(island.getPlayersOnIsland().size() == 0){
            new IslandActiveEvent(island,player).callEvent();
            dataHandler.getTables().forEach(table -> {
                Object data = table.getData(event.getIsland().getUniqueId());
                if(data instanceof PostActivate){
                    Bukkit.getScheduler().runTaskLater(IslandCore.getInstance(), ((PostActivate) data)::onActivate,20L);
                }
            });
        }
    }

    @EventHandler
    public void onExit(IslandExitEvent event){
        Island island = event.getIsland();
        Player player = Bukkit.getPlayer(event.getPlayerUUID());
        if(island.getPlayersOnIsland().size() == 1){
            new IslandDeactivateEvent(island,player).callEvent();
            dataHandler.getTables().forEach(table -> {
                Object data = table.getData(event.getIsland().getUniqueId());
                if(data instanceof PostActivate){
                    ((PostActivate) data).onDeactivate();
                }
            });
        }
    }

}
