package com.adasplus.homepager.set.mvp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by dell on 2019/11/15 16:30
 * Description:
 * Emain: 1187278976@qq.com
 */
public class CameraSetModel implements Serializable {

    /**
     * switch : 0
     */

    @SerializedName("switch")
    private int switchX;

    @Override
    public String toString() {
        return "CameraSetModel{" +
                "switchX=" + switchX +
                '}';
    }

    public int getSwitchX() {
        return switchX;
    }

    public void setSwitchX(int switchX) {
        this.switchX = switchX;
    }
}
