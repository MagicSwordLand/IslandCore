package net.brian.islandcore.crop.crops;

import net.brian.islandcore.IslandCore;
import net.brian.islandcore.crop.objects.CropLocation;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import javax.xml.stream.events.Namespace;

public abstract class IslandCrop implements Listener {

    private String id;

    protected int grow_time;
    protected String material;
    protected HarvestType harvestType;



    public String getId() {
        return id;
    }

    public abstract void instantiate(CropLocation location, long age);


    public NamespacedKey getKey(){
        return new NamespacedKey(IslandCore.getInstance(),id);
    }
}
