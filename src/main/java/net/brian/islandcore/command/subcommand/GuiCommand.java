package net.brian.islandcore.command.subcommand;

import dev.reactant.reactant.core.component.Component;
import dev.reactant.reactant.core.dependency.injection.Inject;
import net.brian.islandcore.gui.GuiManager;
import net.brian.islandcore.gui.objects.Gui;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;



@Component
public class GuiCommand extends SubCommand{
    public GuiCommand() {
        super("gui");
    }

    @Inject
    GuiManager guiManager;

    /*
    /islandcore gui <id> <player>
     */

    @Override
    public void onCommand(CommandSender commandSender, String[] args) {
        if(commandSender.hasPermission("islandcore.admin")){
            if(args.length>2){
                Player player = Bukkit.getPlayer(args[2]);
                Gui gui = guiManager.getShop(args[1]);
                if(player != null && gui != null){
                    if(!gui.open(player)){
                        commandSender.sendMessage("&c至少要有一位玩家在島嶼上才可使用此指令");
                        return;
                    };
                }
                if(gui == null){
                    commandSender.sendMessage("找不到該選單");
                }
            }
        }
    }

}
