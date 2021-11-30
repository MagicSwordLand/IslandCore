package net.brian.islandcore.crop.crops;

import net.Indyuce.mmoitems.MMOItems;
import net.brian.islandcore.crop.IslandCropService;
import net.brian.islandcore.crop.objects.ActiveCrop;
import net.brian.islandcore.crop.objects.CropDrop;
import org.bukkit.Location;

public abstract class RightClickCrop extends IslandCrop{


   public abstract void onRightClick(ActiveCrop crop);


   protected void rightClickDrop(ActiveCrop crop, CropDrop cropDrop, int minusAmount){
      if(crop.getStage() >= getMaxStage()){
         crop.downAge(minusAmount);
         Location location = crop.getLocation().getLocation();;
         location.getWorld().dropItem(location, cropDrop.getItem());
         crop.getIsland().addCount(crop.getCropType().id);
      }
   }

}
