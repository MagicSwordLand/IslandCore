package net.brian.islandcore.crop.objects;

import com.google.gson.annotations.Expose;
import kotlin.jvm.Transient;
import net.brian.islandcore.data.gson.PostProcessable;
import net.brian.islandcore.data.objects.IslandData;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

import java.util.HashMap;
import java.util.List;

public class IslandCropProfile extends IslandData implements PostProcessable {

    private Long lastSaved;
    private int cropLimit = 0;
    List<ActiveCrop> crops;


    private transient HashMap<ActiveCrop, Block> cropEntityHashMap = new HashMap<>();

    public IslandCropProfile(String uuid) {
        super(uuid);
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
        lastSaved = System.currentTimeMillis();
    }
}
