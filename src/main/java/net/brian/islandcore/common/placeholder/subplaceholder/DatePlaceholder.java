package net.brian.islandcore.common.placeholder.subplaceholder;

import dev.reactant.reactant.core.component.Component;
import dev.reactant.reactant.core.dependency.injection.Inject;
import net.brian.islandcore.season.IslandSeasonService;

@Component
public class DatePlaceholder extends SubPlaceholder{

    @Inject
    IslandSeasonService seasonService;

    public DatePlaceholder() {
        super("date");
    }

    @Override
    public String onRequest(String islandUUID, String[] args) {
        return seasonService.getDate();
    }
}
