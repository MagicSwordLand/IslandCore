package net.brian.islandcore.common.objects;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class IslandLocation {
    double x,y,z;
    String world;

    public IslandLocation(Location location){
        x = location.getX();
        y = location.getY();
        z = location.getZ();
        world = location.getWorld().getName();
    }

    public static IslandLocation getLocation(Location location){
        return new IslandLocation(location);
    }

    public Location getLocation(){
        return new Location(Bukkit.getWorld(world),x,y,z);
    }

}
