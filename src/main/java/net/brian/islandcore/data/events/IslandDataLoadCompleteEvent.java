package net.brian.islandcore.data.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import world.bentobox.bentobox.database.objects.Island;

public class IslandDataLoadCompleteEvent extends Event {
    private final Island island;


    private final static HandlerList handler = new HandlerList();


    public IslandDataLoadCompleteEvent(Island island){
        this.island = island;

    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handler;
    }

    public HandlerList getHandlerList(){
        return handler;
    }

    public Island getIsland() {
        return island;
    }
}
