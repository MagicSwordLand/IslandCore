package net.brian.islandcore.common.persistent;

import net.brian.islandcore.IslandCore;
import org.bukkit.NamespacedKey;
import org.checkerframework.checker.units.qual.N;

public class Namespaces {
    public final static NamespacedKey islandID = new NamespacedKey(IslandCore.getInstance(),"islandID");
    public final static NamespacedKey stock_type = new NamespacedKey(IslandCore.getInstance(),"stock_type");
    public final static NamespacedKey crop_type = new NamespacedKey(IslandCore.getInstance(),"crop_type");

}
