package com.adasplus.homepager.set.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.adasplus.homepager.set.mvp.contract.IVideoSetContract;

/**
 * Author:刘净辉
 * Date : 2019/10/15 16:44
 * Description :
 */
public class VideoSetModel implements IVideoSetContract.Model, Parcelable {

    private int channelNumber;
    private int streamType;
    private int streamEnable;
    private int videoFrameRate;
    private int pictureQuality;
    private int resolutionRate;
    private int dateEnable;
    private int plateNumberEnable;
    private int channalNameEnable;
    private int localtionSignleEnable;
    private int speedEnable;

    private VideoSetModel(Parcel in) {
        channelNumber = in.readInt();
        streamType = in.readInt();
        streamEnable = in.readInt();
        videoFrameRate = in.readInt();
        pictureQuality = in.readInt();
        resolutionRate = in.readInt();
        dateEnable = in.readInt();
        plateNumberEnable = in.readInt();
        channalNameEnable = in.readInt();
        localtionSignleEnable = in.readInt();
        speedEnable = in.readInt();
    }

    public VideoSetModel(){

    }

    public static final Creator<VideoSetModel> CREATOR = new Creator<VideoSetModel>() {
        @Override
        public VideoSetModel createFromParcel(Parcel in) {
            return new VideoSetModel(in);
        }

        @Override
        public VideoSetModel[] newArray(int size) {
            return new VideoSetModel[size];
        }
    };

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

    public int getStreamEnable() {
        return streamEnable;
    }

    public void setStreamEnable(int streamEnable) {
        this.streamEnable = streamEnable;
    }

    public int getVideoFrameRate() {
        return videoFrameRate;
    }

    public void setVideoFrameRate(int videoFrameRate) {
        this.videoFrameRate = videoFrameRate;
    }

    public int getPictureQuality() {
        return pictureQuality;
    }

    public void setPictureQuality(int pictureQuality) {
        this.pictureQuality = pictureQuality;
    }

    public int getResolutionRate() {
        return resolutionRate;
    }

    public void setResolutionRate(int resolutionRate) {
        this.resolutionRate = resolutionRate;
    }

    public int getDateEnable() {
        return dateEnable;
    }

    public void setDateEnable(int dateEnable) {
        this.dateEnable = dateEnable;
    }

    public int getPlateNumberEnable() {
        return plateNumberEnable;
    }

    public void setPlateNumberEnable(int plateNumberEnable) {
        this.plateNumberEnable = plateNumberEnable;
    }

    public int getChannalNameEnable() {
        return channalNameEnable;
    }

    public void setChannalNameEnable(int channalNameEnable) {
        this.channalNameEnable = channalNameEnable;
    }

    public int getLocaltionSignleEnable() {
        return localtionSignleEnable;
    }

    public void setLocaltionSignleEnable(int localtionSignleEnable) {
        this.localtionSignleEnable = localtionSignleEnable;
    }

    public int getSpeedEnable() {
        return speedEnable;
    }

    public void setSpeedEnable(int speedEnable) {
        this.speedEnable = speedEnable;
    }

    @Override
    public String toString() {
        return "VideoSetModel{" +
                "channelNumber=" + channelNumber +
                ", streamType=" + streamType +
                ", streamEnable=" + streamEnable +
                ", videoFrameRate=" + videoFrameRate +
                ", pictureQuality=" + pictureQuality +
                ", resolutionRate=" + resolutionRate +
                ", dateEnable=" + dateEnable +
                ", plateNumberEnable=" + plateNumberEnable +
                ", channalNameEnable=" + channalNameEnable +
                ", localtionSignleEnable=" + localtionSignleEnable +
                ", speedEnable=" + speedEnable +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(channelNumber);
        dest.writeInt(streamType);
        dest.writeInt(streamEnable);
        dest.writeInt(videoFrameRate);
        dest.writeInt(pictureQuality);
        dest.writeInt(resolutionRate);
        dest.writeInt(dateEnable);
        dest.writeInt(plateNumberEnable);
        dest.writeInt(channalNameEnable);
        dest.writeInt(localtionSignleEnable);
        dest.writeInt(speedEnable);
    }
}
