package net.brian.islandcore;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.reactant.reactant.core.ReactantPlugin;
import io.github.clayclaw.islandcore.IslandCore;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.stat.type.DoubleStat;
import net.Indyuce.mmoitems.stat.type.StringStat;
import net.brian.islandcore.data.gson.PostProcessable;
import net.brian.islandcore.data.listener.IslandEventsListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.Executor;

public class IslandCropsAndLiveStocks {

    public static Gson gson = IslandCore.getGson();

    public static Executor getExecutor(){
        return IslandCore.getExecutor();
    }

}
