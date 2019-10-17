package com.adasplus.settings.activity;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.adasplus.base.base.BaseActivity;
import com.adasplus.settings.R;
import com.adasplus.settings.mvp.contract.ISettingsContract;
import com.adasplus.settings.mvp.presenter.SettingsPresenter;
import com.alibaba.android.arouter.facade.annotation.Route;

@Route(path = "/device/settings")
public class SettingsActivity extends BaseActivity implements ISettingsContract.View {

    ImageView mIvBack;
    TextView mTvSpeedSet;
    TextView mTvNetworkSet;
    TextView mTvCanSet;
    TextView mTvCalibrationSet;
    TextView mTvWarningSet;
    TextView mTvCommonSet;
    TextView mTvVideoSet;

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
        mTvSpeedSet = findViewById(R.id.tv_speed_set);
        mTvNetworkSet = findViewById(R.id.tv_network_set);
        mTvCanSet = findViewById(R.id.tv_can_set);
        mTvCalibrationSet = findViewById(R.id.tv_calibration_set);
        mTvWarningSet = findViewById(R.id.tv_warning_set);
        mTvCommonSet = findViewById(R.id.tv_common_set);
        mTvVideoSet = findViewById(R.id.tv_video_set);
    }

    @Override
    public ImageView getIvBack() {
        return mIvBack;
    }

    @Override
    public TextView getTvSpeedSet() {
        return mTvSpeedSet;
    }

    @Override
    public TextView getTvNetworkSet() {
        return mTvNetworkSet;
    }

    @Override
    public TextView getTvCanSet() {
        return mTvCanSet;
    }

    @Override
    public TextView getTvCalibrationSet() {
        return mTvCalibrationSet;
    }

    @Override
    public TextView getTvWarningSet() {
        return mTvWarningSet;
    }

    @Override
    public TextView getTvCommonSet() {
        return mTvCommonSet;
    }

    @Override
    public TextView getTvVideoSet() {
        return mTvVideoSet;
    }
}
