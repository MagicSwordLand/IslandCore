package net.brian.islandcore.command;

import dev.reactant.reactant.core.component.Component;
import dev.reactant.reactant.core.component.lifecycle.LifeCycleHook;
import io.github.clayclaw.islandcore.IslandCore;
import io.lumine.shadow.bukkit.ObfuscatedTarget;
import net.brian.islandcore.IslandCropsAndLiveStocks;
import net.brian.islandcore.command.subcommand.SubCommand;
import net.brian.islandcore.common.objects.IslandLogger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommandManager implements CommandExecutor, LifeCycleHook {

    private List<SubCommand> subCommands;

    @Override
    public void onEnable(){
        subCommands = new ArrayList<>();
        IslandCore.getInstance().getCommand("IslandCore").setExecutor(this);
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 0) return true;
        SubCommand subCommand = getCommand(args[0]);
        if(subCommand != null){
            subCommand.onCommand(sender,args);
        }
        else{
            sender.sendMessage("未知指令");
        }
        return true;
    }

    public void register(SubCommand subCommand){
        subCommands.add(subCommand);
        IslandLogger.logInfo("Register command "+subCommand.getName());
    }

    private SubCommand getCommand(String name){
        for(SubCommand subCommand : subCommands){
            if(name.equalsIgnoreCase(subCommand.getName())){
                return subCommand;
            }
        }
        return null;
    }

}
