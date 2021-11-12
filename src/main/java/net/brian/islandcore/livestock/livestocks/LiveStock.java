package net.brian.islandcore.livestock.livestocks;

import io.github.clayclaw.clawlibrary.java.ColorUtil;
import io.lumine.xikage.mythicmobs.MythicMobs;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public abstract class LiveStock {
    protected String id;
    protected String mythic_mob;
    protected String name;

    int grow_time;
    int max_food;
    int food_consumption;

    public Entity instantiate(Location location){
        return MythicMobs.inst().getMobManager().spawnMob(mythic_mob,location).getEntity().getBukkitEntity();
    }

    public String getId() {
        return id;
    }


    public String getName() {
        return ColorUtil.translateColor(name);
    }
}
