package net.brian.islandcore.common.placeholder.subplaceholder;

import dev.reactant.reactant.core.component.Component;
import dev.reactant.reactant.core.dependency.injection.Inject;
import net.brian.islandcore.crop.IslandCropService;
import net.brian.islandcore.crop.objects.IslandCropProfile;
import org.bukkit.OfflinePlayer;

@Component
public class FarmLand extends SubPlaceholder{
    public FarmLand() {
        super("farmland");
    }

    @Inject
    IslandCropService cropService;

    @Override
    public String onRequest(OfflinePlayer player, String[] args) {
        if(args.length == 0) return "0";
        IslandCropProfile cropProfile = cropService.getProfile(player.getUniqueId());
        if(cropProfile != null){
            if(args[0].equals("used")){
                return String.valueOf(cropProfile.getCrops().size());
            }
            if(args[0].equals("limit")){
                return String.valueOf(cropProfile.getCropLimit());
            }
        }
        return "0";
    }
}
