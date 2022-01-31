package net.brian.islandcore.crop.listener;

import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import dev.reactant.reactant.core.component.Component;
import dev.reactant.reactant.core.dependency.injection.Inject;
import dev.reactant.reactant.extra.config.type.SharedConfig;
import net.brian.islandcore.crop.IslandCropService;
import net.brian.islandcore.crop.config.MessageConfig;
import net.brian.islandcore.crop.crops.IslandCrop;
import net.brian.islandcore.crop.crops.Tomato;
import net.brian.islandcore.crop.objects.ActiveCrop;
import net.brian.islandcore.crop.objects.IslandCropProfile;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.Opt;
import world.bentobox.bentobox.BentoBox;
import world.bentobox.bentobox.api.user.User;
import world.bentobox.bentobox.database.objects.Island;

import java.util.Locale;
import java.util.Optional;

@Component
public class CropPlaceListener implements Listener {

    private final BentoBox bentoBox = BentoBox.getInstance();

    @Inject(name = "plugins/IslandFarming/Message.json")
    SharedConfig<MessageConfig> config;

    @Inject
    IslandCropService cropService;

    @EventHandler(ignoreCancelled = true,priority = EventPriority.HIGHEST)
    public void onRightClick(PlayerInteractEvent event){
        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            if(event.getHand().equals(EquipmentSlot.OFF_HAND)) return;
            Material blockMat = event.getClickedBlock().getType();
            if(!event.getBlockFace().equals(BlockFace.UP)) return;
            if(blockMat.equals(Material.GRASS_BLOCK) || blockMat.equals(Material.DIRT) || blockMat.equals(Material.FARMLAND)) {
                ItemStack seed = event.getItem();
                String seedType = IslandCropService.readSeed(seed);
                Block block = event.getClickedBlock();
                Location loc = block.getLocation();
                loc.add(0,1,0);
                if(seedType.equals("")) return;
                if(cropService.getCropAtLocation(loc) != null) {
                    event.getPlayer().sendMessage(ChatColor.RED+"該位置已經有作物，若你相信這是錯誤，請回報");
                    return;
                }
                Optional<Island> optIsland = bentoBox.getIslandsManager().getIslandAt(block.getLocation());
                if(optIsland.isPresent()){
                    IslandCropProfile cropProfile = cropService.getProfile(optIsland.get().getUniqueId());
                    if(cropProfile == null) return;
                    IslandCrop cropType = cropService.getCrop(seedType);
                    if(!event.getPlayer().hasPermission("islandcrop.bypass")){
                        if(!cropProfile.hasUnlocked(seedType)){
                            event.getPlayer().sendMessage(config.getContent().getNotUnlocked(cropType.getName()));
                            return;
                        }
                    }
                    if(cropProfile.accededMaxCrop()) {
                        event.getPlayer().sendMessage(config.getContent().getExceedMax(cropProfile.getCropLimit()));
                        return;
                    }
                    if(cropType != null){
                        boolean success = cropProfile.plant(cropService.getCrop(seedType),loc);
                        if(success){
                            seed.setAmount(seed.getAmount()-1);
                            event.getPlayer().getInventory().setItem(event.getHand(),seed);
                            loc.getWorld().playSound(loc, Sound.BLOCK_GRASS_BREAK,1,1);
                        }
                        else{
                            event.getPlayer().sendMessage(config.getContent().getAlreadyHasCrop());
                        }
                    }
                    else{
                        event.getPlayer().sendMessage(ChatColor.RED +"找不到該種子的作物 請洽管理員");
                    }
                }
            }
        }
    }




}
