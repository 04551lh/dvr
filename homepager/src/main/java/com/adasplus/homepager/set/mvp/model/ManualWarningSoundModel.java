package com.adasplus.homepager.set.mvp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dell on 2019/11/29 19:01
 * Description:
 * Emain: 1187278976@qq.com
 */
public class ManualWarningSoundModel {

    /**
     * switchStatus : 1
     * do : 0
     */

    private int switchStatus;
    @SerializedName("do")
    private int doX;

    public int getSwitchStatus() {
        return switchStatus;
    }

    public void setSwitchStatus(int switchStatus) {
        this.switchStatus = switchStatus;
    }

    public int getDoX() {
        return doX;
    }

    public void setDoX(int doX) {
        this.doX = doX;
    }
}
