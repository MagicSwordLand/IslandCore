package net.brian.islandcore;

import dev.reactant.reactant.core.ReactantPlugin;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.stat.type.DoubleStat;
import net.Indyuce.mmoitems.stat.type.StringStat;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public class IslandCore extends JavaPlugin {

    private static IslandCore instance;

    @Override
    public void onLoad(){
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

    public static IslandCore getInstance(){
        return instance;
    }
}
