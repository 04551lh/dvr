package com.adasplus.settings.mvp.model;

import com.adasplus.settings.mvp.contract.ITimeSetContract;

/**
 * Author:刘净辉
 * Date : 2019/9/26 15:17
 * Description :
 */
public class TimeSetModel implements ITimeSetContract.Model {

    //自动校时
    private AutoCalibrationTimeBean autoCalibrationTime;
    //手动校时
    private ManualCalibrationTimeBean manualCalibrationTime;

    public AutoCalibrationTimeBean getAutoCalibrationTime() {
        return autoCalibrationTime;
    }

    public void setAutoCalibrationTime(AutoCalibrationTimeBean autoCalibrationTime) {
        this.autoCalibrationTime = autoCalibrationTime;
    }

    public ManualCalibrationTimeBean getManualCalibrationTime() {
        return manualCalibrationTime;
    }

    public void setManualCalibrationTime(ManualCalibrationTimeBean manualCalibrationTime) {
        this.manualCalibrationTime = manualCalibrationTime;
    }

    @Override
    public String toString() {
        return "TimeSetModel{" +
                "autoCalibrationTime=" + autoCalibrationTime +
                ", manualCalibrationTime=" + manualCalibrationTime +
                '}';
    }

    public static class AutoCalibrationTimeBean {

        // 是否选择了自动校时的按钮 1 : true 0: false
        private int enable;
        //网络校时 1 : true 0:false
        private int calibrationTimeThroughNet;
        //gps 校时 1:true 0:false
        private int calibrationTimeThroughGPS;


        public int getEnable() {
            return enable;
        }

        public void setEnable(int enable) {
            this.enable = enable;
        }

        public int getCalibrationTimeThroughNet() {
            return calibrationTimeThroughNet;
        }

        public void setCalibrationTimeThroughNet(int calibrationTimeThroughNet) {
            this.calibrationTimeThroughNet = calibrationTimeThroughNet;
        }

        public int getCalibrationTimeThroughGPS() {
            return calibrationTimeThroughGPS;
        }

        public void setCalibrationTimeThroughGPS(int calibrationTimeThroughGPS) {
            this.calibrationTimeThroughGPS = calibrationTimeThroughGPS;
        }

        @Override
        public String toString() {
            return "AutoCalibrationTimeBean{" +
                    "enable=" + enable +
                    ", calibrationTimeThroughNet=" + calibrationTimeThroughNet +
                    ", calibrationTimeThroughGPS=" + calibrationTimeThroughGPS +
                    '}';
        }
    }

    public static class ManualCalibrationTimeBean {

        private int enable;
        private String date;
        private String time;

        public int getEnable() {
            return enable;
        }

        public void setEnable(int enable) {
            this.enable = enable;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        @Override
        public String toString() {
            return "ManualCalibrationTimeBean{" +
                    "enable=" + enable +
                    ", date='" + date + '\'' +
                    ", time='" + time + '\'' +
                    '}';
        }
    }
}
