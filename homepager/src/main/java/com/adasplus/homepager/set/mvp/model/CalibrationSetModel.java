package com.adasplus.homepager.set.mvp.model;


import com.adasplus.homepager.set.mvp.contract.ICalibrationSetContract;

/**
 * Author:刘净辉
 * Date : 2019/9/26 19:34
 * Description :
 */
public class CalibrationSetModel implements ICalibrationSetContract.Model {

    @Override
    public String toString() {
        return "CalibrationSetModel{" +
                "autoReferenceLineEnable=" + autoReferenceLineEnable +
                ", manualReferenceLineEnable=" + manualReferenceLineEnable +
                ", stepValue=" + stepValue +
                ", cmd='" + cmd + '\'' +
                '}';
    }

    /**
     * autoReferenceLineEnable : 1
     * manualReferenceLineEnable : 0
     * cameraHight : 20
     * stepValue : 20
     * cmd : up
     */

    private int autoReferenceLineEnable;
    private int manualReferenceLineEnable;
    private int stepValue;
    private String cmd;

    public int getAutoReferenceLineEnable() {
        return autoReferenceLineEnable;
    }

    public void setAutoReferenceLineEnable(int autoReferenceLineEnable) {
        this.autoReferenceLineEnable = autoReferenceLineEnable;
    }

    public int getManualReferenceLineEnable() {
        return manualReferenceLineEnable;
    }

    public void setManualReferenceLineEnable(int manualReferenceLineEnable) {
        this.manualReferenceLineEnable = manualReferenceLineEnable;
    }

    public int getStepValue() {
        return stepValue;
    }

    public void setStepValue(int stepValue) {
        this.stepValue = stepValue;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }
}
