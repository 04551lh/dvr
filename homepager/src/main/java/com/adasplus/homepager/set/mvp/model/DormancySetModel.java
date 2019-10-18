package com.adasplus.homepager.set.mvp.model;


import com.adasplus.homepager.set.mvp.contract.IDormancySetContract;

/**
 * Author:刘净辉
 * Date : 2019/9/26 16:02
 * Description :
 */
public class DormancySetModel implements IDormancySetContract.Model {

    private int timeoutValue;

    public int getTimeoutValue() {
        return timeoutValue;
    }

    public void setTimeoutValue(int timeoutValue) {
        this.timeoutValue = timeoutValue;
    }

    @Override
    public String toString() {
        return "DormancySetModel{" +
                "timeoutValue=" + timeoutValue +
                '}';
    }
}
