package com.adasplus.base.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;


/**
 * Author:刘净辉
 * Date : 2019/9/11 10:09
 */
public class WifiBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "WifiBroadcastReceiver";
    private IWifiChangeListener mWifiChangeListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(action)) {
            int state = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
            if (mWifiChangeListener != null) {
                mWifiChangeListener.onWifiSwitchState(state);
            }
        } else if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(action)) {
            NetworkInfo networkInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (networkInfo == null) {
                Log.e(TAG, "FATAL ERROR! NetworkInfo object is empty");
                return;
            }
            Log.i(TAG, "Wifi connection network status changes:" + networkInfo.toString());
            if (mWifiChangeListener != null) {
                mWifiChangeListener.onWifiConnectStatus(networkInfo);
            }
        } else if (WifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals(action)) { //扫描wifi的结果
            if (mWifiChangeListener != null) {
                Log.i(TAG, " Get scan wifi result ");
                mWifiChangeListener.onScanWifiResult();
            }
        } else if (WifiManager.SUPPLICANT_STATE_CHANGED_ACTION.equals(action)) { //密码错误
            int wifiResult = intent.getIntExtra(WifiManager.EXTRA_SUPPLICANT_ERROR, 0);
            String wifiName = intent.getStringExtra(WifiManager.EXTRA_WIFI_INFO);
            Log.i(TAG, "Wifi password input error" + wifiResult);
            if (mWifiChangeListener != null) {
                mWifiChangeListener.onWifiPwdError(wifiResult, wifiName);
            }
        }
    }

    public interface IWifiChangeListener {
        void onWifiSwitchState(int state);

        void onWifiConnectStatus(NetworkInfo networkInfo);

        void onScanWifiResult();

        void onWifiPwdError(int wifiResult, String wifiName);
    }

    public void setOnWifiChangeListener(IWifiChangeListener listener) {
        mWifiChangeListener = listener;
    }
}
