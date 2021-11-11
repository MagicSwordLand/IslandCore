package net.brian.islandcore;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.reactant.reactant.core.ReactantPlugin;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.stat.type.DoubleStat;
import net.Indyuce.mmoitems.stat.type.StringStat;
import net.brian.islandcore.data.gson.PostProcessable;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

@ReactantPlugin(servicePackages = "net.brian.islandcore")
public class IslandCropsAndLiveStocks extends JavaPlugin {

    private static IslandCropsAndLiveStocks instance;
    public static Gson gson;


    @Override
    public void onLoad(){
        gson = new GsonBuilder().registerTypeAdapterFactory(new PostProcessable.PostProcessingEnabler()).create();
        MMOItems.plugin.getStats().register(new StringStat("SEED",Material.WHEAT_SEEDS,"種子類型",new String[]{"種下的種子類型"},null,Material.WHEAT_SEEDS));
        MMOItems.plugin.getStats().register(new DoubleStat("FODDER",Material.WHEAT,"飼料強度",new String[]{"飼料的恢復得飽實度"}));
    }


    @Override
    public void onEnable(){
        instance = this;
    }

    @Override
    public void onDisable(){

    }

    public static IslandCropsAndLiveStocks getInstance(){
        return instance;
    }
}
