package com.adasplus.base.network.model;

/**
 * Author:刘净辉
 * Date : 2019/10/14 10:41
 * Description :
 */
public class FileExportModel {

    private int channelCount;
    private int progress;

    public int getChannelCount() {
        return channelCount;
    }

    public void setChannelCount(int channelCount) {
        this.channelCount = channelCount;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    @Override
    public String toString() {
        return "FileExportModel{" +
                "channelCount=" + channelCount +
                ", progress=" + progress +
                '}';
    }
}
