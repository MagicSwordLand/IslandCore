package net.brian.islandcore.crop.listener;

import dev.reactant.reactant.core.component.Component;
import dev.reactant.reactant.core.dependency.injection.Inject;
import dev.reactant.reactant.extra.config.type.SharedConfig;
import io.lumine.mythic.lib.api.item.NBTItem;
import kotlin.Pair;
import net.brian.islandcore.common.objects.IslandLocation;
import net.brian.islandcore.common.objects.IslandLogger;
import net.brian.islandcore.crop.IslandCropService;
import net.brian.islandcore.crop.config.MessageConfig;
import net.brian.islandcore.crop.crops.IslandCrop;
import net.brian.islandcore.crop.crops.RightClickCrop;
import net.brian.islandcore.crop.objects.ActiveCrop;
import net.brian.islandcore.crop.objects.Compost;
import net.brian.islandcore.crop.objects.IslandCropProfile;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;


@Component
public class CropInteractListener implements Listener {

    @Inject
    IslandCropService cropService;

    @Inject(name = "plugins/IslandFarming/Message.json")
    SharedConfig<MessageConfig> config;

    HashMap<Player,Long> coolDownMap = new HashMap<>();

    @EventHandler(ignoreCancelled = true,priority = EventPriority.HIGH)
    public void onInteract(PlayerInteractEntityEvent event){
        if (!IslandCropService.isCrop(event.getRightClicked())){
            return;
        }
        event.setCancelled(true);
        IslandCropProfile cropProfile = cropService.getProfileFromCrop(event.getRightClicked());
        if(cropProfile == null) {
            event.getPlayer().sendMessage(config.getContent().getDataLoading());
            return;
        }
        ActiveCrop activeCrop = cropProfile.getCrop((ItemFrame) event.getRightClicked());
        if(activeCrop == null){
            cropProfile.refreshCrops();
            activeCrop = cropProfile.getCrop((ItemFrame) event.getRightClicked());
            if(activeCrop == null) {
                event.getRightClicked().remove();
                event.getPlayer().sendMessage(config.getContent().getDataError(ActiveCrop.readType(event.getRightClicked())));
            }
            return;
        }
        if(event.getPlayer().isSneaking()){
            activeCrop.showInfo();
        }
        else{
            ItemStack itemStack = event.getPlayer().getEquipment().getItem(event.getHand());
            Compost compost = readCompost(itemStack);
            if(compost != null){
                activeCrop.addCompost(compost);
                itemStack.setAmount(itemStack.getAmount()-1);
                event.getPlayer().sendMessage(config.getContent().getAddedCompost(itemStack.getItemMeta().getDisplayName()));
            }
            else if(activeCrop.getCropType() instanceof RightClickCrop rightClickCrop){
                rightClickCrop.onRightClick(activeCrop);
            }
        }
    }

    private Compost readCompost(ItemStack itemStack){
        if(itemStack != null){
            NBTItem nbtItem = NBTItem.get(itemStack);
            double len = nbtItem.getDouble("MMOITEMS_COMPOST_LEN");
            double str = nbtItem.getDouble("MMOITEMS_COMPOST_STR");
            if(len != 0.0D && str != 0.0D){
                return new Compost(str, (long) len);
            }
        }
        return null;
    }
}
