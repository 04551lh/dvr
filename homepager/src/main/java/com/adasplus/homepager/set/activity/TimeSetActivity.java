package com.adasplus.homepager.set.activity;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.TextView;

import com.adasplus.base.base.BaseActivity;
import com.adasplus.homepager.R;
import com.adasplus.homepager.set.mvp.contract.ITimeSetContract;
import com.adasplus.homepager.set.mvp.presenter.TimeSetPresenter;


public class TimeSetActivity extends BaseActivity implements ITimeSetContract.View {


    private ImageView mIvBack;
    private ImageView mIvAutomaticCorrectionWhen;
    private ImageView mIvNetworkTime;
    private ImageView mIvGpsTime;
    private ImageView mIvWhenManualCalibration;
    private EditText mEtYear;
    private EditText mEtMonth;
    private EditText mEtDay;
    private EditText mEtHours;
    private EditText mEtMinutes;
    private EditText mEtSeconds;
    private TextView mTvSave;

    @Override
    protected void init(Bundle savedInstanceState) {
        TimeSetPresenter timeSetPresenter = new TimeSetPresenter(this);
        timeSetPresenter.initData();
        timeSetPresenter.initListener();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_time_set;
    }

    @Override
    protected void initWidget() {
        mIvBack = findViewById(R.id.iv_back);
        mIvAutomaticCorrectionWhen = findViewById(R.id.iv_automatic_correction_when);
        mIvNetworkTime = findViewById(R.id.iv_network_time);
        mIvGpsTime = findViewById(R.id.iv_gps_time);
        mIvWhenManualCalibration = findViewById(R.id.iv_when_manual_calibration);
        mEtYear = findViewById(R.id.et_year);
        mEtMonth = findViewById(R.id.et_month);
        mEtDay = findViewById(R.id.et_day);
        mEtHours = findViewById(R.id.et_hours);
        mEtMinutes = findViewById(R.id.et_minutes);
        mEtSeconds = findViewById(R.id.et_seconds);
        mTvSave =  findViewById(R.id.tv_save);
    }

    @Override
    public ImageView getIvBack() {
        return mIvBack;
    }
    
    @Override
    public ImageView getIvNetworkTime() {
        return mIvNetworkTime;
    }

    @Override
    public ImageView getIvAutomaticCorrectionWhen() {
        return mIvAutomaticCorrectionWhen;
    }

    @Override
    public ImageView getIvGpsTime() {
        return mIvGpsTime;
    }
    
    @Override
    public ImageView getIvWhenManualCalibration() {
        return mIvWhenManualCalibration;
    }

    @Override
    public EditText getEtYear() {
        return mEtYear;
    }

    @Override
    public EditText getEtMonth() {
        return mEtMonth;
    }

    @Override
    public EditText getEtDay() {
        return mEtDay;
    }

    @Override
    public EditText getEtHours() {
        return mEtHours;
    }

    @Override
    public EditText getEtMinutes() {
        return mEtMinutes;
    }

    @Override
    public EditText getEtSeconds() {
        return mEtSeconds;
    }

    @Override
    public TextView getTvSave() {
        return mTvSave;
    }
}
