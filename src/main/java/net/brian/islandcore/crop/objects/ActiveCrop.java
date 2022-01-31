package net.brian.islandcore.crop.objects;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import io.github.clayclaw.islandcore.IslandCore;
import me.casperge.realisticseasons.season.Season;
import net.brian.islandcore.common.objects.IslandLocation;
import net.brian.islandcore.common.objects.IslandLogger;
import net.brian.islandcore.crop.crops.IslandCrop;
import net.brian.islandcore.crop.IslandCropService;
import net.brian.islandcore.data.gson.PostProcessable;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitTask;
import world.bentobox.bentobox.api.commands.island.IslandNearCommand;

import java.util.Optional;
import java.util.UUID;

public class ActiveCrop implements PostProcessable {

    public static final NamespacedKey cropTypeKey = new NamespacedKey(IslandCore.getInstance(),"crop_type");
    private static final IslandCore plugin = IslandCore.getInstance();
    public static IslandCropService islandCropManager;

    transient Hologram hologram;
    transient BukkitTask hologramRemoveTask;

    transient ItemFrame itemFrame;
    transient IslandCrop cropType;

    private String type;
    private String islandUUID;
    private IslandLocation location;
    private UUID itemFrameID;


    private Compost compost;

    int age = 0;
    int stage = 0;

    public ActiveCrop(IslandCrop cropType,IslandLocation location,String islandUUID){
        this.location = location;
        this.islandUUID = islandUUID;
        this.cropType = cropType;
        this.type = cropType.getId();
        itemFrame = cropType.instantiate(location.getBukkitLoc(),islandUUID);
        itemFrameID = itemFrame.getUniqueId();
    }

    public ActiveCrop(){
    }

    public IslandLocation getLocation(){
        return location;
    }

    public IslandCrop getCropType(){
        return cropType;
    }

    public void drop(){
        cropType.drop(this, stage,location.getBukkitLoc());
    }

    public void age(int amount){
        if(age<cropType.getGrow_time()){
            if(compost != null){
                if(compost.expired()){
                    compost = null;
                }
                else{
                    amount = (int) (amount*(1+compost.amount));
                }
            }
            if(age + amount >= getMaxAge()){
                age = getMaxAge();
            }
            else {
                age += amount;
            }
            while (age >= cropType.getNextStageRequire(stage)){
                if(stage<cropType.getMaxStage()){
                    stage++;
                    updateAppearance();
                }
            }
        }
    }

    public void flatAge(int amount){
        if(compost != null){
            amount = (int) (amount*(1+compost.getAmount()));
        }
        if(stage < getCropType().getMaxStage()){
            if(age+amount > getMaxAge()){
                age = getMaxAge();
            }
            else{
                this.age += amount;
            }
            while (age>=cropType.getNextStageRequire(stage)){
                stage ++;
            }
        }
    }

    public void age(Season seasonType){
        if(seasonType.equals(cropType.getMainSeason())){
            age(12);
        }
        else if(seasonType.equals(cropType.getWeakSeason())){
            age(8);
        }
        else {
            age(10);
        }
    }

    public void downAge(int amount){
        if(age - amount >=0){
            age -= amount;
        }
        else{
            age = 0;
        }

        while (age<cropType.getNextStageRequire(stage-1)){
            stage --;
        }
        updateAppearance();
    }

    public boolean overLaps(){
        Location loc = location.getBukkitLoc();
        Optional<ItemFrame> itemFrame = getFrameAtLocation(loc);
        return itemFrame.filter(frame -> !frame.getUniqueId().equals(itemFrameID)).isPresent();
    }

    private Optional<ItemFrame> getFrameAtLocation(Location loc){
        Location location = loc.clone();
        location.add(0.5,0,0.5);
        return  location.getNearbyEntitiesByType(ItemFrame.class,0.01)
                .stream().filter(IslandCropService::isCrop).findFirst();
    }

    public void updateAppearance(){
        if(itemFrame != null){
            cropType.updateAppearance(stage,itemFrame);
        }
        else{
            loadItemFrame();
        }
    }

    public void showInfo(){
        if(hologram == null){
            hologram = HologramsAPI.createHologram(plugin,location.getBukkitLoc().add(+0.5,2.9,+0.5));
        }
        else{
            hologramRemoveTask.cancel();
            hologram.clearLines();
        }
        hologramRemoveTask = Bukkit.getScheduler().runTaskLater(plugin,()->{
            hologram.delete();
            hologram = null;
        },60);
        hologram.appendItemLine(cropType.getDrop());
        hologram.appendTextLine(cropType.getName());
        hologram.appendTextLine(getCompostBar());
        //hologram.appendTextLine("§x§C§0§C§0§C§0§m━━━━━━━━━━━━━━━");
        if(age >= getMaxAge()){
            hologram.appendTextLine("§a§l可以採收");
        }
        else{
            hologram.appendTextLine(growthBar());
        }
        /*
        if(stage == cropType.getMaxStage()){
            hologram.appendTextLine("§7成長階段: §a成熟");
        }
        else{
            hologram.appendTextLine("§7成長階段: "+stage+"/"+cropType.getMaxStage());
        }
        hologram.appendTextLine("§x§C§0§C§0§C§0§m━━━━━━━━━━━━━━━");

         */
    }

    public int getAge() {
        return age;
    }

    public int getMaxAge(){
        return cropType.getGrow_time();
    }

    public int getStage() {
        return stage;
    }

    @Override
    public void gsonPostDeserialize() {
        cropType = islandCropManager.getCrop(type);
        itemFrame = (ItemFrame) Bukkit.getEntity(itemFrameID);
    }

    public boolean loadItemFrame(){
        if(!location.getBukkitLoc().isChunkLoaded()){
            location.getBukkitLoc().getChunk().load();
        }
        itemFrame = (ItemFrame) Bukkit.getEntity(itemFrameID);
        if(itemFrame != null && !itemFrame.isDead()){
            updateAppearance();
            return true;
        }
        return false;
    }


    @Override
    public void gsonPostSerialize() {

    }

    private String growthBar(){
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<=getMaxAge();i+=getMaxAge()/8){
            if(age>i){
                builder.append("§a§l§m━");
            }
            else{
                builder.append("§7§l§m━");
            }
        }
        return builder.toString();
    }

    private String getCompostBar(){
        if(compost != null && !compost.expired()){
            StringBuilder builder = new StringBuilder();
            long remain = compost.duration - (System.currentTimeMillis()- compost.timeStamp);
            for(long i=0;i<=compost.duration;i+=compost.duration/8){
                IslandLogger.logInfo("Remain:"+remain+" i:"+i);
                if(remain>=i-10000){
                    builder.append("§x§e§a§b§c§2§f§l§m━");
                }
                else{
                    builder.append("§7§l§m━");
                }
            }
            return builder.toString();
        }
        return "§7§l沒有肥料";
        //return "§7§l§m━━━━━━━━━";
    }

    public ItemFrame getItemFrame() {
        return itemFrame;
    }

    public IslandCropProfile getIsland() {
        return islandCropManager.getProfile(islandUUID);
    }

    public static String readType(Entity entity){
        return entity.getPersistentDataContainer().get(cropTypeKey, PersistentDataType.STRING);
    }

    /*
    public boolean addCompost(Compost compost){
        if(this.compost == null || this.compost.amount < compost.amount){
            this.compost = compost;
            return true;
        }
        return false;
    }
     */
    public void addCompost(Compost compost){
        this.compost = compost;
    }

}
