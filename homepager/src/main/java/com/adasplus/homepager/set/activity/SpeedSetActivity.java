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

    private ImageView mIvSpeedBack;
    private SwipeRefreshLayout mSwipeRefreshLayoutSpeedSet;
    private ImageView mIvPulseSpeed;
    private TextView mTvManualCalibration;
    private ImageView mIvManualCalibration;
    private TextView mTvCoefficientOfThePulse;
    private EditText mEtCoefficientOfThePulseValue;
    private TextView mTvCoefficientDefaultSettings;
    private TextView mTvAutomaticCalibration;
    private ImageView mIvAutomaticCalibration;
    private TextView mTvErrorValue;
    private Button mBtnSub;
    private EditText mEtErrorNumber;
    private Button mBtnAdd;
    private ImageView mIvGPSSpeedStatus;
    private ImageView mIvSimulationSpeedStatus;
    private SignSeekBar mSsbSpeedValue;
    private TextView mTvCurrentSpeed;
    private TextView mTvSpeedSave;

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
        mIvSpeedBack = findViewById(R.id.iv_speed_back);
        mIvPulseSpeed =  findViewById(R.id.iv_pulse_speed);
        mSwipeRefreshLayoutSpeedSet = findViewById(R.id.swipeRefreshLayout_speed_set);
        mTvManualCalibration =  findViewById(R.id.tv_manual_calibration);
        mIvManualCalibration =  findViewById(R.id.iv_manual_calibration);
        mTvCoefficientOfThePulse =  findViewById(R.id.tv_coefficient_of_the_pulse);
        mEtCoefficientOfThePulseValue =  findViewById(R.id.et_coefficient_of_the_pulse_value);
        mTvCoefficientDefaultSettings =  findViewById(R.id.tv_coefficient_default_settings);
        mTvAutomaticCalibration =  findViewById(R.id.tv_automatic_calibration);
        mIvAutomaticCalibration =  findViewById(R.id.iv_automatic_calibration);
        mTvErrorValue =  findViewById(R.id.tv_error_value);
        mBtnSub =  findViewById(R.id.btn_sub);
        mEtErrorNumber =  findViewById(R.id.et_error_number);
        mBtnAdd =  findViewById(R.id.btn_add);
        mIvGPSSpeedStatus =  findViewById(R.id.iv_gps_speed);
        mIvSimulationSpeedStatus =  findViewById(R.id.iv_simulation_speed_status);
        mSsbSpeedValue =  findViewById(R.id.ssb_speed_value);
        mTvCurrentSpeed =  findViewById(R.id.tv_current_speed);
        mTvSpeedSave =  findViewById(R.id.tv_speed_save);
    }

    @Override
    public ImageView getIvSpeedBack() {
        return mIvSpeedBack;
    }
    @Override
    public ImageView getIvPulseSpeed() {
        return mIvPulseSpeed;
    }

    @Override
    public SwipeRefreshLayout getSwipeRefreshLayoutSpeedSet() {
        return mSwipeRefreshLayoutSpeedSet;
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
    public TextView getTvCoefficientDefaultSettings() {
        return mTvCoefficientDefaultSettings;
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
    public ImageView getIvGPSSpeedStatus() {
        return mIvGPSSpeedStatus;
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
    public TextView getTvSpeedSave() {
        return mTvSpeedSave;
    }
}
