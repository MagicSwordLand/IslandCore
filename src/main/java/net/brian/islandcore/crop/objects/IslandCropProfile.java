package net.brian.islandcore.crop.objects;

import com.google.gson.annotations.Expose;
import io.github.clayclaw.islandcore.IslandCore;
import io.github.clayclaw.islandcore.season.SeasonType;
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

    public static IslandCore plugin = IslandCore.getInstance();

    private Long lastSaved;
    private int cropLimit = 10;

    private final List<ActiveCrop> crops;
    private final HashMap<String,Long> cropHarvestCount;

    private final transient HashMap<Block,ActiveCrop> cropBlockHashMap;
    private transient Island island;

    public IslandCropProfile(String uuid) {
        super(uuid);
        crops = new ArrayList<>();
        cropHarvestCount  = new HashMap<>();
        cropBlockHashMap = new HashMap<>();
        island = BentoBox.getInstance().getIslands().getIslandById(uuid).get();
    }

    public IslandCropProfile(){
        cropBlockHashMap = new HashMap<>();
        cropHarvestCount  = new HashMap<>();
        crops = new ArrayList<>();
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
        IslandLogger.logInfo("IslandCropProfile Post Deserialize ran");
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

    public void addCount(String id){
        cropHarvestCount.put(id,cropHarvestCount.getOrDefault(id,0L)+1);
    }

    public Island getIsland(){
        return island;
    }

    public boolean hasMember(UUID uuid){
        return island.getMemberSet().contains(uuid);
    }

    public void ageAll(SeasonType seasonType){
        crops.forEach(activeCrop -> activeCrop.age(seasonType));
    }

    public void increaseLimit(){
        cropLimit ++;
    }

    public int getCropLimit() {
        return cropLimit;
    }

    public List<ActiveCrop> getCrops(){
        return crops;
    }

    public boolean accededMaxCrop(){
        return crops.size()>=cropLimit;
    }

    public long getHarvestCount(String id){
        return cropHarvestCount.getOrDefault(id,0L);
    }
}
