package net.brian.islandcore.data.config;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class IslandConfig {

    private String world = "bskyblock_world";

    public World getWorld(){
        return Bukkit.getWorld(world);
    }
}
