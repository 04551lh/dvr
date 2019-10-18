package com.adasplus.homepager.activate.mvp.model;

import java.util.List;

/**
 * Author:刘净辉
 * Date : 2019/10/16 15:32
 * Description :
 */
public class CarInfoModel {

    private List<AdministrativeRegionCodeModel> administrativeRegionCodeModelList;
    private List<CarColorModel> carColor;

    public CarInfoModel(List<AdministrativeRegionCodeModel> administrativeRegionCodeModelList, List<CarColorModel> carColor) {
        this.administrativeRegionCodeModelList = administrativeRegionCodeModelList;
        this.carColor = carColor;
    }

    public List<AdministrativeRegionCodeModel> getAdministrativeRegionCodeModelList() {
        return administrativeRegionCodeModelList;
    }

    public void setAdministrativeRegionCodeModelList(List<AdministrativeRegionCodeModel> administrativeRegionCodeModelList) {
        this.administrativeRegionCodeModelList = administrativeRegionCodeModelList;
    }

    public List<CarColorModel> getCarColor() {
        return carColor;
    }

    public void setCarColor(List<CarColorModel> carColor) {
        this.carColor = carColor;
    }

    @Override
    public String toString() {
        return "CarInfoModel{" +
                "administrativeRegionCodeModelList=" + administrativeRegionCodeModelList +
                ", carColor=" + carColor +
                '}';
    }
}
