package net.brian.islandcore.crop.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CropLevelConfig {

    List<Long> harvestAmount = new ArrayList<>();


    public int getLevel(long amount){
        int level = 1;
        for (long integer : harvestAmount) {
            if(amount >= integer){
                level ++;
            }
            else{
                return level;
            }
        }
        return level;
    }

    public long getLevelRequire(int level){
        return harvestAmount.get(level-1);
    }

}
