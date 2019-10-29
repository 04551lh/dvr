package com.adasplus.homepager.set.activity;


import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.TextView;

import com.adasplus.base.base.BaseActivity;
import com.adasplus.base.view.SignSeekBar;
import com.adasplus.homepager.R;
import com.adasplus.homepager.set.mvp.contract.ISpeedSetContract;
import com.adasplus.homepager.set.mvp.presenter.SpeedSetPresenter;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class SpeedSetActivity extends BaseActivity implements ISpeedSetContract.View {

    private ImageView mIvBack;
    private SwipeRefreshLayout mSwipeContainer;
    private ImageView mIvPulseSpeed;
    private TextView mTvManualCalibration;
    private ImageView mIvManualCalibration;
    private TextView mTvCoefficientOfThePulse;
    private EditText mEtCoefficientOfThePulseValue;
    private TextView mTvAutomaticCalibration;
    private ImageView mIvAutomaticCalibration;
    private TextView mTvErrorValue;
    private Button mBtnSub;
    private EditText mEtErrorNumber;
    private Button mBtnAdd;
    private ImageView mIvSimulationSpeedStatus;
    private SignSeekBar mSsbSpeedValue;
    private TextView mTvCurrentSpeed;
    private TextView mTvSave;

    @Override
    protected void init(Bundle savedInstanceState) {
        SpeedSetPresenter speedSetPresenter = new SpeedSetPresenter(this);
        speedSetPresenter.initData();
        speedSetPresenter.initListener();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_speed_set;
    }

    @Override
    protected void initWidget() {
        mIvBack = findViewById(R.id.iv_back);
        mIvPulseSpeed =  findViewById(R.id.iv_pulse_speed);
        mSwipeContainer = findViewById(R.id.swipe_container);
        mTvManualCalibration =  findViewById(R.id.tv_manual_calibration);
        mIvManualCalibration =  findViewById(R.id.iv_manual_calibration);
        mTvCoefficientOfThePulse =  findViewById(R.id.tv_coefficient_of_the_pulse);
        mEtCoefficientOfThePulseValue =  findViewById(R.id.et_coefficient_of_the_pulse_value);
        mTvAutomaticCalibration =  findViewById(R.id.tv_automatic_calibration);
        mIvAutomaticCalibration =  findViewById(R.id.iv_automatic_calibration);
        mTvErrorValue =  findViewById(R.id.tv_error_value);
        mBtnSub =  findViewById(R.id.btn_sub);
        mEtErrorNumber =  findViewById(R.id.et_error_number);
        mBtnAdd =  findViewById(R.id.btn_add);
        mIvSimulationSpeedStatus =  findViewById(R.id.iv_simulation_speed_status);
        mSsbSpeedValue =  findViewById(R.id.ssb_speed_value);
        mTvCurrentSpeed =  findViewById(R.id.tv_current_speed);
        mTvSave =  findViewById(R.id.tv_save);
    }

    @Override
    public ImageView getIvBack() {
        return mIvBack;
    }
    @Override
    public ImageView getIvPulseSpeed() {
        return mIvPulseSpeed;
    }

    @Override
    public SwipeRefreshLayout getSwipeContainer() {
        return mSwipeContainer;
    }

    @Override
    public TextView getTvManualCalibration() {
        return mTvManualCalibration;
    }

    @Override
    public ImageView getIvManualCalibration() {
        return mIvManualCalibration;
    }

    @Override
    public TextView getTvCoefficientOfThePulse() {
        return mTvCoefficientOfThePulse;
    }

    @Override
    public EditText getEtCoefficientOfThePulseValue() {
        return mEtCoefficientOfThePulseValue;
    }

    @Override
    public TextView getTvAutomaticCalibration() {
        return mTvAutomaticCalibration;
    }

    @Override
    public ImageView getIvAutomaticCalibration() {
        return mIvAutomaticCalibration;
    }


    @Override
    public TextView getTvErrorValue() {
        return mTvErrorValue;
    }

    @Override
    public Button getBtnSub() {
        return mBtnSub;
    }

    @Override
    public EditText getEtErrorNumber() {
        return mEtErrorNumber;
    }

    @Override
    public Button getBtnAdd() {
        return mBtnAdd;
    }

    @Override
    public ImageView getIvSimulationSpeedStatus() {
        return mIvSimulationSpeedStatus;
    }

    @Override
    public SignSeekBar getSsbSpeedValue() {
        return mSsbSpeedValue;
    }

    @Override
    public TextView getTvCurrentSpeed() {
        return mTvCurrentSpeed;
    }

    @Override
    public TextView getTvSave() {
        return mTvSave;
    }
}
