package com.adasplus.deviceconnection.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.adasplus.base.base.BaseActivity;
import com.adasplus.base.network.ActivityPathConstant;
import com.adasplus.deviceconnection.R;
import com.adasplus.deviceconnection.mvp.contract.IConnectDeviceContract;
import com.adasplus.deviceconnection.mvp.presenter.ConnectDevicePresenter;
import com.alibaba.android.arouter.facade.annotation.Route;

@Route(path = ActivityPathConstant.CONNECT_DEVICE_PATH)
public class ConnectDeviceActivity extends BaseActivity implements IConnectDeviceContract.View {


    private ImageView mIvBack;
    private RecyclerView mRvWifiHotSpots;
    private TextView mTvNoAvailableDevice;
    private LinearLayout mLlShowWifiList;
    private LinearLayout mLlConnectedWifi;
    private TextView mTvWifiName;
    private ImageView mIvIsEncrypt;
    private ImageView mIvWifiSignal;

    private ConnectDevicePresenter mConnectDevicePresenter;

    @Override
    protected void init(Bundle savedInstanceState) {
        mConnectDevicePresenter = new ConnectDevicePresenter(this);
        mConnectDevicePresenter.initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mConnectDevicePresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mConnectDevicePresenter.onPause();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_connect_device;
    }

    @Override
    protected void initWidget() {
        mIvBack = findViewById(R.id.iv_back);
        mRvWifiHotSpots = findViewById(R.id.rv_wifi_hotspot);
        mTvNoAvailableDevice = findViewById(R.id.tv_no_available_device);
        mLlShowWifiList = (LinearLayout) findViewById(R.id.ll_show_wifi_list);
        mLlConnectedWifi = (LinearLayout) findViewById(R.id.ll_connected_wifi);
        mTvWifiName = (TextView) findViewById(R.id.tv_wifi_name);
        mIvIsEncrypt = (ImageView) findViewById(R.id.iv_is_encrypt);
        mIvWifiSignal = (ImageView) findViewById(R.id.iv_wifi_signal);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mConnectDevicePresenter.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mConnectDevicePresenter.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public ImageView getIvBack() {
        return mIvBack;
    }

    @Override
    public RecyclerView getRvWifiHotSpots() {
        return mRvWifiHotSpots;
    }

    @Override
    public TextView getTvNoAvailableDevice() {
        return mTvNoAvailableDevice;
    }

    @Override
    public LinearLayout getLlShowWifiList() {
        return mLlShowWifiList;
    }

    @Override
    public LinearLayout getLlConnectedWifi() {
        return mLlConnectedWifi;
    }

    @Override
    public TextView getTvWifiName() {
        return mTvWifiName;
    }

    @Override
    public ImageView getIvIsEncrypt() {
        return mIvIsEncrypt;
    }

    @Override
    public ImageView getIvWifiSignal() {
        return mIvWifiSignal;
    }

}
