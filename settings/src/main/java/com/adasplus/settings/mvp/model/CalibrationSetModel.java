package com.adasplus.settings.mvp.model;

import com.adasplus.settings.mvp.contract.ICalibrationSetContract;

/**
 * Author:刘净辉
 * Date : 2019/9/26 19:34
 * Description :
 */
public class CalibrationSetModel implements ICalibrationSetContract.Model {

    private int autoReferenceLineEnable;
    private int manualReferenceLineEnable;
    private int cameraHight;
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

    public int getCameraHight() {
        return cameraHight;
    }

    public void setCameraHight(int cameraHight) {
        this.cameraHight = cameraHight;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    @Override
    public String toString() {
        return "CalibrationSetModel{" +
                "autoReferenceLineEnable=" + autoReferenceLineEnable +
                ", manualReferenceLineEnable=" + manualReferenceLineEnable +
                ", cameraHight=" + cameraHight +
                ", cmd='" + cmd + '\'' +
                '}';
    }
}
