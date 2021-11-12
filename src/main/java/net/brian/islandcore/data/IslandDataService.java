package net.brian.islandcore.data;

import dev.reactant.reactant.core.component.Component;
import dev.reactant.reactant.core.dependency.injection.Inject;
import net.brian.islandcore.IslandCropsAndLiveStocks;
import net.brian.islandcore.crop.events.IslandLoadEvent;
import net.brian.islandcore.crop.events.IslandUnloadEvent;
import net.brian.islandcore.data.events.IslandDataLoadCompleteEvent;
import net.brian.islandcore.data.events.IslandDataPrepareSaveEvent;
import net.brian.islandcore.data.gson.PostQuitProcessable;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.hamcrest.core.Is;
import world.bentobox.bentobox.database.objects.Island;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@Component
public class IslandDataService implements Listener {

    @Inject IslandDataHandlerImpl dataHandler;

    List<Island> loadedIsland = new ArrayList<>();


    @EventHandler(priority = EventPriority.NORMAL)
    public void onLoad(IslandLoadEvent event){
        loadedIsland.add(event.getIsland());
        String uuid = event.getIsland().getUniqueId();
        CompletableFuture.runAsync(()->{
            dataHandler.getTables().forEach(table ->{
                    Object object = dataHandler.getData(table.getId(),uuid,table.getDataClass());
                    if(object == null) {
                        Bukkit.getScheduler().runTask(IslandCropsAndLiveStocks.getInstance(),()->event.getTriggered().kick(net.kyori.adventure.text.Component.text("你的島嶼載入資料出錯 請再次嘗試登入 或聯絡管理員")));
                        return;
                    }
                    table.setData(uuid,object);
            });
        }).thenRun(()->{
            if(isLoaded(event.getIsland())){
                Bukkit.getPluginManager().callEvent(new IslandDataLoadCompleteEvent(event.getIsland()));
            }
        });
    }


    @EventHandler(priority = EventPriority.NORMAL)
    public void onUnload(IslandUnloadEvent event){
        loadedIsland.remove(event.getIsland());
        Bukkit.getPluginManager().callEvent(new IslandDataPrepareSaveEvent(event.getIsland()));
        String uuid = event.getIsland().getUniqueId();
        CompletableFuture.runAsync(()->{
           dataHandler.getTables().forEach(table -> {
               Object data = table.getData(uuid);
               dataHandler.saveData(table.getId(),uuid, data);
               if(data instanceof PostQuitProcessable) {
                   Bukkit.getScheduler().runTask(IslandCropsAndLiveStocks.getInstance(), ((PostQuitProcessable) data)::onQuit);
               }
           });
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
}
