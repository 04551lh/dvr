package com.adasplus.homepager.activate.mvp.model;

import java.util.List;

/**
 * Author:刘净辉
 * Date : 2019/9/16 11:05
 */
public class LicensePlateColorModel {

    private List<CarColorModel> car_color;

    public List<CarColorModel> getCar_color() {
        return car_color;
    }

    public void setCar_color(List<CarColorModel> car_color) {
        this.car_color = car_color;
    }

    @Override
    public String toString() {
        return "LicensePlateColorModel{" +
                "car_color=" + car_color +
                '}';
    }
}
