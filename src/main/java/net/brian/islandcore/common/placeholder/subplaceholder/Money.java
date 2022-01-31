package net.brian.islandcore.common.placeholder.subplaceholder;

import dev.reactant.reactant.core.component.Component;
import dev.reactant.reactant.core.dependency.injection.Inject;
import net.brian.islandcore.economy.IslandEconomyService;
import net.brian.islandcore.economy.object.IslandEconomyProfile;



@Component
public class Money extends SubPlaceholder{

    @Inject
    IslandEconomyService economyService;

    public Money() {
        super("money");
    }

    @Override
    public String onRequest(String islandUUID, String[] args) {
        IslandEconomyProfile economyProfile = economyService.getProfile(islandUUID);
        if(economyProfile != null){
            return String.valueOf(economyProfile.getMoney());
        }
        return "0";
    }

}
