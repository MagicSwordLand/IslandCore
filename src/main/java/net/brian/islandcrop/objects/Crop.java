package net.brian.islandcrop.objects;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Crop {
    double x,y,z;
    String world;

    public Crop(Location loc,String type){
        world = loc.getWorld().getName();
        x = loc.getX();
        y = loc.getY();
        z = loc.getZ();
    }

    public Location getLocation(){
        return new Location(Bukkit.getWorld(world),x,y,z);
    }

}
