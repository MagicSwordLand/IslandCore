package net.brian.islandcore.common.persistent;

import io.github.clayclaw.islandcore.IslandCore;
import net.brian.islandcore.IslandCropsAndLiveStocks;
import org.bukkit.NamespacedKey;

public class Namespaces {
    public final static NamespacedKey islandID = new NamespacedKey(IslandCore.getInstance(),"IslandKey");
    public final static NamespacedKey stock_type = new NamespacedKey(IslandCore.getInstance(),"stock_type");
}
