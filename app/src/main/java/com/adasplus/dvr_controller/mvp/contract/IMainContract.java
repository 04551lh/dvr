package com.adasplus.dvr_controller.mvp.contract;

import android.media.Image;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Author:刘净辉
 * Date : 2019/9/10
 * Description :
 */
public interface IMainContract {
    interface Model {
    }

    interface View {
        RecyclerView getRvNavigationBar();

        ImageView getIvHighBeam();

        ImageView getIvDippedHeadlight();

        ImageView getIvBrake();

        ImageView getIvNetworkDevice();

        ImageView getIvActivate();

        ImageView getIvPhoneSignal();

        ImageView getIvGpsStatus();
    }

    interface Presenter {
        void initData();

        void onResume();

        void onStop();
    }
}
