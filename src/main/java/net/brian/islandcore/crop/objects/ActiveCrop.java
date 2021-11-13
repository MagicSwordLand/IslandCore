package net.brian.islandcore.crop.objects;

import net.brian.islandcore.common.objects.IslandLocation;
import net.brian.islandcore.crop.crops.IslandCrop;
import net.brian.islandcore.crop.managers.IslandCropManager;
import net.brian.islandcore.data.gson.PostProcessable;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockCanBuildEvent;

public class ActiveCrop implements PostProcessable {

    public static IslandCropManager islandCropManager;

    transient Block block;
    transient IslandCrop cropType;
    String type;

    IslandLocation location;
    int age;
    int water;


    public ActiveCrop(Location loc, IslandCrop cropType){
        location = new IslandLocation(loc);
        this.cropType = cropType;
        this.type = cropType.getId();

    }

    public IslandLocation getLocation(){
        return location;
    }

    public IslandCrop getCropType(){
        if(cropType == null){
            return islandCropManager.getCrop(type);
        }
        else return cropType;
    }

    public void age(int amount){
        this.age+=amount;
    }


    @Override
    public void gsonPostDeserialize() {
        cropType.instantiate(location,age);
        block = location.getLocation().getBlock();
    }

    @Override
    public void gsonPostSerialize() {
    }


}
