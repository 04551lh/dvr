package com.adasplus.homepager.activate.mvp.model;

import java.util.List;

/**
 * Author:刘净辉
 * Date : 2019/9/20 11:47
 * Description :
 */
public class GetPlatformInfoModel {

    private List<ArrayBean> Array;

    public List<ArrayBean> getArray() {
        return Array;
    }

    public void setArray(List<ArrayBean> Array) {
        this.Array = Array;
    }

    @Override
    public String toString() {
        return "GetPlatformInfoModel{" +
                "Array=" + Array +
                '}';
    }

    public static class ArrayBean {

        private int platformKey;
        private String platformIp;
        private int platformPort;
        private int connectStatus;

        public int getPlatformKey() {
            return platformKey;
        }

        public void setPlatformKey(int platformKey) {
            this.platformKey = platformKey;
        }

        public String getPlatformIp() {
            return platformIp;
        }

        public void setPlatformIp(String platformIp) {
            this.platformIp = platformIp;
        }

        public int getPlatformPort() {
            return platformPort;
        }

        public void setPlatformPort(int platformPort) {
            this.platformPort = platformPort;
        }

        public int getConnectStatus() {
            return connectStatus;
        }

        public void setConnectStatus(int connectStatus) {
            this.connectStatus = connectStatus;
        }

        @Override
        public String toString() {
            return "ArrayBean{" +
                    "platformKey=" + platformKey +
                    ", platformIp='" + platformIp + '\'' +
                    ", platformPort=" + platformPort +
                    ", connectStatus=" + connectStatus +
                    '}';
        }
    }
}
