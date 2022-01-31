package net.brian.islandcore.economy.object;

import net.brian.islandcore.data.objects.IslandData;

public class IslandEconomyProfile extends IslandData {

    private int money = 0;

    public IslandEconomyProfile(String uuid){
        super(uuid);
    }


    public int getMoney(){
        return money;
    }

    public void give(int amount){
        if(money + amount < 0){
            money = 0;
        }
        else{
            money += amount;
        }
    }

    public void setMoney(int amount){
        money = Math.max(amount, 0);
    }
}
