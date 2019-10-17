package com.adasplus.dvr_controller.activity;


import android.os.Bundle;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.adasplus.dvr_controller.R;
import com.adasplus.base.base.BaseActivity;
import com.adasplus.dvr_controller.mvp.contract.IMainContract;
import com.adasplus.dvr_controller.mvp.presenter.MainPresenter;


public class MainActivity extends BaseActivity implements IMainContract.View {

    RecyclerView mRvNavigationBar;
    ImageView mIvHighBeam;
    ImageView mIvDippedHeadlight;
    ImageView mIvBrake;
    ImageView mIvNetworkDevice;
    ImageView mIvActivate;
    ImageView mIvPhoneSignal;
    ImageView mIvGpsStatus;

    MainPresenter mMainPresenter;

    @Override
    protected void init(Bundle savedInstanceState) {
        mMainPresenter = new MainPresenter(this);
        mMainPresenter.initData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        mRvNavigationBar = findViewById(R.id.rv_navigation_bar);
        mIvHighBeam = findViewById(R.id.iv_high_beam);
        mIvDippedHeadlight = findViewById(R.id.iv_dipped_headlight);
        mIvBrake = findViewById(R.id.iv_brake);
        mIvNetworkDevice = findViewById(R.id.iv_network_device);
        mIvActivate = findViewById(R.id.iv_activate);
        mIvPhoneSignal = findViewById(R.id.iv_phone_signal);
        mIvGpsStatus = findViewById(R.id.iv_gps_status);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMainPresenter.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMainPresenter.onStop();
    }

    @Override
    public RecyclerView getRvNavigationBar() {
        return mRvNavigationBar;
    }

    @Override
    public ImageView getIvHighBeam() {
        return mIvHighBeam;
    }

    @Override
    public ImageView getIvDippedHeadlight() {
        return mIvDippedHeadlight;
    }

    @Override
    public ImageView getIvBrake() {
        return mIvBrake;
    }

    @Override
    public ImageView getIvNetworkDevice() {
        return mIvNetworkDevice;
    }

    @Override
    public ImageView getIvActivate() {
        return mIvActivate;
    }

    @Override
    public ImageView getIvPhoneSignal() {
        return mIvPhoneSignal;
    }

    @Override
    public ImageView getIvGpsStatus() {
        return mIvGpsStatus;
    }
}
