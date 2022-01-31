package net.brian.islandcore.data.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import world.bentobox.bentobox.database.objects.Island;

public class IslandUnloadEvent  extends Event {

    private final Island island;
    private final Player triggered;
    private final static HandlerList handlerList = new HandlerList();

    public IslandUnloadEvent(Island island, Player triggered){
        this.triggered = triggered;
        this.island = island;
    }

    public Island getIsland() {
        return island;
    }

    public Player getTriggered() {
        return triggered;
    }

    public @NotNull
    HandlerList getHandlers() {
        return handlerList;
    }
    public static HandlerList getHandlerList(){
        return handlerList;
    }
}
