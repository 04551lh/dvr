package com.adasplus.homepager.set.mvp.model;

import com.adasplus.homepager.set.mvp.contract.IVideoShowContract;

/**
 * Author:刘净辉
 * Date : 2019/9/25 19:16
 * Description :
 */
public class ShowPlatformListModel implements IVideoShowContract.Model {

    private String platformIpAddress;
    private String platformPort;

    public ShowPlatformListModel(String platformIpAddress, String platformPort) {
        this.platformIpAddress = platformIpAddress;
        this.platformPort = platformPort;
    }

    public String getPlatformIpAddress() {
        return platformIpAddress;
    }

    public void setPlatformIpAddress(String platformIpAddress) {
        this.platformIpAddress = platformIpAddress;
    }

    public String getPlatformPort() {
        return platformPort;
    }

    public void setPlatformPort(String platformPort) {
        this.platformPort = platformPort;
    }

    @Override
    public String toString() {
        return "ShowPlatformListModel{" +
                "platformIpAddress='" + platformIpAddress + '\'' +
                ", platformPort='" + platformPort + '\'' +
                '}';
    }
}
