package com.adasplus.dvr_controller.mvp.contract;


import com.adasplus.base.network.model.SystemInfoModel;

/**
 * Author:刘净辉
 * Date : 2019/10/18 11:36
 * Description :
 */
public interface IHomeContract {
    interface Model {
    }

    interface View {

    }

    interface Presenter {

        void initData(SystemInfoModel systemInfoModel);

        void findViewById(android.view.View view);

        void setClickEvent(android.view.View view);

        void onResume();

        void onPause();

        void onDestroy();

        void setUSBStatus(boolean usbStatus);
    }
}
