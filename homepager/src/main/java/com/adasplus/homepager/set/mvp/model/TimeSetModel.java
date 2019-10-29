package com.adasplus.homepager.set.mvp.model;


import com.adasplus.homepager.set.mvp.contract.ITimeSetContract;

/**
 * Author:刘净辉
 * Date : 2019/9/26 15:17
 * Description :
 */
public class TimeSetModel implements ITimeSetContract.Model {


    @Override
    public String toString() {
        return "TimeSetModel{" +
                "timeCalibration=" + timeCalibration +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    /**
     * timeCalibration : 1
     * date : 2019-09-24
     * time : 14:32:14
     */

    private int timeCalibration;
    private String date;
    private String time;

    public int getTimeCalibration() {
        return timeCalibration;
    }

    public void setTimeCalibration(int timeCalibration) {
        this.timeCalibration = timeCalibration;
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
}
