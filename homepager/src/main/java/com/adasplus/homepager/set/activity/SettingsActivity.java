package com.adasplus.homepager.set.activity;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout;

import com.adasplus.base.base.BaseActivity;
import com.adasplus.homepager.R;
import com.adasplus.homepager.set.mvp.contract.ISettingsContract;
import com.adasplus.homepager.set.mvp.presenter.SettingsPresenter;
import com.alibaba.android.arouter.facade.annotation.Route;

@Route(path = "/device/settings")
public class SettingsActivity extends BaseActivity implements ISettingsContract.View {

    ImageView mIvBack;
    LinearLayout mLlSpeedSet;
    LinearLayout mLlNetworkSet;
    LinearLayout mLlCanSet;
    LinearLayout mLlCalibrationSet;
    LinearLayout mLlWarningSet;
    LinearLayout mLlCommonSet;
    LinearLayout mLlVideoSet;

    @Override
    protected void init(Bundle savedInstanceState) {
        SettingsPresenter settingsPresenter = new SettingsPresenter(this);
        settingsPresenter.initData();
        settingsPresenter.initListener();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    protected void initWidget() {
        mIvBack = findViewById(R.id.iv_back);
        mLlSpeedSet = findViewById(R.id.ll_speed_set);
        mLlNetworkSet = findViewById(R.id.ll_network_set);
        mLlCanSet = findViewById(R.id.ll_can_set);
        mLlCalibrationSet = findViewById(R.id.ll_calibration_set);
        mLlWarningSet = findViewById(R.id.ll_warning_set);
        mLlCommonSet = findViewById(R.id.ll_common_set);
        mLlVideoSet = findViewById(R.id.ll_video_set);
    }

    @Override
    public ImageView getIvBack() {
        return mIvBack;
    }

    @Override
    public LinearLayout getLlSpeedSet() {
        return mLlSpeedSet;
    }

    @Override
    public LinearLayout getLlNetworkSet() {
        return mLlNetworkSet;
    }

    @Override
    public LinearLayout getLlCanSet() {
        return mLlCanSet;
    }

    @Override
    public LinearLayout getLlCalibrationSet() {
        return mLlCalibrationSet;
    }

    @Override
    public LinearLayout getLlWarningSet() {
        return mLlWarningSet;
    }

    @Override
    public LinearLayout getLlCommonSet() {
        return mLlCommonSet;
    }

    @Override
    public LinearLayout getLlVideoSet() {
        return mLlVideoSet;
    }
}
