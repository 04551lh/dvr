package com.adasplus.homepager.params.mvp.model;


import com.adasplus.homepager.params.mvp.contract.IParamsSetContract;

/**
 * Author:刘净辉
 * Date : 2019/9/29 18:46
 * Description :
 */
public class ParamsSetModel implements IParamsSetContract.Model {

    public int getAdasCameraHight() {
        return adasCameraHight;
    }

    public void setAdasCameraHight(int adasCameraHight) {
        this.adasCameraHight = adasCameraHight;
    }

    //摄像头高度
    private int adasCameraHight;

    @Override
    public String toString() {
        return "ParamsSetModel{" +
                "adasCameraHight=" + adasCameraHight +
                ", bumperDistance=" + bumperDistance +
                ", leftWheelDistance=" + leftWheelDistance +
                ", rightWheelDistance=" + rightWheelDistance +
                '}';
    }

    //保险杠距离
    private int bumperDistance;
    //左车轮距离
    private int leftWheelDistance;
    //右车轮距离
    private int rightWheelDistance;
    //前车轮距离

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

}
