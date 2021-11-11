package net.brian.islandcore.common.persistent;

import net.brian.islandcore.IslandCropsAndLiveStocks;
import org.bukkit.NamespacedKey;

public class Namespaces {
    public final static NamespacedKey islandID = new NamespacedKey(IslandCropsAndLiveStocks.getInstance(),"islandID");
    public final static NamespacedKey stock_type = new NamespacedKey(IslandCropsAndLiveStocks.getInstance(),"stock_type");
    public final static NamespacedKey crop_type = new NamespacedKey(IslandCropsAndLiveStocks.getInstance(),"crop_type");

}
