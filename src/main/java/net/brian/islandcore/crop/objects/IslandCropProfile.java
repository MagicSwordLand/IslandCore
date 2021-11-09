package net.brian.islandcore.crop.objects;

import com.google.gson.annotations.Expose;
import kotlin.jvm.Transient;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

import java.util.HashMap;
import java.util.List;

public class IslandCropProfile {

    private Long lastSaved;
    private int cropLimit = 0;
    List<ActiveCrop> crops;


    private transient HashMap<ActiveCrop, Block> cropEntityHashMap = new HashMap<>();



    public void setLastSaved(Long currentTime){
        lastSaved = currentTime;
    }

    public void setup(){
        crops.forEach(ActiveCrop::instantiate);
    }

}
