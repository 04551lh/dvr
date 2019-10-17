package com.adasplus.settings.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.adasplus.settings.mvp.contract.IVideoSetContract;

/**
 * Author:刘净辉
 * Date : 2019/10/15 16:44
 * Description :
 */
public class VideoSetModel implements IVideoSetContract.Model, Parcelable {

    private int channelCount;
    private int channelNumber;
    private MainStreamBean mainStream;
    private SubStreamBean subStream;

    private VideoSetModel(Parcel in) {
        channelCount = in.readInt();
        channelNumber = in.readInt();
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

    public int getChannelCount() {
        return channelCount;
    }

    public void setChannelCount(int channelCount) {
        this.channelCount = channelCount;
    }

    public int getChannelNumber() {
        return channelNumber;
    }

    public void setChannelNumber(int channelNumber) {
        this.channelNumber = channelNumber;
    }

    public MainStreamBean getMainStream() {
        return mainStream;
    }

    public void setMainStream(MainStreamBean mainStream) {
        this.mainStream = mainStream;
    }

    public SubStreamBean getSubStream() {
        return subStream;
    }

    public void setSubStream(SubStreamBean subStream) {
        this.subStream = subStream;
    }

    @Override
    public String toString() {
        return "VideoSetModel{" +
                "channelCount=" + channelCount +
                ", channelNumber=" + channelNumber +
                ", mainStream=" + mainStream +
                ", subStream=" + subStream +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(channelCount);
        dest.writeInt(channelNumber);
    }

    public static class MainStreamBean implements Parcelable{


        private int enable;
        private int videoFrameRate;
        private int pictureQuality;
        private int resolutionRate;
        private int dateEnable;
        private int plateNumberEnable;
        private int channalNameEnable;
        private int localtionSignleEnable;
        private int speedEnable;

        MainStreamBean(Parcel in) {
            enable = in.readInt();
            videoFrameRate = in.readInt();
            pictureQuality = in.readInt();
            resolutionRate = in.readInt();
            dateEnable = in.readInt();
            plateNumberEnable = in.readInt();
            channalNameEnable = in.readInt();
            localtionSignleEnable = in.readInt();
            speedEnable = in.readInt();
        }

        public static final Creator<MainStreamBean> CREATOR = new Creator<MainStreamBean>() {
            @Override
            public MainStreamBean createFromParcel(Parcel in) {
                return new MainStreamBean(in);
            }

            @Override
            public MainStreamBean[] newArray(int size) {
                return new MainStreamBean[size];
            }
        };

        public int getEnable() {
            return enable;
        }

        public void setEnable(int enable) {
            this.enable = enable;
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
            return "MainStreamBean{" +
                    "enable=" + enable +
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
            dest.writeInt(enable);
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

    public static class SubStreamBean implements Parcelable{

        private int enable;
        private int videoFrameRate;
        private int pictureQuality;
        private int resolutionRate;
        private int dateEnable;
        private int plateNumberEnable;
        private int channalNameEnable;
        private int localtionSignleEnable;
        private int speedEnable;

        SubStreamBean(Parcel in) {
            enable = in.readInt();
            videoFrameRate = in.readInt();
            pictureQuality = in.readInt();
            resolutionRate = in.readInt();
            dateEnable = in.readInt();
            plateNumberEnable = in.readInt();
            channalNameEnable = in.readInt();
            localtionSignleEnable = in.readInt();
            speedEnable = in.readInt();
        }

        public static final Creator<SubStreamBean> CREATOR = new Creator<SubStreamBean>() {
            @Override
            public SubStreamBean createFromParcel(Parcel in) {
                return new SubStreamBean(in);
            }

            @Override
            public SubStreamBean[] newArray(int size) {
                return new SubStreamBean[size];
            }
        };

        public int getEnable() {
            return enable;
        }

        public void setEnable(int enable) {
            this.enable = enable;
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
            return "SubStreamBean{" +
                    "enable=" + enable +
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
            dest.writeInt(enable);
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
}
