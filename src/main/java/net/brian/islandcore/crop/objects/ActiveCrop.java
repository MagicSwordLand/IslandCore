package net.brian.islandcore.crop.objects;

import net.brian.islandcore.crop.crops.IslandCrop;
import net.brian.islandcore.crop.managers.IslandCropManager;
import net.brian.islandcore.data.gson.PostProcessable;
import org.bukkit.Location;

public class ActiveCrop implements PostProcessable {

    public static IslandCropManager islandCropManager;

    transient IslandCrop cropType;
    String type;

    CropLocation cropLocation;
    int age;
    int water;


    public ActiveCrop(Location loc, IslandCrop cropType){
        cropLocation = new CropLocation(loc);
        this.cropType = cropType;
        this.type = cropType.getId();

    }

    public CropLocation getLocation(){
        return cropLocation;
    }

    public IslandCrop getCropType(){
        if(cropType == null){
            return islandCropManager.getCrop(type);
        }
        else return cropType;
    }

    public void age(int amount){
        amount+=amount;
    }


    @Override
    public void gsonPostDeserialize() {
        cropType.instantiate(cropLocation,age);
    }

    @Override
    public void gsonPostSerialize() {

    }
}
