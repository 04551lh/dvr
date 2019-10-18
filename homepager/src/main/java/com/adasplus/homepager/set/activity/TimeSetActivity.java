package com.adasplus.homepager.set.activity;


import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adasplus.base.base.BaseActivity;
import com.adasplus.homepager.R;
import com.adasplus.homepager.set.mvp.contract.ITimeSetContract;
import com.adasplus.homepager.set.mvp.presenter.TimeSetPresenter;


public class TimeSetActivity extends BaseActivity implements ITimeSetContract.View {


    private ImageView mIvBack;
    private LinearLayout mLlNetworkTime;
    private CheckBox mCbNetworkTime;
    private LinearLayout mLlGpsTime;
    private CheckBox mCbGpsTime;
    private LinearLayout mLlWhenManualCalibration;
    private CheckBox mCbWhenManualCalibration;
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
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mLlNetworkTime = (LinearLayout) findViewById(R.id.ll_network_time);
        mCbNetworkTime = (CheckBox) findViewById(R.id.cb_network_time);
        mLlGpsTime = (LinearLayout) findViewById(R.id.ll_gps_time);
        mCbGpsTime = (CheckBox) findViewById(R.id.cb_gps_time);
        mLlWhenManualCalibration = (LinearLayout) findViewById(R.id.ll_when_manual_calibration);
        mCbWhenManualCalibration = (CheckBox) findViewById(R.id.cb_when_manual_calibration);
        mEtYear = (EditText) findViewById(R.id.et_year);
        mEtMonth = (EditText) findViewById(R.id.et_month);
        mEtDay = (EditText) findViewById(R.id.et_day);
        mEtHours = (EditText) findViewById(R.id.et_hours);
        mEtMinutes = (EditText) findViewById(R.id.et_minutes);
        mEtSeconds = (EditText) findViewById(R.id.et_seconds);
        mTvSave = (TextView) findViewById(R.id.tv_save);
    }

    @Override
    public ImageView getIvBack() {
        return mIvBack;
    }

    @Override
    public LinearLayout getLlNetworkTime() {
        return mLlNetworkTime;
    }

    @Override
    public CheckBox getCbNetworkTime() {
        return mCbNetworkTime;
    }

    @Override
    public LinearLayout getLlGpsTime() {
        return mLlGpsTime;
    }

    @Override
    public CheckBox getCbGpsTime() {
        return mCbGpsTime;
    }

    @Override
    public LinearLayout getLlWhenManualCalibration() {
        return mLlWhenManualCalibration;
    }

    @Override
    public CheckBox getCbWhenManualCalibration() {
        return mCbWhenManualCalibration;
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
