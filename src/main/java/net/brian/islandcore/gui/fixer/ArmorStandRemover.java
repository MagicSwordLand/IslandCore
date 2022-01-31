package net.brian.islandcore.gui.fixer;

import dev.reactant.reactant.core.component.Component;
import jdk.jfr.Enabled;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

@Component
public class ArmorStandRemover implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEntityEvent event){
        if(event.getPlayer().hasPermission("islandcore.admin")){
            ItemStack itemStack = event.getPlayer().getInventory().getItemInMainHand();
            if(itemStack.getType().equals(Material.ANVIL)){
                if(event.getRightClicked() instanceof ArmorStand){
                    event.getPlayer().sendMessage("removed");
                    event.getRightClicked().remove();
                }
            }
        }
    }
}
