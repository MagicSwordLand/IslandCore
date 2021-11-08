package net.brian.islandcore.crop.managers;

import dev.reactant.reactant.core.component.Component;
import dev.reactant.reactant.core.component.lifecycle.LifeCycleHook;
import dev.reactant.reactant.core.dependency.injection.Inject;
import net.brian.islandcore.crop.objects.IslandCropProfile;
import net.brian.islandcore.data.IslandDataService;

import java.util.HashMap;

@Component
public class IslandCropManager implements LifeCycleHook {

    @Inject
    IslandDataService islandDataService;


    @Override
    public void onEnable(){
        islandDataService.register("crops", IslandCropProfile.class);
    }


}
