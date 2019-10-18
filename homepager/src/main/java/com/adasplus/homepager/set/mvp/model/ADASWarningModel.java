package com.adasplus.homepager.set.mvp.model;


import com.adasplus.homepager.set.mvp.contract.IADASWarningContract;

/**
 * Author:刘净辉
 * Date : 2019/9/26 18:12
 * Description :
 */
public class ADASWarningModel implements IADASWarningContract.Model {

    private int adasEnable;
    private FrontCarCollisionAlarmBean frontCarCollisionAlarm;
    private NearDistanceAlarmBean nearDistanceAlarm;
    private PedestrianCollisionAlarmBean pedestrianCollisionAlarm;
    private VehicleLaneDeviateAlarmBean vehicleLaneDeviateAlarm;
    private FrequentlyChangeLaneAlarmBean frequentlyChangeLaneAlarm;
    private LaneFlagDistinguishBean laneFlagDistinguish;

    public int getAdasEnable() {
        return adasEnable;
    }

    public void setAdasEnable(int adasEnable) {
        this.adasEnable = adasEnable;
    }

    public FrontCarCollisionAlarmBean getFrontCarCollisionAlarm() {
        return frontCarCollisionAlarm;
    }

    public void setFrontCarCollisionAlarm(FrontCarCollisionAlarmBean frontCarCollisionAlarm) {
        this.frontCarCollisionAlarm = frontCarCollisionAlarm;
    }

    public NearDistanceAlarmBean getNearDistanceAlarm() {
        return nearDistanceAlarm;
    }

    public void setNearDistanceAlarm(NearDistanceAlarmBean nearDistanceAlarm) {
        this.nearDistanceAlarm = nearDistanceAlarm;
    }

    public PedestrianCollisionAlarmBean getPedestrianCollisionAlarm() {
        return pedestrianCollisionAlarm;
    }

    public void setPedestrianCollisionAlarm(PedestrianCollisionAlarmBean pedestrianCollisionAlarm) {
        this.pedestrianCollisionAlarm = pedestrianCollisionAlarm;
    }

    public VehicleLaneDeviateAlarmBean getVehicleLaneDeviateAlarm() {
        return vehicleLaneDeviateAlarm;
    }

    public void setVehicleLaneDeviateAlarm(VehicleLaneDeviateAlarmBean vehicleLaneDeviateAlarm) {
        this.vehicleLaneDeviateAlarm = vehicleLaneDeviateAlarm;
    }

    public FrequentlyChangeLaneAlarmBean getFrequentlyChangeLaneAlarm() {
        return frequentlyChangeLaneAlarm;
    }

    public void setFrequentlyChangeLaneAlarm(FrequentlyChangeLaneAlarmBean frequentlyChangeLaneAlarm) {
        this.frequentlyChangeLaneAlarm = frequentlyChangeLaneAlarm;
    }

    public LaneFlagDistinguishBean getLaneFlagDistinguish() {
        return laneFlagDistinguish;
    }

    public void setLaneFlagDistinguish(LaneFlagDistinguishBean laneFlagDistinguish) {
        this.laneFlagDistinguish = laneFlagDistinguish;
    }

    @Override
    public String toString() {
        return "ADASWarningModel{" +
                "adasEnable=" + adasEnable +
                ", frontCarCollisionAlarm=" + frontCarCollisionAlarm +
                ", nearDistanceAlarm=" + nearDistanceAlarm +
                ", pedestrianCollisionAlarm=" + pedestrianCollisionAlarm +
                ", vehicleLaneDeviateAlarm=" + vehicleLaneDeviateAlarm +
                ", frequentlyChangeLaneAlarm=" + frequentlyChangeLaneAlarm +
                ", laneFlagDistinguish=" + laneFlagDistinguish +
                '}';
    }

    public static class FrontCarCollisionAlarmBean {


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
            return "FrontCarCollisionAlarmBean{" +
                    "enable=" + enable +
                    ", sensitivityLevel=" + sensitivityLevel +
                    ", lowestSpeed=" + lowestSpeed +
                    '}';
        }
    }

    public static class NearDistanceAlarmBean {


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
            return "NearDistanceAlarmBean{" +
                    "enable=" + enable +
                    ", sensitivityLevel=" + sensitivityLevel +
                    ", lowestSpeed=" + lowestSpeed +
                    '}';
        }
    }

    public static class PedestrianCollisionAlarmBean {


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
            return "PedestrianCollisionAlarmBean{" +
                    "enable=" + enable +
                    ", sensitivityLevel=" + sensitivityLevel +
                    ", lowestSpeed=" + lowestSpeed +
                    '}';
        }
    }

    public static class VehicleLaneDeviateAlarmBean {


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
            return "VehicleLaneDeviateAlarmBean{" +
                    "enable=" + enable +
                    ", sensitivityLevel=" + sensitivityLevel +
                    ", lowestSpeed=" + lowestSpeed +
                    '}';
        }
    }

    public static class FrequentlyChangeLaneAlarmBean {


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
            return "FrequentlyChangeLaneAlarmBean{" +
                    "enable=" + enable +
                    ", sensitivityLevel=" + sensitivityLevel +
                    ", lowestSpeed=" + lowestSpeed +
                    '}';
        }
    }

    public static class LaneFlagDistinguishBean {


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
            return "LaneFlagDistinguishBean{" +
                    "enable=" + enable +
                    ", sensitivityLevel=" + sensitivityLevel +
                    ", lowestSpeed=" + lowestSpeed +
                    '}';
        }
    }
}
