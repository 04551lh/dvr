package com.adasplus.dvr_controller.mvp.model;

/**
 * Author:刘净辉
 * Date : 2019/10/18 17:07
 * Description :
 */
public class ChannelNumbersModel {
    private int channelNumber; //通道号
    private int streamType; //码流类型
    private boolean isChecked;

    public ChannelNumbersModel(int channelNumber, int streamType) {
        this.channelNumber = channelNumber;
        this.streamType = streamType;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getChannelNumber() {
        return channelNumber;
    }

    public void setChannelNumber(int channelNumber) {
        this.channelNumber = channelNumber;
    }

    public int getStreamType() {
        return streamType;
    }

    public void setStreamType(int streamType) {
        this.streamType = streamType;
    }

    @Override
    public String toString() {
        return "ChannelNumbersModel{" +
                "channelNumber=" + channelNumber +
                ", streamType=" + streamType +
                '}';
    }

}
