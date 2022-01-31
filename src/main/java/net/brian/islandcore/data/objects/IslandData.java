package net.brian.islandcore.data.objects;

import world.bentobox.bentobox.database.objects.Island;

import java.util.UUID;

public abstract class IslandData {
    protected String uuid;

    public IslandData(String uuid){
        this.uuid = uuid;
    }

    public IslandData(){}

    public String getUuid() {
        return uuid;
    }
}
