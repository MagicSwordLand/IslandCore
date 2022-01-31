package net.brian.islandcore.common.placeholder.subplaceholder;

import dev.reactant.reactant.core.component.Component;
import dev.reactant.reactant.core.dependency.injection.Inject;
import net.brian.islandcore.command.subcommand.SubCommand;
import net.brian.islandcore.crop.IslandCropService;
import net.brian.islandcore.crop.crops.IslandCrop;
import net.brian.islandcore.crop.objects.IslandCropProfile;


@Component
public class CropLevel extends SubPlaceholder {

    public CropLevel() {
        super("croplevel");
    }

    @Inject
    IslandCropService cropService;

    @Override
    public String onRequest(String islandUUID, String[] args) {
        if(args.length>0){
            IslandCropProfile cropProfile = cropService.getProfile(islandUUID);
            if(cropProfile != null){
                IslandCrop crop = cropService.getCrop(args[0]);

            }
        }
        return "設定錯誤 請輸入作物ID";
    }
}
