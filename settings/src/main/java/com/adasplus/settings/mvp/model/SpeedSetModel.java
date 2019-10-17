package com.adasplus.settings.mvp.model;

import com.adasplus.settings.mvp.contract.ISpeedSetContract;

/**
 * Author:刘净辉
 * Date : 2019/9/25 11:32
 * Description :
 */
public class SpeedSetModel implements ISpeedSetContract.Model {

    private PulseSpeedBean pulseSpeed;
    private SimulateSpeedBean simulateSpeed;


    public PulseSpeedBean getPulseSpeed() {
        return pulseSpeed;
    }

    public void setPulseSpeed(PulseSpeedBean pulseSpeed) {
        this.pulseSpeed = pulseSpeed;
    }

    public SimulateSpeedBean getSimulateSpeed() {
        return simulateSpeed;
    }

    public void setSimulateSpeed(SimulateSpeedBean simulateSpeed) {
        this.simulateSpeed = simulateSpeed;
    }

    @Override
    public String toString() {
        return "SpeedSetModel{" +
                "pulseSpeed=" + pulseSpeed +
                ", simulateSpeed=" + simulateSpeed +
                '}';
    }

    public static class PulseSpeedBean {


        private int enable;
        private int pulseCoefficient;
        private int autoCalibration;
        private int allowErrorValue;

        public int getEnable() {
            return enable;
        }

        public void setEnable(int enable) {
            this.enable = enable;
        }

        public int getPulseCoefficient() {
            return pulseCoefficient;
        }

        public void setPulseCoefficient(int pulseCoefficient) {
            this.pulseCoefficient = pulseCoefficient;
        }

        public int getAutoCalibration() {
            return autoCalibration;
        }

        public void setAutoCalibration(int autoCalibration) {
            this.autoCalibration = autoCalibration;
        }

        public int getAllowErrorValue() {
            return allowErrorValue;
        }

        public void setAllowErrorValue(int allowErrorValue) {
            this.allowErrorValue = allowErrorValue;
        }

        @Override
        public String toString() {
            return "PulseSpeedBean{" +
                    "enable=" + enable +
                    ", pulseCoefficient=" + pulseCoefficient +
                    ", autoCalibration=" + autoCalibration +
                    ", allowErrorValue=" + allowErrorValue +
                    '}';
        }
    }

    public static class SimulateSpeedBean {


        private int enable;
        private int value;

        public int getEnable() {
            return enable;
        }

        public void setEnable(int enable) {
            this.enable = enable;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "SimulateSpeedBean{" +
                    "enable=" + enable +
                    ", value=" + value +
                    '}';
        }
    }
}
