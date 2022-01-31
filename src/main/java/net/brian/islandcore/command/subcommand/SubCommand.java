package net.brian.islandcore.command.subcommand;

import dev.reactant.reactant.core.component.lifecycle.LifeCycleHook;
import dev.reactant.reactant.core.dependency.injection.Inject;
import net.brian.islandcore.command.CommandManager;
import org.bukkit.command.CommandSender;

public abstract class SubCommand implements LifeCycleHook {


    @Inject
    CommandManager commandManager;

    private final String name;
    public SubCommand(String name){
        this.name = name;
    }

    @Override
    public void onEnable(){
        commandManager.register(this);
    }


    public String getName() {
        return name;
    }

    public abstract void onCommand(CommandSender commandSender,String[] args);
}
