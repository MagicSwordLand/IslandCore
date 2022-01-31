package net.brian.islandcore.gui.guis;


import dev.reactant.reactant.core.component.Component;
import dev.reactant.reactant.core.component.lifecycle.LifeCycleHook;
import dev.reactant.reactant.core.dependency.injection.Inject;
import dev.reactant.reactant.extra.config.type.SharedConfig;
import io.github.clayclaw.clawlibrary.reloader.ReloaderComponent;
import net.brian.islandcore.crop.IslandCropService;
import net.brian.islandcore.crop.config.FarmlandPriceConfig;
import net.brian.islandcore.crop.objects.IslandCropProfile;
import net.brian.islandcore.economy.IslandEconomyService;
import net.brian.islandcore.economy.object.IslandEconomyProfile;
import net.brian.islandcore.gui.objects.ActiveGui;
import net.brian.islandcore.gui.objects.Gui;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


@Component
public class FarmlandShop extends Gui implements ReloaderComponent, LifeCycleHook {

    @Inject
    IslandCropService cropService;

    @Inject
    IslandEconomyService economyService;

    @Inject(name = "plugins/IslandFarming/LandPrice.json")
    SharedConfig<FarmlandPriceConfig> config;

    public FarmlandShop() {
        super("farmland");
    }

    @Override
    protected Inventory createGui(Player player, String islandUUID) {
        IslandCropProfile cropProfile = cropService.getProfile(islandUUID);
        int farmlandAmount = cropProfile.getCropLimit();
        Inventory inventory = Bukkit.createInventory(null,54,"§e島嶼地力購買");
        for(int i=1;i<=45;i++){
            ItemStack itemStack;
            if(farmlandAmount+1<i){
                itemStack = createItem("&c地力等級 "+i,Material.RED_STAINED_GLASS_PANE,new String[]{"&c未解鎖"});
            }
            else if(farmlandAmount+1==i){
                itemStack = createItem("&e地力等級 "+i,Material.YELLOW_STAINED_GLASS_PANE,new String[]{"&7 價格 "+price[i-1]+" "+'\uf8E1'});
            }
            else{
                itemStack = createItem("&a地力等級 "+i,Material.GREEN_STAINED_GLASS_PANE,new String[]{"&a已解鎖"});
            }
            inventory.setItem(i-1,itemStack);
        }
        return inventory;
    }

    @Override
    protected void clicked(HumanEntity player, ActiveGui activeGui, int slot) {
        if(slot<45){
            IslandCropProfile cropProfile = cropService.getProfile(activeGui.islandUUID());
            IslandEconomyProfile economyProfile = economyService.getProfile(activeGui.islandUUID());
            int amount = slot+1;
            if(amount == 1+cropProfile.getCropLimit()){
                if(economyProfile.getMoney() >= price[slot]) {
                    player.playSound(Sound.sound(Key.key("block.note_block.pling"), Sound.Source.AMBIENT,1,2));
                    economyProfile.give(-price[slot]);
                    cropProfile.increaseLimit(1);
                    activeGui.inventory().setItem(slot,createItem("&a地力等級 "+amount,Material.GREEN_STAINED_GLASS_PANE,new String[]{"&a已解鎖"}));
                    if(amount +1<45){
                        activeGui.inventory().setItem(slot+1,createItem("&e地力等級 "+(amount+1),Material.YELLOW_STAINED_GLASS_PANE,new String[]{"&e價格 "+price[slot+1]+" "+'\uf8E1'}));
                    }
                }
                else {
                    player.playSound(Sound.sound(Key.key("block.note_block.pling"), Sound.Source.AMBIENT,1,0.1f));
                    player.sendMessage("§c雲幣不足");
                }
            }
        }
    }

    private int[] price ;

    @Override
    public void onEnable(){
        super.onEnable();
        price = config.getContent().price;
    }

    @Override
    public void reload() {
        config.refresh().blockingAwait();;
        price = config.getContent().price;
    }
}
