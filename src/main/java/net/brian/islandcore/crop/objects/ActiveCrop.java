package net.brian.islandcore.crop.objects;

import com.gmail.filoghost.holographicdisplays.HolographicDisplays;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import net.brian.islandcore.IslandCropsAndLiveStocks;
import net.brian.islandcore.common.objects.IslandLocation;
import net.brian.islandcore.common.persistent.BlockMeta;
import net.brian.islandcore.crop.crops.IslandCrop;
import net.brian.islandcore.crop.IslandCropService;
import net.brian.islandcore.data.gson.PostProcessable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

public class ActiveCrop implements PostProcessable {

    private static final IslandCropsAndLiveStocks plugin = IslandCropsAndLiveStocks.getInstance();
    public static IslandCropService islandCropManager;

    transient Block block;
    transient IslandCrop cropType;
    transient Hologram hologram;
    transient BukkitTask hologramRemoveTask;

    private String type;
    private String islandUUID;
    private IslandLocation location;
    int age = 0;
    int stage = 0;

    public ActiveCrop(IslandCrop cropType,IslandLocation location,String uuid){
        this.location = location;
        this.cropType = cropType;
        this.type = cropType.getId();
        this.islandUUID = uuid;
        block = cropType.instantiate(location,0);
        BlockMeta.setData(block,"island_uuid",uuid);
        BlockMeta.setData(block,"crop_type",cropType);
    }

    public IslandLocation getLocation(){
        return location;
    }

    public Block getBlock() {
        return block;
    }

    public IslandCrop getCropType(){
        return cropType;
    }

    public void drop(){
        cropType.drop(stage,location.getLocation());
    }

    public void age(int amount){
        if(age<cropType.getGrow_time()){
            this.age+=amount;
            if(age >= cropType.getNextStageRequire(stage)){
                if(stage<cropType.getMaxStage()){
                    stage++;
                    updateAppearance();
                }
            }
        }
    }

    void updateAppearance(){
        cropType.updateAppearance(stage,block);
    }

    public void showInfo(){
        if(hologram == null){
            hologram = HologramsAPI.createHologram(plugin,location.getLocation().add(+0.5,2.5,+0.5));
        }
        else{
            hologramRemoveTask.cancel();
            hologram.clearLines();
        }
        hologramRemoveTask = Bukkit.getScheduler().runTaskLater(plugin,()->{
            hologram.delete();
            hologram = null;
        },40);
        hologram.appendItemLine(new ItemStack(Material.WHEAT_SEEDS));
        hologram.appendTextLine(cropType.getName());
        hologram.appendTextLine("§7===============");
        hologram.appendTextLine("§7成長度: "+age+"/"+cropType.getGrow_time());
        if(stage == cropType.getMaxStage()){
            hologram.appendTextLine("§7成長階段: §a成熟");
        }
        else{
            hologram.appendTextLine("§7成長階段: "+stage+"/"+cropType.getMaxStage());
        }
        hologram.appendTextLine("§7===============");
    }

    public int getAge() {
        return age;
    }

    public int getMaxAge(){
        return cropType.getGrow_time();
    }

    @Override
    public void gsonPostDeserialize() {
        block = location.getLocation().getBlock();
        BlockMeta.setData(block,"island_uuid",islandUUID);
        cropType = islandCropManager.getCrop(type);
        BlockMeta.setData(block,"crop_type",cropType.getId());
    }

    @Override
    public void gsonPostSerialize() {

    }

}
