package net.brian.islandcore.crop.objects;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class ActiveCrop {

    CropLocation cropLocation;
    String type;


    public ActiveCrop(Location loc, String type){
        cropLocation = new CropLocation(loc);
        this.type = type;

    }

    public CropLocation getLocation(){
        return cropLocation;
    }

    public void instantiate(){

    }

}
