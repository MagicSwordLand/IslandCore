package net.brian.islandcore.crop.objects;

import com.google.gson.annotations.Expose;
import kotlin.jvm.Transient;
import net.brian.islandcore.IslandCropsAndLiveStocks;
import net.brian.islandcore.common.objects.IslandLocation;
import net.brian.islandcore.common.objects.IslandLogger;
import net.brian.islandcore.crop.crops.IslandCrop;
import net.brian.islandcore.data.gson.PostProcessable;
import net.brian.islandcore.data.objects.IslandData;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.metadata.LazyMetadataValue;
import org.checkerframework.checker.units.qual.A;
import world.bentobox.bentobox.BentoBox;
import world.bentobox.bentobox.database.objects.Island;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class IslandCropProfile extends IslandData implements PostProcessable {

    public static IslandCropsAndLiveStocks plugin = IslandCropsAndLiveStocks.getInstance();

    private Long lastSaved;
    private int cropLimit = 0;

    List<ActiveCrop> crops = new ArrayList<>();

    private final transient HashMap<Block,ActiveCrop> cropBlockHashMap;
    private transient Island island;

    public IslandCropProfile(String uuid) {
        super(uuid);
        cropBlockHashMap = new HashMap<>();
        island = BentoBox.getInstance().getIslands().getIslandById(uuid).get();
    }

    public IslandCropProfile(){
        cropBlockHashMap = new HashMap<>();
    }

    public boolean plant(IslandCrop crop, Location location){
        if(cropBlockHashMap.containsKey(location.getBlock())){
            return false;
        }
        ActiveCrop activeCrop = new ActiveCrop(crop,new IslandLocation(location),uuid);
        crops.add(activeCrop);
        Block cropBlock = activeCrop.getBlock();
        cropBlockHashMap.put(cropBlock,activeCrop);
        return true;
    }

    @Override
    public void gsonPostDeserialize() {
        island = BentoBox.getInstance().getIslandsManager().getIslandById(uuid).orElseGet(()->{
            IslandLogger.logInfo(ChatColor.RED+"Can't find this island from bentonBox!");
            return null;
        });
        int minutesPast = (int) ((System.currentTimeMillis()-lastSaved)/60000);
        crops.forEach(activeCrop -> {
            cropBlockHashMap.put(activeCrop.getBlock(),activeCrop);
            activeCrop.age(minutesPast);
        });
    }

    @Override
    public void gsonPostSerialize() {
        lastSaved = System.currentTimeMillis();
    }

    public ActiveCrop getCrop(Block block){
        return cropBlockHashMap.get(block);
    }

    public void remove(Block block){
        ActiveCrop activeCrop = cropBlockHashMap.get(block);
        crops.remove(activeCrop);
        cropBlockHashMap.remove(block);
    }

    public Island getIsland(){
        return island;
    }

    public boolean hasMember(UUID uuid){
        return island.getMemberSet().contains(uuid);
    }

    public void ageAll(int amount){
        crops.forEach(activeCrop -> activeCrop.age(amount));
    }

    public boolean accededMaxCrop(){
        return false;
    }
}
