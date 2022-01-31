package net.brian.islandcore.common.placeholder;

import dev.reactant.reactant.core.component.Component;
import dev.reactant.reactant.core.component.lifecycle.LifeCycleHook;
import dev.reactant.reactant.core.dependency.injection.Inject;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.brian.islandcore.crop.IslandCropService;
import net.brian.islandcore.common.placeholder.subplaceholder.SubPlaceholder;
import net.brian.islandcore.data.IslandDataService;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Component
public class IslandPlaceholder extends PlaceholderExpansion implements LifeCycleHook {

    @Inject
    IslandDataService dataService;

    List<SubPlaceholder> placeholders = new ArrayList<>();

    @Override
    public void onEnable(){
        this.register();
    }

    private SubPlaceholder getPlaceHolder(String id){
        for(SubPlaceholder placeholder : placeholders){
            if(placeholder.getId().equals(id)){
                return placeholder;
            }
        }
        return null;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "island";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Sleep_AllDay";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        String[] args = params.split("_",2);
        SubPlaceholder placeholder = getPlaceHolder(args[0]);
        if(placeholder != null){
            if(args.length>1){
                args = args[1].split("_");
            }
            String uuid = dataService.getIslandUUIDFromPlayer(player);
            return placeholder.onRequest(uuid,args);
        }
        return null; // Placeholder is unknown by the expansion
    }

    public void register(SubPlaceholder subPlaceholder){
        placeholders.add(subPlaceholder);
    }
}
