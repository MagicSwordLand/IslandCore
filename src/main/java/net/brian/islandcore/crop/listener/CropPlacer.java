package net.brian.islandcore.crop.listener;

import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import dev.reactant.reactant.core.component.Component;
import dev.reactant.reactant.core.dependency.injection.Inject;
import net.brian.islandcore.common.objects.IslandLogger;
import net.brian.islandcore.common.persistent.BlockMeta;
import net.brian.islandcore.crop.IslandCropService;
import net.brian.islandcore.crop.crops.IslandCrop;
import net.brian.islandcore.crop.crops.Tomato;
import net.brian.islandcore.crop.objects.ActiveCrop;
import net.brian.islandcore.crop.objects.IslandCropProfile;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.Opt;
import world.bentobox.bentobox.BentoBox;
import world.bentobox.bentobox.api.user.User;
import world.bentobox.bentobox.database.objects.Island;

import java.util.Optional;

@Component
public class CropPlacer implements Listener {

    private final BentoBox bentoBox = BentoBox.getInstance();

    @Inject
    IslandCropService cropService;

    @EventHandler
    public void onRightClick(PlayerInteractEvent event){
        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            if(event.getClickedBlock().getType().equals(Material.FARMLAND)) {
                ItemStack seed = event.getItem();
                String seedType = IslandCropService.readSeed(seed);

                Block block = event.getClickedBlock();
                Optional<Island> optIsland = bentoBox.getIslandsManager().getIslandAt(block.getLocation());

                if(seedType.equals("")) return;
                if(optIsland.isPresent()){
                    IslandCropProfile cropProfile = cropService.getProfile(optIsland.get().getUniqueId());
                    if(cropProfile == null) return;
                    if(cropProfile.accededMaxCrop()) {
                        event.getPlayer().sendMessage("島嶼上作物已達上限 "+cropProfile.getCrops().size()+"/"+cropProfile.getCropLimit());
                        return;
                    }
                    IslandCrop cropType = cropService.getCrop(seedType);
                    if(cropType != null){
                        boolean success = cropProfile.plant(cropService.getCrop(seedType),event.getClickedBlock().getLocation().add(0,1,0));
                        if(success){
                            seed.setAmount(seed.getAmount()-1);
                            event.getPlayer().getInventory().setItem(event.getHand(),seed);
                        }
                        else{
                            event.getPlayer().sendMessage("該位置已有作物");
                        }
                    }
                    else{
                        event.getPlayer().sendMessage(ChatColor.RED +"找不到該種子的作物 請洽管理員");
                    }
                }
            }
            else if(event.getPlayer().isSneaking()){
                ActiveCrop activeCrop = cropService.getActiveCrop(event.getClickedBlock());
                if(activeCrop != null){
                    activeCrop.showInfo();
                }
            }
        }
    }




}
