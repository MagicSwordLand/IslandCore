package net.brian.islandcore.crop.objects;

import com.google.gson.annotations.Expose;
import kotlin.jvm.Transient;
import net.brian.islandcore.data.gson.PostProcessable;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

import java.util.HashMap;
import java.util.List;

public class IslandCropProfile implements PostProcessable {

    private Long lastSaved;
    private int cropLimit = 0;
    List<ActiveCrop> crops;


    private transient HashMap<ActiveCrop, Block> cropEntityHashMap = new HashMap<>();



    public void setLastSaved(Long currentTime){
        lastSaved = currentTime;
    }


    @Override
    public void gsonPostDeserialize() {
        int minutesPast = (int) ((System.currentTimeMillis()-lastSaved)/60000);
        crops.forEach(activeCrop -> {
            activeCrop.age(minutesPast);
        });
    }

    @Override
    public void gsonPostSerialize() {

    }
}
