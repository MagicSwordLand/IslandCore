package net.brian.islandcore.crop.objects;

public class Compost {

    double amount;
    long timeStamp,duration;

    double totalHours;

    public Compost(double amount , double hours){
        timeStamp = System.currentTimeMillis();
        this.amount = amount;
        this.duration = (long) (hours*3600000);
        totalHours = hours;
    }

    public boolean expired(){
        return System.currentTimeMillis()-timeStamp>duration;
    }

    public double getAmount(){
        if(expired()){
            return amount*duration/(System.currentTimeMillis()-timeStamp);
        }
        else return amount;
    }

    public double getTotalHours(){
        return totalHours;
    }

}
