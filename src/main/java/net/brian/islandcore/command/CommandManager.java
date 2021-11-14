package net.brian.islandcore.command;

import dev.reactant.reactant.core.component.Component;
import dev.reactant.reactant.core.component.lifecycle.LifeCycleHook;
import io.github.clayclaw.islandcore.IslandCore;
import io.lumine.shadow.bukkit.ObfuscatedTarget;
import net.brian.islandcore.IslandCropsAndLiveStocks;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

@Component
public class CommandManager implements CommandExecutor, LifeCycleHook {

    @Override
    public void onEnable(){
        IslandCore.getInstance().getCommand("IslandCrop").setExecutor(this);
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return false;
    }
}
