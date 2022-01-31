package net.brian.islandcore.season;

import dev.reactant.reactant.core.component.Component;
import dev.reactant.reactant.core.component.lifecycle.LifeCycleHook;
import dev.reactant.reactant.core.dependency.injection.Inject;
import dev.reactant.reactant.extra.config.type.SharedConfig;
import io.github.clayclaw.clawlibrary.reloader.ReloaderComponent;
import me.casperge.realisticseasons.RealisticSeasons;
import me.casperge.realisticseasons.api.SeasonsAPI;
import me.casperge.realisticseasons.calendar.Date;
import me.casperge.realisticseasons.calendar.TimeManager;
import me.casperge.realisticseasons.season.Season;
import net.brian.islandcore.data.IslandDataService;
import net.brian.islandcore.season.config.SeasonSettings;
import net.brian.mythicpet.motd.PlayerJoin;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;

@Component
public class IslandSeasonService implements Listener, ReloaderComponent {

    boolean synced = false;

    @Inject
    IslandDataService dataService;


    @Inject(name = "plugins/IslandFarming/Season.yml")
    SharedConfig<SeasonSettings> config;


    public void onJoin(PlayerJoinEvent event){
        if(!synced){
            final World world = config.getContent().getWorld();
            Season season = SeasonsAPI.getInstance().getSeason(world);
            Date date = SeasonsAPI.getInstance().getDate(world);
            Bukkit.getWorlds().forEach(world1 -> {
                if(!world1.equals(world)){
                    SeasonsAPI.getInstance().setSeason(world1,season);
                    SeasonsAPI.getInstance().setDate(world1,date);
                }
            });
            synced = true;
        }
    }

    public Season getSeason(){
        return RealisticSeasons.getInstance().getSeasonManager().getSeason(config.getContent().getWorld());
    }

    public String getDate(){
        TimeManager timeManager = RealisticSeasons.getInstance().getTimeManager();
        Date date = timeManager.getDate(config.getContent().getWorld());
        int hour = timeManager.getHours(config.getContent().getWorld());
        int minutes = timeManager.getMinutes(config.getContent().getWorld());
        return date.getMonth()+"/"+date.getDay()+" "+hour+":"+minutes;
    }


    @Override
    public void reload() {

    }
}
