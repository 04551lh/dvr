package com.adasplus.homepager.connect.mvp.contract;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Author:刘净辉
 * Date : 2019/9/10 12:26
 * Description :
 */
public interface IConnectDeviceContract {
    interface Model {
    }

    interface View {
        ImageView getIvBack();

        RecyclerView getRvWifiHotSpots();

        TextView getTvNoAvailableDevice();

        LinearLayout getLlShowWifiList();

        LinearLayout getLlConnectedWifi();

        TextView getTvWifiName();

        ImageView getIvIsEncrypt();

        ImageView getIvWifiSignal();
    }

    interface Presenter {
        void initData();

        void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);

        void onActivityResult(int requestCode, int resultCode, @Nullable Intent data);

        void onResume();

        void onPause();
    }
}
