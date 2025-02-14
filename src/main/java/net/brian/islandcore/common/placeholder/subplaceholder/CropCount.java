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
    public String onRequest(String islandUUID, String[] args) {
        if(args.length == 0) return "&c0";
        IslandCropProfile profile = cropService.getProfile(islandUUID);
        if(profile == null) return "0";
        return String.valueOf(profile.getHarvestCount(args[0]));
    }


}
