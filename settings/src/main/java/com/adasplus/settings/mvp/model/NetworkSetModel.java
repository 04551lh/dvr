package com.adasplus.settings.mvp.model;

import com.adasplus.settings.mvp.contract.INetworkSetContract;

/**
 * Author:刘净辉
 * Date : 2019/9/25 18:05
 * Description :
 */
public class NetworkSetModel implements INetworkSetContract.Model {

    private WirelessNetworkConfigBean wirelessNetworkConfig;
    private WiredNetworkConfigBean wiredNetworkConfig;

    public WirelessNetworkConfigBean getWirelessNetworkConfig() {
        return wirelessNetworkConfig;
    }

    public void setWirelessNetworkConfig(WirelessNetworkConfigBean wirelessNetworkConfig) {
        this.wirelessNetworkConfig = wirelessNetworkConfig;
    }

    public WiredNetworkConfigBean getWiredNetworkConfig() {
        return wiredNetworkConfig;
    }

    public void setWiredNetworkConfig(WiredNetworkConfigBean wiredNetworkConfig) {
        this.wiredNetworkConfig = wiredNetworkConfig;
    }

    @Override
    public String toString() {
        return "NetworkSetModel{" +
                "wirelessNetworkConfig=" + wirelessNetworkConfig +
                ", wiredNetworkConfig=" + wiredNetworkConfig +
                '}';
    }

    public static class WirelessNetworkConfigBean {



        private String ipAddress;
        private String mask;
        private String gateway;
        private String mac;

        public String getIpAddress() {
            return ipAddress;
        }

        public void setIpAddress(String ipAddress) {
            this.ipAddress = ipAddress;
        }

        public String getMask() {
            return mask;
        }

        public void setMask(String mask) {
            this.mask = mask;
        }

        public String getGateway() {
            return gateway;
        }

        public void setGateway(String gateway) {
            this.gateway = gateway;
        }

        public String getMac() {
            return mac;
        }

        public void setMac(String mac) {
            this.mac = mac;
        }

        @Override
        public String toString() {
            return "WirelessNetworkConfigBean{" +
                    "ipAddress='" + ipAddress + '\'' +
                    ", mask='" + mask + '\'' +
                    ", gateway='" + gateway + '\'' +
                    ", mac='" + mac + '\'' +
                    '}';
        }
    }

    public static class WiredNetworkConfigBean {


        private String ipAddress;
        private String mask;
        private String gateway;
        private String mac;

        public String getIpAddress() {
            return ipAddress;
        }

        public void setIpAddress(String ipAddress) {
            this.ipAddress = ipAddress;
        }

        public String getMask() {
            return mask;
        }

        public void setMask(String mask) {
            this.mask = mask;
        }

        public String getGateway() {
            return gateway;
        }

        public void setGateway(String gateway) {
            this.gateway = gateway;
        }

        public String getMac() {
            return mac;
        }

        public void setMac(String mac) {
            this.mac = mac;
        }

        @Override
        public String toString() {
            return "WiredNetworkConfigBean{" +
                    "ipAddress='" + ipAddress + '\'' +
                    ", mask='" + mask + '\'' +
                    ", gateway='" + gateway + '\'' +
                    ", mac='" + mac + '\'' +
                    '}';
        }
    }
}
