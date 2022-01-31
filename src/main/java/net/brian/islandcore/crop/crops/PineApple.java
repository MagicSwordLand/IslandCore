package net.brian.islandcore.crop.crops;

import dev.reactant.reactant.core.component.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ItemFrame;

@Component
public class PineApple extends IslandCrop{
    protected PineApple() {
        super("pineapple");
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
        if(loc.getBlock().getType().equals(Material.AIR)){
            Material mat = loc.add(0,1,0).getBlock().getType();
            return mat.equals(Material.GRASS_BLOCK) || mat.equals(Material.DIRT) || mat.equals(Material.FARMLAND);
        }
        return false;
    }
}
