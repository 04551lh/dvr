package com.adasplus.settings.mvp.model;


import com.adasplus.settings.mvp.contract.IParamsSetContract;

/**
 * Author:刘净辉
 * Date : 2019/9/29 18:46
 * Description :
 */
public class ParamsSetModel implements IParamsSetContract.Model {

    //保险杠距离
    private int bumperDistance;
    //左车轮距离
    private int leftWheelDistance;
    //右车轮距离
    private int rightWheelDistance;
    //前车轮距离
    private int frontWheelDistance;

    public int getBumperDistance() {
        return bumperDistance;
    }

    public void setBumperDistance(int bumperDistance) {
        this.bumperDistance = bumperDistance;
    }

    public int getLeftWheelDistance() {
        return leftWheelDistance;
    }

    public void setLeftWheelDistance(int leftWheelDistance) {
        this.leftWheelDistance = leftWheelDistance;
    }

    public int getRightWheelDistance() {
        return rightWheelDistance;
    }

    public void setRightWheelDistance(int rightWheelDistance) {
        this.rightWheelDistance = rightWheelDistance;
    }

    public int getFrontWheelDistance() {
        return frontWheelDistance;
    }

    public void setFrontWheelDistance(int frontWheelDistance) {
        this.frontWheelDistance = frontWheelDistance;
    }

    @Override
    public String toString() {
        return "ParamsSetModel{" +
                "bumperDistance=" + bumperDistance +
                ", leftWheelDistance=" + leftWheelDistance +
                ", rightWheelDistance=" + rightWheelDistance +
                ", frontWheelDistance=" + frontWheelDistance +
                '}';
    }
}
