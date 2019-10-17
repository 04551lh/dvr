package com.adasplus.deviceconnection.mvp.contract;

import android.view.View;

import com.adasplus.deviceconnection.mvp.model.ConnectDeviceModel;

/**
 * Author:刘净辉
 * Date : 2019/9/11 14:58
 */
public interface IConnectWifiItemListener {
    void onConnectWifiItem(View view, int position, ConnectDeviceModel connectDeviceModel);
}
