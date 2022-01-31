package net.brian.islandcore.crop.config;

import io.github.clayclaw.clawlibrary.java.ColorUtil;

public class MessageConfig {

    String alreadyHasCrop = "已經有作物";
    String exceedMax = "已達上限 $1 / $1";
    String notUnlocked = "尚未解鎖 $1";
    String dataLoading = "資料載入中";
    String dataError = "此作物資料出錯 作物id:$1 可回報給管理員";
    String addedCompost = "你幫此作物加了 $1 肥料";

    public String getAlreadyHasCrop() {
        return alreadyHasCrop;
    }

    public String getExceedMax(int max) {
        return exceedMax.replace("$1",String.valueOf(max));
    }

    public String getNotUnlocked(String crop) {
        return notUnlocked.replace("$1",crop);
    }

    public String getDataError(String type) {
        return dataError.replace("$1",type);
    }

    public String getAddedCompost(String compost) {
        return addedCompost.replace("$1",compost);
    }

    public void setColors(){
        alreadyHasCrop = ColorUtil.translateColor(alreadyHasCrop);
        exceedMax = ColorUtil.translateColor(exceedMax);
        notUnlocked = ColorUtil.translateColor(notUnlocked);
        dataError = ColorUtil.translateColor(dataError);
        dataLoading = ColorUtil.translateColor(dataLoading);
        addedCompost = ColorUtil.translateColor(addedCompost);
    }


    public String getDataLoading() {
        return dataLoading;
    }
}
