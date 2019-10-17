package com.adasplus.settings.mvp.model;

import com.adasplus.settings.mvp.contract.IDMSWarningContract;

/**
 * Author:刘净辉
 * Date : 2019/9/26 19:31
 * Description :
 */
public class DMSWarningModel implements IDMSWarningContract.Model {


    private int dmsEnable;
    private LongTimeDriveAlarmBean longTimeDriveAlarm;
    private TakePhoneAlarmBean takePhoneAlarm;
    private SmokingAlarmBean smokingAlarm;
    private DistractAlarmBean distractAlarm;
    private DriverErrorAlarmBean driverErrorAlarm;
    private DriverChangeEventBean driverChangeEvent;


    public int getDmsEnable() {
        return dmsEnable;
    }

    public void setDmsEnable(int dmsEnable) {
        this.dmsEnable = dmsEnable;
    }

    public LongTimeDriveAlarmBean getLongTimeDriveAlarm() {
        return longTimeDriveAlarm;
    }

    public void setLongTimeDriveAlarm(LongTimeDriveAlarmBean longTimeDriveAlarm) {
        this.longTimeDriveAlarm = longTimeDriveAlarm;
    }

    public TakePhoneAlarmBean getTakePhoneAlarm() {
        return takePhoneAlarm;
    }

    public void setTakePhoneAlarm(TakePhoneAlarmBean takePhoneAlarm) {
        this.takePhoneAlarm = takePhoneAlarm;
    }

    public SmokingAlarmBean getSmokingAlarm() {
        return smokingAlarm;
    }

    public void setSmokingAlarm(SmokingAlarmBean smokingAlarm) {
        this.smokingAlarm = smokingAlarm;
    }

    public DistractAlarmBean getDistractAlarm() {
        return distractAlarm;
    }

    public void setDistractAlarm(DistractAlarmBean distractAlarm) {
        this.distractAlarm = distractAlarm;
    }

    public DriverErrorAlarmBean getDriverErrorAlarm() {
        return driverErrorAlarm;
    }

    public void setDriverErrorAlarm(DriverErrorAlarmBean driverErrorAlarm) {
        this.driverErrorAlarm = driverErrorAlarm;
    }

    public DriverChangeEventBean getDriverChangeEvent() {
        return driverChangeEvent;
    }

    public void setDriverChangeEvent(DriverChangeEventBean driverChangeEvent) {
        this.driverChangeEvent = driverChangeEvent;
    }

    @Override
    public String toString() {
        return "DMSWarningModel{" +
                "longTimeDriveAlarm=" + longTimeDriveAlarm +
                ", takePhoneAlarm=" + takePhoneAlarm +
                ", smokingAlarm=" + smokingAlarm +
                ", distractAlarm=" + distractAlarm +
                ", driverErrorAlarm=" + driverErrorAlarm +
                ", driverChangeEvent=" + driverChangeEvent +
                '}';
    }

    public static class LongTimeDriveAlarmBean {

        private int enable;
        private int sensitivityLevel;
        private int lowestSpeed;

        public int getEnable() {
            return enable;
        }

        public void setEnable(int enable) {
            this.enable = enable;
        }

        public int getSensitivityLevel() {
            return sensitivityLevel;
        }

        public void setSensitivityLevel(int sensitivityLevel) {
            this.sensitivityLevel = sensitivityLevel;
        }

        public int getLowestSpeed() {
            return lowestSpeed;
        }

        public void setLowestSpeed(int lowestSpeed) {
            this.lowestSpeed = lowestSpeed;
        }

        @Override
        public String toString() {
            return "LongTimeDriveAlarmBean{" +
                    "enable=" + enable +
                    ", sensitivityLevel=" + sensitivityLevel +
                    ", lowestSpeed=" + lowestSpeed +
                    '}';
        }
    }

    public static class TakePhoneAlarmBean {

        private int enable;
        private int sensitivityLevel;
        private int lowestSpeed;

        public int getEnable() {
            return enable;
        }

        public void setEnable(int enable) {
            this.enable = enable;
        }

        public int getSensitivityLevel() {
            return sensitivityLevel;
        }

