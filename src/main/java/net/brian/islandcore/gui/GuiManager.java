package net.brian.islandcore.gui;

import dev.reactant.reactant.core.component.Component;
import dev.reactant.reactant.core.component.lifecycle.LifeCycleHook;
import net.brian.islandcore.gui.objects.Gui;

import java.util.HashMap;

@Component
public class GuiManager implements LifeCycleHook {


    private final HashMap<String, Gui> guis = new HashMap<>();


    @Override
    public void onEnable(){

    }




    public Gui getShop(String id){
        return guis.get(id);
    }

    public void registerShop(Gui gui){
        guis.put(gui.getId(), gui);
    }

}
