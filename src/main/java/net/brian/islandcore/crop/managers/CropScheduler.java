package net.brian.islandcore.crop.managers;

import dev.reactant.reactant.extra.net.BaseUrl;
import net.brian.islandcore.IslandCore;
import org.bukkit.Bukkit;

public class CropScheduler {
    private final IslandCropManager cropManager;

    public CropScheduler(IslandCropManager manager){
        cropManager = manager;
    }

    public void run(){
        Bukkit.getScheduler().runTaskTimer(IslandCore.getInstance(),()->{

        },20*60,0);
    }
}
