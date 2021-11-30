package net.brian.islandcore.crop.crops;

import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import io.github.clayclaw.islandcore.season.SeasonType;
import net.Indyuce.mmoitems.MMOItems;
import net.brian.customblocks.blocks.CustomBlock;
import net.brian.islandcore.IslandCropsAndLiveStocks;
import net.brian.islandcore.common.objects.IslandLocation;
import net.brian.islandcore.crop.objects.ActiveCrop;
import net.brian.islandcore.crop.objects.CropDrop;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;

public class Tomato extends RightClickCrop{


    final CustomBlock stage1,stage2,stage3,stage4,stage5;


    public Tomato(){
        seed = new CropDrop("SEED","TOMATO_SEED");
        drop = new CropDrop("CROP","TOMATO");
        this.id = "tomato";
        this.grow_time = 150;
        this.name = "§c番茄";
        stageMap.put(0,10);
        stageMap.put(1,25);
        stageMap.put(2,40);
        stageMap.put(3,50);
        stageMap.put(4,150);
        max_stage = 5;
        weakSeason = SeasonType.WINTER;
        mainSeason = SeasonType.SUMMER;
        stage1 = new CustomBlock(Material.BLUE_DYE,3);
        stage2 = new CustomBlock(Material.BLUE_DYE,4);
        stage3 = new CustomBlock(Material.BLUE_DYE,5);
        stage4 = new CustomBlock(Material.BLUE_DYE,6);
        stage5 = new CustomBlock(Material.BLUE_DYE,7);
    }


    @Override
    public Block instantiate(IslandLocation location, long age) {
        Block block  = location.getLocation().getBlock();
        stage1.place(block);
        return block;
    }



    @Override
    public void updateAppearance(int stage, Block block) {
        switch (stage){
            case 1: {
                stage1.place(block);
                break;
            }
            case 2: {
                stage2.place(block);
                break;
            }
            case 3: {
                stage3.place(block);
                break;
            }
            case 4: {
                stage4.place(block);
                break;
            }
            case 5: {
                stage5.place(block);
                break;
            }
        }
    }


    @Override
    public void onRightClick(ActiveCrop activeCrop) {
        rightClickDrop(activeCrop,drop,99);
    }

}
