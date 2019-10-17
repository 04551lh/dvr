package com.adasplus.settings.mvp.model;

import java.util.List;

/**
 * Author:刘净辉
 * Date : 2019/9/26 12:05
 * Description :
 */
public class CANChannelsModel {

    private List<ArrayBean> Array;

    public List<ArrayBean> getArray() {
        return Array;
    }

    public void setArray(List<ArrayBean> Array) {
        this.Array = Array;
    }



    public static class ArrayBean {

        private String channelName;
        private int channelIndex;
        private int speed;

        public ArrayBean(String channelName) {
            this.channelName = channelName;
        }

        public String getChannelName() {
            return channelName;
        }

        public void setChannelName(String channelName) {
            this.channelName = channelName;
        }

        public int getChannelIndex() {
            return channelIndex;
        }

        public void setChannelIndex(int channelIndex) {
            this.channelIndex = channelIndex;
        }

        public int getSpeed() {
            return speed;
        }

        public void setSpeed(int speed) {
            this.speed = speed;
        }

        @Override
        public String toString() {
            return "ArrayBean{" +
                    "channelIndex=" + channelIndex +
                    ", speed=" + speed +
                    '}';
        }
    }
}
