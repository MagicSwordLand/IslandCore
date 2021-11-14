package net.brian.islandcore.common.placeholder.subplaceholder;

import dev.reactant.reactant.core.component.Component;
import dev.reactant.reactant.core.dependency.injection.Inject;
import net.brian.islandcore.crop.IslandCropService;
import net.brian.islandcore.crop.crops.IslandCrop;
import net.brian.islandcore.crop.objects.IslandCropProfile;
import net.brian.islandcore.data.IslandDataService;
import org.bukkit.OfflinePlayer;
import world.bentobox.bentobox.api.user.User;
import world.bentobox.bentobox.database.objects.Island;

@Component
public class CropCount extends SubPlaceholder{

    public CropCount() {
        super("crops");
    }

    @Inject
    IslandCropService cropService;
    @Inject
    IslandDataService dataService;


    @Override
    public String onRequest(OfflinePlayer player, String[] args) {
        Island island = bentoBox.getIslandsManager().getIsland(dataService.getWorld(), User.getInstance(player));
        if(island == null) return null;
        IslandCropProfile profile = cropService.getProfile(island.getUniqueId());
        return String.valueOf(profile.getHarvestCount(args[0]));
    }

}
