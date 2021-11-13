package net.brian.islandcore.crop.crops;

import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import net.brian.islandcore.common.objects.IslandLocation;
import org.bukkit.block.Block;

public class Tomato extends IslandCrop{

    public Tomato(){
        this.grow_time = 1;


    }

    @Override
    public Block instantiate(IslandLocation location, long age) {

    }

    protected void harvestEvent(BlockDestroyEvent event) {

    }
}
