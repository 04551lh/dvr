package com.adasplus.homepager.set.mvp.model;

/**
 * Author:刘净辉
 * Date : 2019/9/30 11:57
 * Description :
 */
public class ConvertWarningsModel {

    private int warningNameResId;
    private int enable;
    private int sensitivityLevel;
    private int lowestSpeed;

    public ConvertWarningsModel(int warningNameResId, int enable, int sensitivityLevel, int lowestSpeed) {
        this.warningNameResId = warningNameResId;
        this.enable = enable;
        this.sensitivityLevel = sensitivityLevel;
        this.lowestSpeed = lowestSpeed;
    }

    public int getWarningNameResId() {
        return warningNameResId;
    }

    public void setWarningNameResId(int warningNameResId) {
        this.warningNameResId = warningNameResId;
    }

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
        return "ConvertWarningsModel{" +
                "warningNameResId=" + warningNameResId +
                ", enable=" + enable +
                ", sensitivityLevel=" + sensitivityLevel +
                ", lowestSpeed=" + lowestSpeed +
                '}';
    }
}
