package net.brian.islandcore.data;

import dev.reactant.reactant.core.component.Component;
import dev.reactant.reactant.core.component.lifecycle.LifeCycleHook;
import dev.reactant.reactant.core.dependency.injection.Inject;
import dev.reactant.reactant.extra.config.type.SharedConfig;
import io.github.clayclaw.islandcore.IslandCore;
import net.brian.islandcore.IslandCropsAndLiveStocks;
import net.brian.islandcore.data.events.*;
import net.brian.islandcore.data.config.IslandConfig;
import net.brian.islandcore.data.gson.PostActivate;
import net.brian.islandcore.data.gson.PostQuitProcessable;
import net.brian.islandcore.data.objects.Table;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import world.bentobox.bentobox.BentoBox;
import world.bentobox.bentobox.api.user.User;
import world.bentobox.bentobox.database.objects.Island;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;


@Component
public class IslandDataService implements Listener, LifeCycleHook {

    private static boolean disabling = false;

    @Inject IslandDataHandlerImpl dataHandler;

    List<Island> loadedIsland = new ArrayList<>();

    @Inject(name = "plugins/IslandFarming/config.json")
    SharedConfig<IslandConfig> sharedConfig;

    @Override
    public void onDisable(){
        disabling = true;
        dataHandler.getTables().forEach(table -> {
            table.getDataEntries().forEach(entry->{
                String id = entry.getKey();
                Object data = entry.getValue();
                if(data instanceof PostActivate){
                    ((PostActivate) data).onDeactivate();
                }
                dataHandler.saveData(table.getId(),id,data);
                if(data instanceof PostQuitProcessable){
                    ((PostQuitProcessable) data).onQuit();
                }
            });
        });
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onLoad(IslandLoadEvent event){
        loadedIsland.add(event.getIsland());
        String uuid = event.getIsland().getUniqueId();

        AtomicBoolean shouldActivate = new AtomicBoolean(false);
        Optional<Island> island = BentoBox.getInstance().getIslands().getIslandAt(event.getTriggered().getLocation());
        island.ifPresent(value -> {
            if(value.getPlayersOnIsland().size() == 1){
                shouldActivate.set(true);
            }
        });

        CompletableFuture.runAsync(()->{
            dataHandler.getTables().forEach(table ->{
                Object object = dataHandler.getData(table.getId(),uuid,table.getDataClass());
                Bukkit.getScheduler().runTask(IslandCore.getInstance(),()->{
                    if(object == null){
                        event.getTriggered().kick(net.kyori.adventure.text.Component.text("你的島嶼載入資料出錯 請再次嘗試登入 或聯絡管理員"));
                    }
                    else{
                        if(shouldActivate.get()){
                            if(object instanceof PostActivate){
                                ((PostActivate) object).onActivate();
                            }
                        }
                        table.setData(uuid,object);
                    }
                });
            });
        }).thenRunAsync(()->{
            if(shouldActivate.get()){
                new IslandActiveEvent(island.get(),event.getTriggered()).callEvent();
            }
            Bukkit.getPluginManager().callEvent(new IslandDataLoadCompleteEvent(event.getIsland()));
        },IslandCropsAndLiveStocks.getExecutor());
    }



    @EventHandler(priority = EventPriority.NORMAL)
    public void onUnload(IslandUnloadEvent event){
        loadedIsland.remove(event.getIsland());
        IslandDataPrepareSaveEvent prepareSaveEvent = new IslandDataPrepareSaveEvent(event.getIsland());
        prepareSaveEvent.callEvent();
        String uuid = event.getIsland().getUniqueId();
        dataHandler.getTables().forEach(table -> {
            Object data = table.getData(uuid);
            if(data != null){
                Optional<Island> optionalIsland = BentoBox.getInstance().getIslands().getIslandAt(event.getTriggered().getLocation());
                optionalIsland.ifPresent(island -> {
                    if(island.getPlayersOnIsland().size() == 1){
                        if(data instanceof PostActivate){
                            ((PostActivate) data).onDeactivate();
                        }
                        new IslandDeactivateEvent(island,event.getTriggered()).callEvent();
                    }
                });
                CompletableFuture.runAsync(()->{
                    dataHandler.saveData(table.getId(),uuid, data);
                }).thenRunAsync(()->{
                    if(data instanceof PostQuitProcessable){
                        ((PostQuitProcessable) data).onQuit();
                    }
                },IslandCore.getExecutor());
            }
        });
    }





    public <T> T getData(String uuid, Class<T> dataClass){
        return dataHandler.getTable(dataClass).getData(uuid);
    }

    public void register(String id,Class<?> dataClass){
        dataHandler.register(id,dataClass);
    }

    public boolean isLoaded(Island island){
        return loadedIsland.contains(island);
    }

    public World getWorld(){
        return sharedConfig.getContent().getWorld();
    }

    public String getIslandUUIDFromPlayer(OfflinePlayer player){
        Island island = BentoBox.getInstance().getIslandsManager().getIsland(getWorld(), User.getInstance(player));
        if(island == null){
            return null;
        }
        return island.getUniqueId();
    }


    public static boolean isDisabling() {
        return disabling;
    }
}
