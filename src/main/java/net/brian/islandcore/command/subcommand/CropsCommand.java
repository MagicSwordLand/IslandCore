package net.brian.islandcore.command.subcommand;

import dev.reactant.reactant.core.component.Component;
import dev.reactant.reactant.core.dependency.injection.Inject;
import net.brian.islandcore.crop.IslandCropService;
import net.brian.islandcore.crop.objects.IslandCropProfile;
import net.brian.islandcore.data.IslandDataService;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;



@Component
public class CropsCommand extends SubCommand{

    @Inject
    IslandCropService cropService;

    @Inject
    IslandDataService dataService;

    public CropsCommand() {
        super("crops");
    }

    /*
    /IslandCore crops unlock <OfflinePlayer> <id>
    /IslandCore crops farmland <set/add> <amount>
     */


    @Override
    public void onCommand(CommandSender commandSender, String[] args) {
        if(args.length>0 && args[1].equalsIgnoreCase("check")){
            IslandCropProfile cropProfile = cropService.getProfile((OfflinePlayer) commandSender);
            if(cropProfile != null) {
                cropProfile.getCrops().forEach(crop->{
                    Location loc = crop.getLocation().getBukkitLoc();
                    int x = (int) loc.getX(),y= (int) loc.getY(),z= (int) loc.getZ();
                    commandSender.sendMessage(crop.getCropType().getName()+"§f : "+x+","+y+","+z);
                });
                return;
            }
        }
        if(args.length<4 || !commandSender.hasPermission("islandcore.admin")){
            commandSender.sendMessage("你沒有權限或指令使用錯誤");
            return;
        }
        OfflinePlayer player = Bukkit.getOfflinePlayer(args[2]);
        if(args[1].equalsIgnoreCase("unlock")){
            if(!player.hasPlayedBefore()){
                commandSender.sendMessage("找不到該玩家");
                return;
            }
            IslandCropProfile cropProfile = cropService.getProfile(player);
            if(cropProfile == null) {
                commandSender.sendMessage("&c必須要至少有一位該島的玩家才可以使用此指令");
                return;
            }
            cropProfile.unlock(args[3]);
        }
        else if(args[1].equalsIgnoreCase("farmland")){
            IslandCropProfile cropProfile = cropService.getProfile(player);
            if(cropProfile == null) {
                commandSender.sendMessage("&c必須要至少有一位該島的玩家才可以使用此指令");
                return;
            }
            if(args[2].equalsIgnoreCase("set")){
                cropProfile.setFarmLand(Integer.parseInt(args[3]));
            }
            else if(args[2].equalsIgnoreCase("add")){
                cropProfile.increaseLimit(Integer.parseInt(args[3]));
            }
        }
    }
}
