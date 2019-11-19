package com.adasplus.homepager.set.mvp.model;


import com.adasplus.homepager.set.mvp.contract.ISoundsContract;

/**
 * Author:刘净辉
 * Date : 2019/9/26 15:48
 * Description :
 */
public class SoundsModel implements ISoundsContract.Model {


    private int soundValue;

    public int getSoundType() {
        return soundType;
    }

    public void setSoundType(int soundType) {
        this.soundType = soundType;
    }

    private int soundType;

    public int getSoundValue() {
        return soundValue;
    }

    @Override
    public String toString() {
        return "SoundsModel{" +
                "soundValue=" + soundValue +
                ", soundType=" + soundType +
                '}';
    }

    public void setSoundValue(int soundValue) {
        this.soundValue = soundValue;
    }
}
