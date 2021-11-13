package net.brian.islandcore.livestock.listener;

import dev.reactant.reactant.core.component.Component;
import dev.reactant.reactant.core.dependency.injection.Inject;
import net.brian.islandcore.livestock.LiveStockService;
import net.brian.islandcore.livestock.objects.IslandLiveStockProfile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;


@Component
public class TestListener implements Listener {

    @Inject
    LiveStockService liveStockService;

    @EventHandler
    public void onShift(PlayerToggleSneakEvent event){
        event.getPlayer().sendMessage("TestListener run");
        IslandLiveStockProfile profile = liveStockService.getData(event.getPlayer(),event.getPlayer().getWorld());
        profile.spawn(liveStockService.getLiveStock("GoldenCow"),event.getPlayer().getLocation());
    }

    @EventHandler
    public void rightClick(PlayerInteractEntityEvent event){
        event.getPlayer().sendMessage(event.getRightClicked().getUniqueId()+" : uuid");
    }

}
