package net.brian.islandcrop;

import dev.reactant.reactant.core.ReactantPlugin;
import net.brian.islandcrop.listener.IslandDataManager;
import net.brian.islandcrop.listener.IslandEventsListener;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import world.bentobox.bentobox.BentoBox;

import java.util.logging.Level;
import java.util.logging.Logger;

@ReactantPlugin(servicePackages = "net.brian.islandcrop")
public class IslandCrop extends JavaPlugin {

    private static final Logger logger = Logger.getLogger("IslandCrop");


    @Override
    public void onEnable(){
        getServer().getPluginManager().registerEvents(new IslandEventsListener(BentoBox.getInstance()),this);
        getServer().getPluginManager().registerEvents(new IslandDataManager(),this);
    }

    @Override
    public void onDisable(){

    }

    public static void log(String msg){
        logger.log(Level.INFO,msg);
    }

}
