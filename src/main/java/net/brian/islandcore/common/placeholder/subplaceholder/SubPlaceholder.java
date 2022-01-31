package net.brian.islandcore.common.placeholder.subplaceholder;

import dev.reactant.reactant.core.component.lifecycle.LifeCycleHook;
import dev.reactant.reactant.core.dependency.injection.Inject;
import net.brian.islandcore.common.placeholder.IslandPlaceholder;
import org.bukkit.OfflinePlayer;
import world.bentobox.bentobox.BentoBox;

public abstract class SubPlaceholder implements LifeCycleHook {

    private String id;
    protected BentoBox bentoBox = BentoBox.getInstance();

    public SubPlaceholder(String id){
        this.id = id;
    }

    @Inject
    protected IslandPlaceholder main;

    @Override
    public void onEnable(){
        main.register(this);
    }

    public String getId() {
        return id;
    }

    public abstract String onRequest(String islandUUID, String[] args);
}
