package net.brian.islandcore.command.subcommand;

import org.bukkit.command.CommandSender;

public abstract class SubCommand {

    private final String name;
    public SubCommand(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void onCommand(CommandSender commandSender);
}
