package net.brian.islandcore.crop;

import dev.reactant.reactant.core.component.Component;
import dev.reactant.reactant.core.component.lifecycle.LifeCycleHook;
import dev.reactant.reactant.core.dependency.injection.Inject;
import dev.reactant.reactant.extra.config.type.MultiConfigs;
import dev.reactant.reactant.extra.config.type.SharedConfig;
import io.github.clayclaw.clawlibrary.reloader.ReloaderComponent;
import io.github.clayclaw.islandcore.IslandCore;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.brian.islandcore.crop.config.MessageConfig;
import net.brian.islandcore.crop.crops.IslandCrop;
import net.brian.islandcore.crop.objects.ActiveCrop;
import net.brian.islandcore.crop.objects.IslandCropProfile;
import net.brian.islandcore.data.IslandDataService;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import world.bentobox.bentobox.BentoBox;

import java.util.*;

@Component
public class IslandCropService implements LifeCycleHook, ReloaderComponent {

    private static IslandCropService instance;
    private final BentoBox bentoBox = BentoBox.getInstance();

    @Inject
    IslandDataService islandDataService;

    @Inject(name = "plugins/IslandFarming/Crops")
    MultiConfigs<IslandCrop> sharedConfig;

    @Inject(name = "plugins/IslandFarming/Message.json")
    SharedConfig<MessageConfig> messageConfigSharedConfig;

    private final HashMap<String, IslandCrop> cropMap = new HashMap<>();

    @Override
    public void onEnable(){
        instance = this;
        islandDataService.register("crops", IslandCropProfile.class);
        ActiveCrop.islandCropManager = this;
        reload();
    }

    @Override
    public void reload(){
        messageConfigSharedConfig.refresh().blockingAwait();
        messageConfigSharedConfig.getContent().setColors();
        sharedConfig.getAll(true).blockingForEach(it->{
            IslandCrop content = it.getContent();
            cropMap.put(content.getId(),content);
        });
    }

    public ActiveCrop getActiveCrop(Entity entity){
        if(isCrop(entity)){
            IslandCropProfile profile = getProfileFromCrop(entity);
            if(profile != null) return profile.getCrop((ItemFrame) entity);
        }
        return null;
    }

    public IslandCropProfile getProfile(String uuid){
        return islandDataService.getData(uuid,IslandCropProfile.class);
    }


    public IslandCropProfile getProfileFromCrop(Entity entity){
        if(entity.getPersistentDataContainer().has(IslandCore.islandKey, PersistentDataType.STRING)){
            String uuid = entity.getPersistentDataContainer().get(IslandCore.islandKey,PersistentDataType.STRING);
            return getProfile(uuid);
        }
        return null;
    }

    public IslandCropProfile getProfile(OfflinePlayer player){
        String uuid = islandDataService.getIslandUUIDFromPlayer(player);
        if(uuid != null){
            return getProfile(uuid);
        }
        return null;
    }

    public static boolean isCrop(Entity entity){
        return entity.getPersistentDataContainer().has(IslandCore.islandKey,PersistentDataType.STRING);
    }

    public IslandCrop getCrop(String id){
        return cropMap.get(id);
    }

    public static String readSeed(ItemStack itemStack){
        NBTItem nbtItem = NBTItem.get(itemStack);
        return nbtItem.getString("MMOITEMS_SEED");
    }

    public static IslandCropService getInstance() {
        return instance;
    }

    public ActiveCrop getCropAtLocation(Location location){
        Location loc = location.clone();
        loc.add(0.5,0,0.5);
        Collection<ItemFrame> collection = location.getNearbyEntitiesByType(ItemFrame.class,0.01);
        if(collection.isEmpty()){
            return null;
        }
        ItemFrame itemFrame = collection.stream().findFirst().get();
        if(isCrop(itemFrame)){
            IslandCropProfile cropProfile = getProfileFromCrop(itemFrame);
            if(cropProfile == null){
                return null;
            }
            ActiveCrop activeCrop = cropProfile.getCrop(itemFrame);
            if(activeCrop == null){
                itemFrame.remove();
                return  null;
            }
            return activeCrop;
        }
        return null;
    }

    public void registerCrop(String name,IslandCrop islandCrop){
        cropMap.put(name,islandCrop);
    }


}
