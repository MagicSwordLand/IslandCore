package net.brian.islandcore.data;

import dev.reactant.reactant.core.component.Component;
import dev.reactant.reactant.core.dependency.injection.Inject;
import dev.reactant.reactant.extra.net.BaseUrl;
import io.reactivex.rxjava3.schedulers.Schedulers;
import net.brian.islandcore.crop.events.IslandLoadEvent;
import net.brian.islandcore.crop.events.IslandUnloadEvent;
import net.brian.islandcore.data.events.IslandDataLoadCompleteEvent;
import net.brian.islandcore.data.events.IslandDataPrepareSaveEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;


@Component
public class IslandDataService implements Listener {

    @Inject IslandDataHandlerImpl dataHandler;


    @EventHandler
    public void onLoad(IslandLoadEvent event){
        String uuid = event.getIsland().getUniqueId();
        CompletableFuture.runAsync(()->{
            dataHandler.getTables().forEach(table ->
                    table.setData(uuid,dataHandler.getData(table.getId(),uuid,table.getDataClass())));
        }).thenRun(()->{
            Bukkit.getPluginManager().callEvent(new IslandDataLoadCompleteEvent(event.getIsland()));
        });
    }


    @EventHandler
    public void onUnload(IslandUnloadEvent event){
        Bukkit.getPluginManager().callEvent(new IslandDataPrepareSaveEvent(event.getIsland()));

        String uuid = event.getIsland().getUniqueId();
        CompletableFuture.runAsync(()->{
           dataHandler.getTables().forEach(table -> {
               dataHandler.saveData(table.getId(),uuid,table.getData(uuid));
           });
        });

    }


    public <T> T getData(String uuid, Class<T> dataClass){
        return dataHandler.getTable(dataClass).getData(uuid);
    }

    public void register(String id,Class<?> dataClass){
        dataHandler.register(id,dataClass);
    }
}
