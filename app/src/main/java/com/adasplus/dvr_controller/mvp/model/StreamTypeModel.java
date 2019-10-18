package com.adasplus.dvr_controller.mvp.model;

/**
 * Author:刘净辉
 * Date : 2019/10/18 17:07
 * Description :
 */
public class StreamTypeModel {
    private String streamType;
    private int streamIndex;
    private boolean isChecked;

    public StreamTypeModel(String streamType, int streamIndex) {
        this.streamType = streamType;
        this.streamIndex = streamIndex;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getStreamType() {
        return streamType;
    }

    public void setStreamType(String streamType) {
        this.streamType = streamType;
    }

    public int getStreamIndex() {
        return streamIndex;
    }

    public void setStreamIndex(int streamIndex) {
        this.streamIndex = streamIndex;
    }

    @Override
    public String toString() {
        return "StreamTypeModel{" +
                "streamType='" + streamType + '\'' +
                ", streamIndex='" + streamIndex + '\'' +
                '}';
    }

}
