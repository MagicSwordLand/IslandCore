package net.brian.islandcore.crop.objects;

import io.github.clayclaw.islandcore.IslandCore;
import me.casperge.realisticseasons.season.Season;
import net.brian.islandcore.common.objects.IslandLocation;
import net.brian.islandcore.common.objects.IslandLogger;
import net.brian.islandcore.crop.IslandCropService;
import net.brian.islandcore.crop.crops.IslandCrop;
import net.brian.islandcore.data.gson.PostActivate;
import net.brian.islandcore.data.gson.PostProcessable;
import net.brian.islandcore.data.objects.IslandData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ItemFrame;
import org.bukkit.persistence.PersistentDataType;
import world.bentobox.bentobox.BentoBox;
import world.bentobox.bentobox.database.objects.Island;

import java.util.*;

public class IslandCropProfile extends IslandData implements PostActivate,PostProcessable {


    public static IslandCore plugin = IslandCore.getInstance();

    private Long lastSaved;
    private int cropLimit = 5;

    private final List<ActiveCrop> crops;
    private final HashMap<String,Long> cropHarvestCount;
    private final Set<String> unlockedCrops;

    private final transient HashMap<ItemFrame,ActiveCrop> cropFrameMap;
    private transient Island island;

    public IslandCropProfile(String uuid) {
        super(uuid);
        crops = new ArrayList<>();
        unlockedCrops = new HashSet<>();
        cropHarvestCount  = new HashMap<>();
        cropFrameMap = new HashMap<>();
        island = BentoBox.getInstance().getIslands().getIslandById(uuid).get();
    }

    public IslandCropProfile(){
        cropFrameMap = new HashMap<>();
        cropHarvestCount  = new HashMap<>();
        crops = new ArrayList<>();
        unlockedCrops = new HashSet<>();
    }

    public boolean plant(IslandCrop crop, Location location){
        if(getNearbyCrops(location) != null){
            return false;
        }
        ActiveCrop activeCrop = new ActiveCrop(crop,new IslandLocation(location),uuid);
        ItemFrame itemFrame = activeCrop.getItemFrame();
        crops.add(activeCrop);
        cropFrameMap.put(itemFrame,activeCrop);
        return true;
    }


    public ActiveCrop getCrop(ItemFrame entity){
        return cropFrameMap.get(entity);
    }

    public void remove(ItemFrame itemFrame){
        if(itemFrame.getPersistentDataContainer().has(IslandCore.islandKey, PersistentDataType.STRING)){
            ActiveCrop activeCrop = cropFrameMap.get(itemFrame);
            if(activeCrop != null){
                cropFrameMap.remove(itemFrame);
                crops.remove(activeCrop);
            }


            itemFrame.remove();
        }
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

    public void ageAll(Season season){
        crops.forEach(activeCrop -> activeCrop.age(season));
    }

    public void increaseLimit(int amount){
        cropLimit += amount;
    }

    public void setFarmLand(int amount){
        cropLimit = amount;
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

    public boolean hasUnlocked(String type){
        return unlockedCrops.contains(type);
    }

    public void unlock(String type){
        unlockedCrops.add(type);
    }

    private static ItemFrame getNearbyCrops(Location location){
        Location loc = location.clone();
        loc.add(0.5,0,0.5);
        return loc.getNearbyEntitiesByType(ItemFrame.class,0.1).stream().filter(IslandCropService::isCrop).findFirst().orElse(null);
    }

    public long getHarvestCount(String id){
        return cropHarvestCount.getOrDefault(id,0L);
    }



    @Override
    public void onActivate() {
        refreshCrops();
    }

    @Override
    public void onDeactivate() {
    }

    @Override
    public void gsonPostDeserialize() {
        cropLimit = 5;
        island = BentoBox.getInstance().getIslandsManager().getIslandById(uuid).orElseGet(()->{
            IslandLogger.logInfo(ChatColor.RED+"Can't find this island from bentonBox!");
            return null;
        });
        int minutesPast = (int) ((System.currentTimeMillis()-lastSaved)/60000);
        crops.forEach(activeCrop -> activeCrop.flatAge(minutesPast*10));
    }

    @Override
    public void gsonPostSerialize() {
        lastSaved = System.currentTimeMillis();
    }

    public void refreshCrops(){
        cropFrameMap.clear();
        Iterator<ActiveCrop> it = crops.iterator();
        while (it.hasNext()){
            ActiveCrop activeCrop = it.next();
            if(activeCrop.getItemFrame() == null || activeCrop.getItemFrame().isDead()){
                boolean success = activeCrop.loadItemFrame();
                if(success){
                    cropFrameMap.put(activeCrop.getItemFrame(),activeCrop);
                }
                else {
                    Bukkit.getScheduler().runTaskLater(IslandCore.getInstance(),()->{
                        if(!activeCrop.loadItemFrame()) crops.remove(activeCrop);
                        else cropFrameMap.put(activeCrop.getItemFrame(),activeCrop);
                    },400);
                }
            }
            else{
                cropFrameMap.put(activeCrop.getItemFrame(),activeCrop);
                activeCrop.cropType.updateAppearance(activeCrop.getStage(),activeCrop.getItemFrame());
            }
        }
    }

    public void checkCropsLocation(){
        Iterator<Map.Entry<ItemFrame,ActiveCrop>> it = cropFrameMap.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<ItemFrame, ActiveCrop> entry = it.next();
            ActiveCrop activeCrop = entry.getValue();
            ItemFrame frame = entry.getKey();
            IslandLocation location = activeCrop.getLocation();
            if(!activeCrop.getCropType().checkLocation(location.getBukkitLoc())){
                it.remove();
                activeCrop.drop();
                crops.remove(activeCrop);
                frame.remove();
            };
        }
    }

}
