package net.brian.islandcore.season.config;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class SeasonSettings {
    String world = "bskyblock_world";
    public World getWorld(){
        return Bukkit.getWorld(world);
    }
}
