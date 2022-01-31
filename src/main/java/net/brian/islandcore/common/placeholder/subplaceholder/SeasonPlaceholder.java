package net.brian.islandcore.common.placeholder.subplaceholder;

import dev.reactant.reactant.core.component.Component;
import dev.reactant.reactant.core.dependency.injection.Inject;
import io.github.clayclaw.islandcore.season.SeasonServiceImpl;
import me.casperge.realisticseasons.api.SeasonsAPI;
import net.brian.islandcore.season.IslandSeasonService;
import org.bukkit.OfflinePlayer;

@Component
public class SeasonPlaceholder extends SubPlaceholder{

    public SeasonPlaceholder() {
        super("season");
    }

    @Inject
    IslandSeasonService seasonService;


    @Override
    public String onRequest(String islandUUID, String[] args) {
        return seasonService.getSeason().toString();
    }

}
