package com.adasplus.homepager.set.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.adasplus.homepager.set.mvp.contract.IVideoShowContract;

public class VideoShowModel implements IVideoShowContract.Model, Parcelable {

    /**
     * channelNumber : 1
     * showFullScreen : 0
     */

    private int channelNumber;

    @Override
    public String toString() {
        return "VideoShowModel{" +
                "channelNumber=" + channelNumber +
                ", showFullScreen=" + showFullScreen +
                '}';
    }

    private int showFullScreen;

    protected VideoShowModel(Parcel in) {
        channelNumber = in.readInt();
        showFullScreen = in.readInt();
    }

    public static final Creator<VideoShowModel> CREATOR = new Creator<VideoShowModel>() {
        @Override
        public VideoShowModel createFromParcel(Parcel in) {
            return new VideoShowModel(in);
        }

        @Override
        public VideoShowModel[] newArray(int size) {
            return new VideoShowModel[size];
        }
    };

    public int getChannelNumber() {
        return channelNumber;
    }

    public void setChannelNumber(int channelNumber) {
        this.channelNumber = channelNumber;
    }

    public int getShowFullScreen() {
        return showFullScreen;
    }

    public void setShowFullScreen(int showFullScreen) {
        this.showFullScreen = showFullScreen;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(channelNumber);
        dest.writeInt(showFullScreen);
    }
}
