package net.brian.islandcore.crop;

import dev.reactant.reactant.core.component.Component;
import dev.reactant.reactant.core.component.lifecycle.LifeCycleHook;
import dev.reactant.reactant.core.dependency.injection.Inject;
import dev.reactant.reactant.extra.config.type.MultiConfigs;
import io.github.clayclaw.clawlibrary.reloader.ReloaderComponent;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.brian.islandcore.common.persistent.BlockMeta;
import net.brian.islandcore.crop.crops.IslandCrop;
import net.brian.islandcore.crop.crops.Tomato;
import net.brian.islandcore.crop.objects.ActiveCrop;
import net.brian.islandcore.crop.objects.IslandCropProfile;
import net.brian.islandcore.data.IslandDataService;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import world.bentobox.bentobox.BentoBox;
import world.bentobox.bentobox.api.user.User;
import world.bentobox.bentobox.database.objects.Island;

import java.util.*;

@Component
public class IslandCropService implements LifeCycleHook, ReloaderComponent {

    private static IslandCropService instance;
    private final BentoBox bentoBox = BentoBox.getInstance();

    @Inject
    IslandDataService islandDataService;

    @Inject(name = "plugins/IslandFarming/Crops")
    MultiConfigs<IslandCrop> sharedConfig;

    private final HashMap<String, IslandCrop> cropMap = new HashMap<>();

    @Override
    public void onEnable(){
        instance = this;
        islandDataService.register("crops", IslandCropProfile.class);
        ActiveCrop.islandCropManager = this;
        cropMap.put("tomato",new Tomato());
    }

    @Override
    public void reload(){
        sharedConfig.getAll(true).blockingForEach(it->{
            IslandCrop content = it.getContent();
            cropMap.put(content.getId(),content);
        });
    }

    public ActiveCrop getActiveCrop(Block block){
        if(isCrop(block)){
            IslandCropProfile profile = getProfileFromCrop(block);
            if(profile != null) return profile.getCrop(block);
        }
        return null;
    }

    public IslandCropProfile getProfile(String uuid){
        return islandDataService.getData(uuid,IslandCropProfile.class);
    }

    public IslandCropProfile getProfileFromCrop(Block block){
        String uuid = BlockMeta.readString(block,"island_uuid");
        if(uuid != null){
            return getProfile(uuid);
        }
        return null;
    }

    public IslandCropProfile getProfile(UUID playerUUID){
        Island island = bentoBox.getIslandsManager().getIsland(islandDataService.getWorld(), User.getInstance(playerUUID));
        if(island != null){
            return getProfile(island.getUniqueId());
        }
        return null;
    }

    public boolean isCrop(Block block){
        return block.hasMetadata("island_uuid");
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
}
