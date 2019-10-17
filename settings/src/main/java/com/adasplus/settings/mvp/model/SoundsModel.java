package com.adasplus.settings.mvp.model;

import com.adasplus.settings.mvp.contract.ISoundsContract;

/**
 * Author:刘净辉
 * Date : 2019/9/26 15:48
 * Description :
 */
public class SoundsModel implements ISoundsContract.Model {


    private int soundValue;

    public int getSoundValue() {
        return soundValue;
    }

    public void setSoundValue(int soundValue) {
        this.soundValue = soundValue;
    }

    @Override
    public String toString() {
        return "SoundsModel{" +
                "soundValue=" + soundValue +
                '}';
    }
}
