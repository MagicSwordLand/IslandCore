package net.brian.islandcore.crop.managers;

import dev.reactant.reactant.core.component.Component;
import dev.reactant.reactant.core.component.lifecycle.LifeCycleHook;
import dev.reactant.reactant.core.dependency.injection.Inject;
import dev.reactant.reactant.extra.config.type.MultiConfigs;
import io.github.clayclaw.clawlibrary.reloader.ReloaderComponent;
import io.lumine.mythic.lib.api.item.NBTItem;
import net.brian.islandcore.crop.crops.IslandCrop;
import net.brian.islandcore.crop.objects.ActiveCrop;
import net.brian.islandcore.crop.objects.IslandCropProfile;
import net.brian.islandcore.data.IslandDataService;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Component
public class IslandCropManager implements LifeCycleHook, ReloaderComponent {

    @Inject
    IslandDataService islandDataService;

    @Inject(name = "plugins/IslandFarming/Crops")
    MultiConfigs<IslandCrop> sharedConfig;

    private final HashMap<String, IslandCrop> cropMap = new HashMap<>();


    @Override
    public void onEnable(){
        islandDataService.register("crops", IslandCropProfile.class);
        ActiveCrop.islandCropManager = this;
    }

    @Override
    public void reload(){
        sharedConfig.getAll(true).blockingForEach(it->{
            IslandCrop content = it.getContent();
            cropMap.put(content.getId(),content);
        });
    }

    public IslandCrop getCrop(String id){
        return cropMap.get(id);
    }

    public static String readSeed(ItemStack itemStack){
        NBTItem nbtItem = NBTItem.get(itemStack);
        return nbtItem.getString("MMOITEMS_SEED");
    }


}
