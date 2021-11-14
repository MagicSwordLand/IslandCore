package net.brian.islandcore.crop.scheduler;

import dev.reactant.reactant.core.component.Component;
import dev.reactant.reactant.core.dependency.injection.Inject;
import io.github.clayclaw.islandcore.updater.AliveIslandUpdater;
import net.brian.islandcore.IslandCropsAndLiveStocks;
import net.brian.islandcore.common.objects.IslandLogger;
import net.brian.islandcore.crop.IslandCropService;
import net.brian.islandcore.crop.objects.IslandCropProfile;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import world.bentobox.bentobox.database.objects.Island;

import java.util.concurrent.CompletableFuture;

@Component
public class CropScheduler implements AliveIslandUpdater {

    @Inject
    IslandCropService cropService;


    @Override
    public long getCycleTime() {
        return 10;
    }


    @Override
    public void onUpdate(@NotNull Island island) {
        IslandCropProfile cropProfile = cropService.getProfile(island.getUniqueId());
        cropProfile.ageAll(6);
    }

}
