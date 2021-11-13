package net.brian.islandcore.data.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import world.bentobox.bentobox.database.objects.Island;

public class IslandDataLoadCompleteEvent extends Event {
    private final Island island;
    private final static HandlerList handlerList = new HandlerList();

    public IslandDataLoadCompleteEvent(Island island){
        this.island = island;
    }

    public Island getIsland() {
        return island;
    }

    public static HandlerList getHandlerList(){
        return handlerList;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }
}
