package net.brian.islandcore.crop.crops;

import dev.reactant.reactant.core.component.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ItemFrame;

@Component
public class Lotus extends IslandCrop{
    protected Lotus() {
        super("lotus");
    }

    @Override
    public void updateAppearance(int stage, ItemFrame itemFrame) {

    }

    @Override
    public ItemFrame instantiate(Location location, String islandUUID) {
        return null;
    }

    @Override
    public boolean checkLocation(Location loc) {
        if(loc.getBlock().getType().equals(Material.WATER)){
            if(loc.add(0,1,0).getBlock().getType().equals(Material.AIR)){
                return true;
            }
        }
        return false;
    }
}
