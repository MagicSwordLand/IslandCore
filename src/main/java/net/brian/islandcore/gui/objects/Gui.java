package net.brian.islandcore.gui.objects;


import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import dev.reactant.reactant.core.component.lifecycle.LifeCycleHook;
import dev.reactant.reactant.core.dependency.injection.Inject;
import io.github.clayclaw.clawlib.iridiumcolorapi.IridiumColorAPI;
import net.brian.islandcore.data.IslandDataService;
import net.brian.islandcore.gui.GuiManager;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import world.bentobox.bentobox.BentoBox;
import world.bentobox.bentobox.api.user.User;
import world.bentobox.bentobox.database.objects.Island;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.UUID;

public abstract class Gui implements Listener, LifeCycleHook {


    @Inject
    protected GuiManager guiManager;
    @Inject
    protected IslandDataService islandDataService;

    private final HashMap<HumanEntity, ActiveGui> viewers = new HashMap<>();
    protected LinkedHashMap<String, GuiItem> guiItems = new LinkedHashMap<>();

    private final String id;


    public Gui(String id){
        this.id = id;
    }

    @Override
    public void onEnable(){
        guiManager.registerShop(this);
    }

    protected abstract Inventory createGui(Player player,String islandUUID);


    @EventHandler
    public void onClick(InventoryClickEvent event){
        if(viewers.containsKey(event.getWhoClicked())){
            event.setCancelled(true);
            ItemStack itemStack = event.getCurrentItem();
            if(itemStack == null) return;
            if(event.getClickedInventory().getType().equals(InventoryType.CHEST)){
                String name = itemStack.getItemMeta().getDisplayName();
                GuiItem guiItem = guiItems.get(name);
                ActiveGui activeGui = viewers.get(event.getWhoClicked());
                if(guiItem != null){
                    guiItem.process(itemStack,event.getWhoClicked(), activeGui.islandUUID());
                }
                else{
                    clicked(event.getWhoClicked(),activeGui,event.getSlot());
                }
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event){
        viewers.remove(event.getPlayer());
    }

    public boolean open(Player player){
        Island island = BentoBox.getInstance().getIslands().getIsland(islandDataService.getWorld(), User.getInstance(player));
        if(island == null) return false;
        String islandUUID = island.getUniqueId();
        if(islandDataService.isLoaded(island)){
            Inventory inv = createGui(player,islandUUID);
            player.openInventory(inv);
            viewers.put(player,new ActiveGui(islandUUID,inv));
            return true;
        }
        return false;
    }

    public String getId() {
        return id;
    }

    protected void registerItem(GuiItem guiItem){
        guiItems.put(guiItem.getName(), guiItem);
    }

    protected abstract void clicked(HumanEntity player,ActiveGui activeGui,int slot);

    protected ItemStack createItem(String name, Material material, String[] lore){
        return createItem(name,material,lore,0);
    }

    protected ItemStack createItem(String name, Material material, String[] lore,int modelData){
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(IridiumColorAPI.process(name));
        itemMeta.setLore(IridiumColorAPI.process(Arrays.stream(lore).toList()));
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.setCustomModelData(modelData);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}
