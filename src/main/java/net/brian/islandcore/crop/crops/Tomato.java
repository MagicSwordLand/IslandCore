package net.brian.islandcore.crop.crops;

import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import io.github.clayclaw.islandcore.season.SeasonType;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.item.build.MMOItemBuilder;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import net.brian.islandcore.IslandCropsAndLiveStocks;
import net.brian.islandcore.common.objects.IslandLocation;
import net.brian.islandcore.common.persistent.BlockMeta;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.LazyMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class Tomato extends IslandCrop{

    public Tomato(){
        this.id = "tomato";
        this.grow_time = 100;
        this.material = Material.WHEAT;
        this.name = "§c番茄";
        stageMap.put(0,50);
        stageMap.put(1,100);
        weakSeason = SeasonType.WINTER;
        mainSeason = SeasonType.SUMMER;
    }


    @Override
    public Block instantiate(IslandLocation location, long age) {
        Block block  = location.getLocation().getBlock();
        block.setType(material);
        return block;
    }

    @Override
    public void drop(int stage, Location location) {
        if(stage == 1){
            location.getWorld().dropItem(location,MMOItems.plugin.getItem("SEED","TOMATO_SEED"));
        }
        if(stage == 2){
            location.getWorld().dropItem(location,new ItemStack(Material.DIAMOND));
        }
    }

    @Override
    public int getMaxStage() {
        return 2;
    }


    @Override
    public void updateAppearance(int stage, Block block) {
        Ageable ageable = (Ageable) block.getBlockData();
        switch (stage){
            case 1: {
                ageable.setAge(2);
                block.setBlockData(ageable);
                break;
            }
            case 2: {
                ageable.setAge(ageable.getMaximumAge());
                block.setBlockData(ageable);
                break;
            }
        }
    }

}
