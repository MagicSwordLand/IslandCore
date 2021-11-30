package net.brian.islandcore.common.placeholder.subplaceholder;

import dev.reactant.reactant.core.component.Component;
import dev.reactant.reactant.core.dependency.injection.Inject;
import io.github.clayclaw.islandcore.season.SeasonServiceImpl;
import org.bukkit.OfflinePlayer;

@Component
public class SeasonPlaceholder extends SubPlaceholder{
    public SeasonPlaceholder() {
        super("season");
    }

    @Inject
    SeasonServiceImpl seasonService;

    @Override
    public String onRequest(OfflinePlayer player, String[] args) {
        return seasonService.getCurrentSeason().getLocalizedName();
    }
}