        public void setSensitivityLevel(int sensitivityLevel) {
            this.sensitivityLevel = sensitivityLevel;
        }

        public int getLowestSpeed() {
            return lowestSpeed;
        }

        public void setLowestSpeed(int lowestSpeed) {
            this.lowestSpeed = lowestSpeed;
        }

        @Override
        public String toString() {
            return "TakePhoneAlarmBean{" +
                    "enable=" + enable +
                    ", sensitivityLevel=" + sensitivityLevel +
                    ", lowestSpeed=" + lowestSpeed +
                    '}';
        }
    }

    public static class SmokingAlarmBean {


        private int enable;
        private int sensitivityLevel;
        private int lowestSpeed;

        public int getEnable() {
            return enable;
        }

        public void setEnable(int enable) {
            this.enable = enable;
        }

        public int getSensitivityLevel() {
            return sensitivityLevel;
        }

        public void setSensitivityLevel(int sensitivityLevel) {
            this.sensitivityLevel = sensitivityLevel;
        }

        public int getLowestSpeed() {
            return lowestSpeed;
        }

        public void setLowestSpeed(int lowestSpeed) {
            this.lowestSpeed = lowestSpeed;
        }

        @Override
        public String toString() {
            return "SmokingAlarmBean{" +
                    "enable=" + enable +
                    ", sensitivityLevel=" + sensitivityLevel +
                    ", lowestSpeed=" + lowestSpeed +
                    '}';
        }
    }

    public static class DistractAlarmBean {

        private int enable;
        private int sensitivityLevel;
        private int lowestSpeed;

        public int getEnable() {
            return enable;
        }

        public void setEnable(int enable) {
            this.enable = enable;
        }

        public int getSensitivityLevel() {
            return sensitivityLevel;
        }

        public void setSensitivityLevel(int sensitivityLevel) {
            this.sensitivityLevel = sensitivityLevel;
        }

        public int getLowestSpeed() {
            return lowestSpeed;
        }

        public void setLowestSpeed(int lowestSpeed) {
            this.lowestSpeed = lowestSpeed;
        }

        @Override
        public String toString() {
            return "DistractAlarmBean{" +
                    "enable=" + enable +
                    ", sensitivityLevel=" + sensitivityLevel +
                    ", lowestSpeed=" + lowestSpeed +
                    '}';
        }
    }

    public static class DriverErrorAlarmBean {

        private int enable;
        private int sensitivityLevel;
        private int lowestSpeed;

        public int getEnable() {
            return enable;
        }

        public void setEnable(int enable) {
            this.enable = enable;
        }

        public int getSensitivityLevel() {
            return sensitivityLevel;
        }

        public void setSensitivityLevel(int sensitivityLevel) {
            this.sensitivityLevel = sensitivityLevel;
        }

        public int getLowestSpeed() {
            return lowestSpeed;
        }

        public void setLowestSpeed(int lowestSpeed) {
            this.lowestSpeed = lowestSpeed;
        }

        @Override
        public String toString() {
            return "DriverErrorAlarmBean{" +
                    "enable=" + enable +
                    ", sensitivityLevel=" + sensitivityLevel +
                    ", lowestSpeed=" + lowestSpeed +
                    '}';
        }
    }

    public static class DriverChangeEventBean {


        private int enable;
        private int sensitivityLevel;
        private int lowestSpeed;

        public int getEnable() {
            return enable;
        }

        public void setEnable(int enable) {
            this.enable = enable;
        }

        public int getSensitivityLevel() {
            return sensitivityLevel;
        }

        public void setSensitivityLevel(int sensitivityLevel) {
            this.sensitivityLevel = sensitivityLevel;
        }

        public int getLowestSpeed() {
            return lowestSpeed;
        }

        public void setLowestSpeed(int lowestSpeed) {
            this.lowestSpeed = lowestSpeed;
        }

        @Override
        public String toString() {
            return "DriverChangeEventBean{" +
                    "enable=" + enable +
                    ", sensitivityLevel=" + sensitivityLevel +
                    ", lowestSpeed=" + lowestSpeed +
                    '}';
        }
    }
}
