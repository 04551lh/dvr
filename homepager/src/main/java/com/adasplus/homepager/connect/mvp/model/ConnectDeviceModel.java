package com.adasplus.homepager.connect.mvp.model;

import android.net.wifi.ScanResult;
import android.os.Parcel;
import android.os.Parcelable;

import com.adasplus.base.utils.WifiConnectStatus;
import com.adasplus.homepager.connect.mvp.contract.IConnectDeviceContract;

/**
 * Author:刘净辉
 * Date : 2019/9/10 12:26
 */
public class ConnectDeviceModel implements IConnectDeviceContract.Model,Comparable<ConnectDeviceModel>, Parcelable {

    private String wifiName;
    private int level;
    private WifiConnectStatus state; // 未连接 正在连接 已连接
    private String capabilities;
    private ScanResult scanResult;

    public ConnectDeviceModel(){

    }

    private ConnectDeviceModel(Parcel in) {
        wifiName = in.readString();
        level = in.readInt();
        capabilities = in.readString();
        scanResult = in.readParcelable(ScanResult.class.getClassLoader());
    }

    public static final Creator<ConnectDeviceModel> CREATOR = new Creator<ConnectDeviceModel>() {
        @Override
        public ConnectDeviceModel createFromParcel(Parcel in) {
            return new ConnectDeviceModel(in);
        }

        @Override
        public ConnectDeviceModel[] newArray(int size) {
            return new ConnectDeviceModel[size];
        }
    };

    public String getWifiName() {
        return wifiName;
    }

    public void setWifiName(String wifiName) {
        this.wifiName = wifiName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public WifiConnectStatus getState() {
        return state;
    }

    public void setState(WifiConnectStatus state) {
        this.state = state;
    }

    public String getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(String capabilities) {
        this.capabilities = capabilities;
    }

    public ScanResult getScanResult() {
        return scanResult;
    }

    public void setScanResult(ScanResult scanResult) {
        this.scanResult = scanResult;
    }

    @Override
    public int compareTo(ConnectDeviceModel connectDeviceModel) {
        int level1 = getLevel();
        int level2 = connectDeviceModel.getLevel();
        if (level1 > level2){
            return -1;
        }else if (level1 == level2){
            return 0;
        }else {
            return 1;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(wifiName);
        parcel.writeInt(level);
        parcel.writeString(capabilities);
        parcel.writeParcelable(scanResult, i);
    }
}
