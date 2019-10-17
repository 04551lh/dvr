package com.adasplus.settings.activity;


import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adasplus.base.base.BaseActivity;
import com.adasplus.base.view.SignSeekBar;
import com.adasplus.base.view.SlideSwitchView;
import com.adasplus.settings.R;
import com.adasplus.settings.mvp.contract.ISpeedSetContract;
import com.adasplus.settings.mvp.presenter.SpeedSetPresenter;

public class SpeedSetActivity extends BaseActivity implements ISpeedSetContract.View {

    private ImageView mIvback;
    private LinearLayout mLlPulseSpeed;
    private CheckBox mCbPulseSpeed;
    private TextView mTvManualCalibration;
    private TextView mTvCoefficientOfThePulse;
    private EditText mEtCoefficientOfThePulseValue;
    private TextView mTvProportionValue;
    private TextView mTvAutomaticCalibration;
    private SlideSwitchView mSsvAutomaticCalibrationSwitch;
    private TextView mTvErrorValue;
    private Button mBtnSub;
    private EditText mEtErrorNumber;
    private Button mBtnAdd;
    private TextView mTvPercent;
    private LinearLayout mLlSimulationSpeed;
    private CheckBox mCbSimulationSpeedStatus;
    private SignSeekBar mSsbSpeedValue;
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
        mIvback = (ImageView) findViewById(R.id.iv_back);
        mLlPulseSpeed = (LinearLayout) findViewById(R.id.ll_pulse_speed);
        mCbPulseSpeed = (CheckBox) findViewById(R.id.cb_pulse_speed);
        mTvManualCalibration = (TextView) findViewById(R.id.tv_manual_calibration);
        mTvCoefficientOfThePulse = (TextView) findViewById(R.id.tv_coefficient_of_the_pulse);
        mEtCoefficientOfThePulseValue = (EditText) findViewById(R.id.et_coefficient_of_the_pulse_value);
        mTvProportionValue = (TextView) findViewById(R.id.tv_proportion_value);
        mTvAutomaticCalibration = (TextView) findViewById(R.id.tv_automatic_calibration);
        mSsvAutomaticCalibrationSwitch = (SlideSwitchView) findViewById(R.id.ssv_automatic_calibration_switch);
        mTvErrorValue = (TextView) findViewById(R.id.tv_error_value);
        mBtnSub = (Button) findViewById(R.id.btn_sub);
        mEtErrorNumber = (EditText) findViewById(R.id.et_error_number);
        mBtnAdd = (Button) findViewById(R.id.btn_add);
        mTvPercent = (TextView) findViewById(R.id.tv_percent);
        mLlSimulationSpeed = (LinearLayout) findViewById(R.id.ll_simulation_speed);
        mCbSimulationSpeedStatus = (CheckBox) findViewById(R.id.cb_simulation_speed_status);
        mSsbSpeedValue = (SignSeekBar) findViewById(R.id.ssb_speed_value);
        mTvSave = (TextView) findViewById(R.id.tv_save);
    }

    @Override
    public ImageView getIvback() {
        return mIvback;
    }

    @Override
    public LinearLayout getLlPulseSpeed() {
        return mLlPulseSpeed;
    }

    @Override
    public CheckBox getCbPulseSpeed() {
        return mCbPulseSpeed;
    }

    @Override
    public TextView getTvManualCalibration() {
        return mTvManualCalibration;
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
    public TextView getTvProportionValue() {
        return mTvProportionValue;
    }

    @Override
    public TextView getTvAutomaticCalibration() {
        return mTvAutomaticCalibration;
    }

    @Override
    public SlideSwitchView getSsvAutomaticCalibrationSwitch() {
        return mSsvAutomaticCalibrationSwitch;
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
    public TextView getTvPercent() {
        return mTvPercent;
    }

    @Override
    public LinearLayout getLlSimulationSpeed() {
        return mLlSimulationSpeed;
    }

    @Override
    public CheckBox getCbSimulationSpeedStatus() {
        return mCbSimulationSpeedStatus;
    }

    @Override
    public SignSeekBar getSsbSpeedValue() {
        return mSsbSpeedValue;
    }

    @Override
    public TextView getTvSave() {
        return mTvSave;
    }
}
