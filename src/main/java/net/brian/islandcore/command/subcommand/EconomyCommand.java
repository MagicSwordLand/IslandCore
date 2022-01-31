package net.brian.islandcore.command.subcommand;

import dev.reactant.reactant.core.component.Component;
import dev.reactant.reactant.core.dependency.injection.Inject;
import net.brian.islandcore.data.IslandDataService;
import net.brian.islandcore.economy.IslandEconomyService;
import net.brian.islandcore.economy.object.IslandEconomyProfile;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


@Component
public class EconomyCommand extends SubCommand {

    @Inject
    IslandEconomyService economyService;

    public EconomyCommand() {
        super("money");
    }

    /*
    /IslandCore money give <player> <amount>
    /IslandCore money set <player> <amount>
     */

    @Override
    public void onCommand(CommandSender commandSender, String[] args) {
        if(commandSender.hasPermission("islandcore.admin")){
            if(args.length < 4) return;
            Player player = Bukkit.getPlayer(args[2]);
            if(player == null){
                return;
            }
            IslandEconomyProfile economyProfile = economyService.getProfileFromPlayer(player);
            if(economyProfile == null){
                commandSender.sendMessage("&c必須要至少有一位玩家在島嶼上才可執行此指令");
                return;
            }
            if(args[1].equalsIgnoreCase("give")){
                economyProfile.give((int) Double.parseDouble(args[3]));
            }
            else if(args[1].equalsIgnoreCase("set")){
                economyProfile.setMoney((int) Double.parseDouble(args[3]));
            }
        }
    }

}
