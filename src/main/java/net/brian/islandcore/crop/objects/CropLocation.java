package net.brian.islandcore.crop.objects;

import dev.reactant.reactant.extra.net.BaseUrl;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class CropLocation {
    double x,y,z;
    String world;

    public CropLocation(Location location){
        x = location.getX();
        y = location.getY();
        z = location.getZ();
        world = location.getWorld().getName();
    }

    public Location getLocation(){
        return new Location(Bukkit.getWorld(world),x,y,z);
    }

}
