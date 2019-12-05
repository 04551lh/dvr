package com.adasplus.homepager.set.mvp.model;

import java.io.Serializable;

/**
 * Created by dell on 2019/12/4 11:07
 * Description:
 * Emain: 1187278976@qq.com
 */
public class AlarmTTSIntervalSwitchModel implements Serializable {


    /**
     * smokingAndCallPhoneSwitchEnable : 0
     * cameraErrorAndNoDriverSwitchEnable : 0
     * returnDefaultSwitchEnable : 0
     */

    private int smokingAndCallPhoneSwitchEnable;
    private int cameraErrorAndNoDriverSwitchEnable;
    private int returnDefaultSwitchEnable;

    public int getSmokingAndCallPhoneSwitchEnable() {
        return smokingAndCallPhoneSwitchEnable;
    }

    public void setSmokingAndCallPhoneSwitchEnable(int smokingAndCallPhoneSwitchEnable) {
        this.smokingAndCallPhoneSwitchEnable = smokingAndCallPhoneSwitchEnable;
    }

    public int getCameraErrorAndNoDriverSwitchEnable() {
        return cameraErrorAndNoDriverSwitchEnable;
    }

    public void setCameraErrorAndNoDriverSwitchEnable(int cameraErrorAndNoDriverSwitchEnable) {
        this.cameraErrorAndNoDriverSwitchEnable = cameraErrorAndNoDriverSwitchEnable;
    }

    public int getReturnDefaultSwitchEnable() {
        return returnDefaultSwitchEnable;
    }

    public void setReturnDefaultSwitchEnable(int returnDefaultSwitchEnable) {
        this.returnDefaultSwitchEnable = returnDefaultSwitchEnable;
    }
}
