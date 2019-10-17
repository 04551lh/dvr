package com.adasplus.basicinfo.mvp.model;

import com.adasplus.basicinfo.mvp.contract.IDriverInfoContract;

/**
 * Author:刘净辉
 * Date : 2019/10/11 18:53
 * Description :
 */
public class DriverInfoModel implements IDriverInfoContract.Model {

    private String name; //姓名
    private int sex; //性别
    private String Id; //身份证号
    private String drivingLicenseId; //驾驶证号
    private String drivingLicenseValidTerm; //驾驶证有效期
    private String qualificationCertificateId; //从业资格证号
    private String affiliate; //发证机构
    private String bindVehiclePlate; //绑定车辆

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getDrivingLicenseId() {
        return drivingLicenseId;
    }

    public void setDrivingLicenseId(String drivingLicenseId) {
        this.drivingLicenseId = drivingLicenseId;
    }

    public String getDrivingLicenseValidTerm() {
        return drivingLicenseValidTerm;
    }

    public void setDrivingLicenseValidTerm(String drivingLicenseValidTerm) {
        this.drivingLicenseValidTerm = drivingLicenseValidTerm;
    }

    public String getQualificationCertificateId() {
        return qualificationCertificateId;
    }

    public void setQualificationCertificateId(String qualificationCertificateId) {
        this.qualificationCertificateId = qualificationCertificateId;
    }

    public String getAffiliate() {
        return affiliate;
    }

    public void setAffiliate(String affiliate) {
        this.affiliate = affiliate;
    }

    public String getBindVehiclePlate() {
        return bindVehiclePlate;
    }

    public void setBindVehiclePlate(String bindVehiclePlate) {
        this.bindVehiclePlate = bindVehiclePlate;
    }

    @Override
    public String toString() {
        return "DriverInfoModel{" +
                "name='" + name + '\'' +
                ", sex=" + sex +
                ", id='" + Id + '\'' +
                ", drivingLicenseId='" + drivingLicenseId + '\'' +
                ", drivingLicenseValidTerm='" + drivingLicenseValidTerm + '\'' +
                ", qualificationCertificateId='" + qualificationCertificateId + '\'' +
                ", affiliate='" + affiliate + '\'' +
                ", bindVehiclePlate='" + bindVehiclePlate + '\'' +
                '}';
    }
}
