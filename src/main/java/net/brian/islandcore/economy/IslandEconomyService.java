package net.brian.islandcore.economy;

import dev.reactant.reactant.core.component.Component;
import dev.reactant.reactant.core.component.lifecycle.LifeCycleHook;
import dev.reactant.reactant.core.dependency.injection.Inject;
import jdk.jfr.Enabled;
import net.brian.islandcore.data.IslandDataService;
import net.brian.islandcore.economy.object.IslandEconomyProfile;
import org.bukkit.entity.Player;
import world.bentobox.bentobox.BentoBox;
import world.bentobox.bentobox.api.user.User;
import world.bentobox.bentobox.database.objects.Island;
import world.bentobox.bentobox.managers.RanksManager;

@Component
public class IslandEconomyService implements LifeCycleHook {

    @Inject
    IslandDataService dataService;

    private final String dataKey = "Economy";

    @Override
    public void onEnable(){
        dataService.register(dataKey , IslandEconomyProfile.class);
    }


    public IslandEconomyProfile getProfile(String uuid){
        return dataService.getData(uuid,IslandEconomyProfile.class);
    }

    public IslandEconomyProfile getProfileFromPlayer(Player player){
        String uuid = dataService.getIslandUUIDFromPlayer(player);
        if(uuid != null){
            return getProfile(uuid);
        }
        return null;
    }

    public boolean canUse(Player player){
        User user = User.getInstance(player);
        Island island = BentoBox.getInstance().getIslands().getIsland(dataService.getWorld(), user);
        if(island != null){
            return island.getRank(user) >= RanksManager.SUB_OWNER_RANK;
        }
        return false;
    }

}
