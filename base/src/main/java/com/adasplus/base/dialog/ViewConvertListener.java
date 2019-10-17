package com.adasplus.base.dialog;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author:刘净辉
 * Date : 2019/9/18 16:16
 */
public abstract class ViewConvertListener implements Parcelable {
    protected abstract void convertView(ViewHolder holder, BasicDialog dialog);

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    protected ViewConvertListener() {

    }

    ViewConvertListener(Parcel in) {

    }

    public static final Creator<ViewConvertListener> CREATOR = new Creator<ViewConvertListener>() {
        @Override
        public ViewConvertListener createFromParcel(Parcel source) {
            return new ViewConvertListener(source) {
                @Override
                protected void convertView(ViewHolder holder, BasicDialog dialog) {

                }
            };
        }

        @Override
        public ViewConvertListener[] newArray(int size) {
            return new ViewConvertListener[size];
        }
    };
}
