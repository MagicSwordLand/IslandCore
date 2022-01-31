package net.brian.islandcore.gui.guis;

import dev.reactant.reactant.core.component.Component;
import dev.reactant.reactant.core.dependency.injection.Inject;
import net.brian.islandcore.crop.IslandCropService;
import net.brian.islandcore.crop.objects.IslandCropProfile;
import net.brian.islandcore.gui.objects.ActiveGui;
import net.brian.islandcore.gui.objects.Gui;
import net.brian.mythicpet.util.IridiumColorAPI;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Component
public class CropsGui extends Gui {


    ItemStack tomatoIcon,tomatoLocked;
    ItemStack notAvailable;
    ItemStack backGround;
    @Inject
    IslandCropService cropService;


    public CropsGui() {
        super("crops");
        backGround = createItem("",Material.LIME_STAINED_GLASS_PANE,new String[]{});
        notAvailable = createItem("&8尚未推出",Material.BLACK_STAINED_GLASS_PANE,new String[]{});
        tomatoIcon = createItem("&c&l番茄", Material.BLUE_DYE,new String[]{
                "&7● 成熟時長 &7 小時",
                "&7● 空間 &f1 &7地力",
                "&7● 價值 &f2 \uF8E1",
                "&7● 右鍵收割",
                "",
                "&x&f&f&e&c&b&7茄科茄屬的一種植物",
                "&x&f&f&e&c&b&7可提供少許血量加成"
        },1);
        tomatoLocked = createItem("&7&l尚未解鎖",Material.GRAY_DYE,new String[]{
                "",
                "&e&l需求: ",
                "  &f無"
        });
    }

    @Override
    public Inventory createGui(Player player, String islandUUID) {
        IslandCropProfile cropProfile = cropService.getProfile(player);
        Inventory inventory = Bukkit.createInventory(null,54,"§x§f§a§c§b§3§d§l作物百科全書");
        if(cropProfile == null) return inventory;
        for(int i =0;i<54;i++) inventory.setItem(i,backGround);
        if(cropProfile.hasUnlocked("tomato")) inventory.setItem(49,tomatoIcon);
        else inventory.setItem(49,tomatoLocked);
        inventory.setItem(39,notAvailable);
        inventory.setItem(41,notAvailable);
        inventory.setItem(29,notAvailable);
        inventory.setItem(31,notAvailable);
        inventory.setItem(33,notAvailable);
        inventory.setItem(19,notAvailable);
        inventory.setItem(21,notAvailable);
        inventory.setItem(23,notAvailable);
        inventory.setItem(25,notAvailable);
        inventory.setItem(9,notAvailable);
        inventory.setItem(11,notAvailable);
        inventory.setItem(13,notAvailable);
        inventory.setItem(15,notAvailable);
        inventory.setItem(17,notAvailable);
        return inventory;
    }

    @Override
    public void clicked(HumanEntity player, ActiveGui activeGui, int slot) {
        IslandCropProfile cropProfile = cropService.getProfile((Player) player);
        if(cropProfile == null) return;
        if(slot == 49 && !cropProfile.hasUnlocked("tomato")){
            cropProfile.unlock("tomato");
            activeGui.inventory().setItem(49,tomatoIcon);
            player.sendMessage("§x§f§a§c§b§3§d§l作物系統 §f>> 該島嶼成功解鎖了§c番茄");
            player.playSound(Sound.sound(Key.key("block.note_block.pling"), Sound.Source.AMBIENT,1,2));
            return;
        }
    }



    private boolean checkAmount(String name,Player player,int amount){
        name = IridiumColorAPI.process(name);
        int size = player.getInventory().getSize();
        int count = 0;
        for(int i=0;i<size;i++ ){
            ItemStack itemStack = player.getInventory().getItem(i);
            if(itemStack != null){
                if(itemStack.hasItemMeta()){
                    if(itemStack.getItemMeta().hasDisplayName()){
                        if(itemStack.getItemMeta().getDisplayName().equals(name)){
                            count+=itemStack.getAmount();
                        }
                    }
                }
            }
        }
        return count>=amount;
    }

}
