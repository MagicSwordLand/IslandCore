package net.brian.islandcore.crop.objects;

import com.google.gson.annotations.Expose;
import org.bukkit.entity.Entity;

import java.util.HashMap;

public class IslandCropProfile {

    private Long lastSaved;
    private int cropLimit = 0;

    @Expose
    HashMap<ActiveCrop,Entity> cropEntityHashMap = new HashMap<>();




    public void setLastSaved(Long currentTime){
        lastSaved = currentTime;
    }


}
