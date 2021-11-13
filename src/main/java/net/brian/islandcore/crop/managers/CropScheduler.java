package net.brian.islandcore.crop.managers;

import net.brian.islandcore.IslandCropsAndLiveStocks;
import org.bukkit.Bukkit;

public class CropScheduler {
    private final IslandCropManager cropManager;

    public CropScheduler(IslandCropManager manager){
        cropManager = manager;
    }

    public void run(){
        Bukkit.getScheduler().runTaskTimer(IslandCropsAndLiveStocks.getInstance(),()->{

        },20*60,0);
    }
}
