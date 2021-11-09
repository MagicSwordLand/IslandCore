package net.brian.islandcore.crop.crops;

import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import net.brian.islandcore.crop.objects.CropLocation;
import org.bukkit.event.Event;

public class Tomato extends IslandCrop{

    public Tomato(){
        this.grow_time = 1;


    }

    @Override
    public void instantiate(CropLocation location, long age) {

    }

    protected void harvestEvent(BlockDestroyEvent event) {

    }
}
