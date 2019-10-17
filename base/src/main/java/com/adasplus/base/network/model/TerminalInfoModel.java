package com.adasplus.base.network.model;

/**
 * Author:刘净辉
 * Date : 2019/10/10 17:20
 * Description :
 */
public class TerminalInfoModel {

    private String phoneNumber;
    private String terminalId;
    private String plateNumber;
    private String plateColor;
    private String vin;
    private String provincialDomain;
    private String cityDomain;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getPlateColor() {
        return plateColor;
    }

    public void setPlateColor(String plateColor) {
        this.plateColor = plateColor;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getProvincialDomain() {
        return provincialDomain;
    }

    public void setProvincialDomain(String provincialDomain) {
        this.provincialDomain = provincialDomain;
    }

    public String getCityDomain() {
        return cityDomain;
    }

    public void setCityDomain(String cityDomain) {
        this.cityDomain = cityDomain;
    }

    @Override
    public String toString() {
        return "TerminalInfoModel{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", terminalId='" + terminalId + '\'' +
                ", plateNumber='" + plateNumber + '\'' +
                ", plateColor='" + plateColor + '\'' +
                ", vin='" + vin + '\'' +
                ", provincialDomain='" + provincialDomain + '\'' +
                ", cityDomain='" + cityDomain + '\'' +
                '}';
    }
}
