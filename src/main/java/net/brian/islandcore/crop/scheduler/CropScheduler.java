package net.brian.islandcore.crop.scheduler;

import dev.reactant.reactant.core.component.Component;
import dev.reactant.reactant.core.dependency.injection.Inject;
import io.github.clayclaw.islandcore.season.SeasonService;
import io.github.clayclaw.islandcore.season.request.SeasonAPI;
import io.github.clayclaw.islandcore.updater.AliveIslandUpdater;
import me.casperge.realisticseasons.api.SeasonsAPI;
import net.brian.islandcore.IslandCropsAndLiveStocks;
import net.brian.islandcore.common.objects.IslandLogger;
import net.brian.islandcore.crop.IslandCropService;
import net.brian.islandcore.crop.objects.IslandCropProfile;
import net.brian.islandcore.data.IslandDataService;
import net.brian.islandcore.season.IslandSeasonService;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import world.bentobox.bentobox.database.objects.Island;

import java.util.concurrent.CompletableFuture;

@Component
public class CropScheduler implements AliveIslandUpdater {

    @Inject
    IslandCropService cropService;


    @Inject
    IslandSeasonService seasonService;


    @Override
    public long getCycleTime() {
        return 1200;
    }


    @Override
    public void onUpdate(@NotNull Island island) {
        IslandCropProfile cropProfile = cropService.getProfile(island.getUniqueId());
        if(cropProfile != null){
            cropProfile.ageAll(seasonService.getSeason());
            cropProfile.checkCropsLocation();
        }
    }

}
