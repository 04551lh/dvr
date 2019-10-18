package com.adasplus.homepager.set.mvp.presenter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adasplus.base.utils.ExceptionUtils;
import com.adasplus.base.view.SignSeekBar;
import com.adasplus.base.view.SlideSwitchView;
import com.adasplus.homepager.R;
import com.adasplus.homepager.network.HomeWrapper;
import com.adasplus.homepager.set.activity.SpeedSetActivity;
import com.adasplus.homepager.set.mvp.contract.ISpeedSetContract;
import com.adasplus.homepager.set.mvp.model.SpeedSetModel;

import org.json.JSONException;
import org.json.JSONObject;

import rx.Subscriber;

/**
 * Author:刘净辉
 * Date : 2019/9/25 11:32
 * Description :
 */
public class SpeedSetPresenter implements ISpeedSetContract.Presenter, View.OnClickListener, SlideSwitchView.OnSwitchStatusChangeListener {

    private ISpeedSetContract.View mSpeedSetView;
    private SpeedSetActivity mSpeedSetActivity;
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
    private TextView mETErrorNumber;
    private Button mBtnAdd;
    private TextView mTvPercent;
    private LinearLayout mLlSimulationSpeed;
    private CheckBox mCbSimulationSpeedStatus;
    private SignSeekBar mSsbSpeedValue;
    private TextView mTvSave;
    private int mUnClickColor;
    private boolean mIsAutomaticCalibration;
    private int mCurrentErrorValue;

    public SpeedSetPresenter(ISpeedSetContract.View view) {
        mSpeedSetView = view;
        mSpeedSetActivity = (SpeedSetActivity) view;
    }

    @Override
    public void initData() {
        mIvback = mSpeedSetView.getIvback();
        mLlPulseSpeed = mSpeedSetView.getLlPulseSpeed();
        mCbPulseSpeed = mSpeedSetView.getCbPulseSpeed();
        mTvManualCalibration = mSpeedSetView.getTvManualCalibration();
        mTvCoefficientOfThePulse = mSpeedSetView.getTvCoefficientOfThePulse();
        mEtCoefficientOfThePulseValue = mSpeedSetView.getEtCoefficientOfThePulseValue();
        mTvProportionValue = mSpeedSetView.getTvProportionValue();
        mTvAutomaticCalibration = mSpeedSetView.getTvAutomaticCalibration();
        mSsvAutomaticCalibrationSwitch = mSpeedSetView.getSsvAutomaticCalibrationSwitch();
        mTvErrorValue = mSpeedSetView.getTvErrorValue();
        mBtnSub = mSpeedSetView.getBtnSub();
        mETErrorNumber = mSpeedSetView.getEtErrorNumber();
        mBtnAdd = mSpeedSetView.getBtnAdd();
        mTvPercent = mSpeedSetView.getTvPercent();
        mLlSimulationSpeed = mSpeedSetView.getLlSimulationSpeed();
        mCbSimulationSpeedStatus = mSpeedSetView.getCbSimulationSpeedStatus();
        mSsbSpeedValue = mSpeedSetView.getSsbSpeedValue();
        mTvSave = mSpeedSetView.getTvSave();

        mUnClickColor = mSpeedSetActivity.getResources().getColor(R.color.split_line_color);
        mCurrentErrorValue = Integer.valueOf(mETErrorNumber.getText().toString());

        //获取速度设置，速度设置分为 脉冲速度（分为手动校准和自动校准） 和 模拟速度两种
        HomeWrapper.getInstance().getDefaultBySpeedSet().subscribe(new Subscriber<SpeedSetModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(SpeedSetModel speedSetModel) {
                SpeedSetModel.PulseSpeedBean pulseSpeed = speedSetModel.getPulseSpeed();
                int enable = pulseSpeed.getEnable();
                int autoCalibration = pulseSpeed.getAutoCalibration();
                int pulseCoefficient = pulseSpeed.getPulseCoefficient();
                int allowErrorValue = pulseSpeed.getAllowErrorValue();

                //设置脉冲系数
                mEtCoefficientOfThePulseValue.setText(String.valueOf(pulseCoefficient));
                //设置误差值
                mETErrorNumber.setText(String.valueOf(allowErrorValue));
                SpeedSetModel.SimulateSpeedBean simulateSpeed = speedSetModel.getSimulateSpeed();
                int value = simulateSpeed.getValue();
                //设置模拟速度的大小
                mSsbSpeedValue.setProgress(value);

                //判断是否选择的自动校准 1: 是 0: 否
                if (autoCalibration == 1) {
                    mIsAutomaticCalibration = true;
                    mSsvAutomaticCalibrationSwitch.setOpen(true);
                    startAutomaticCalibration();
                } else {
                    mIsAutomaticCalibration = false;
                    mSsvAutomaticCalibrationSwitch.setOpen(false);
                    stopAutomaticCalibration();
                }

                //判断是 脉冲速度 或 模拟速度 1: 脉冲速度 0: 模拟速度
                if (enable == 1) {
                    selectCalibration();
                } else {
                    simulationSpeed();
                }
            }
        });
    }

    private void startAutomaticCalibration() {
        mTvManualCalibration.setTextColor(mUnClickColor);
        mTvCoefficientOfThePulse.setTextColor(mUnClickColor);
        mTvProportionValue.setTextColor(mUnClickColor);
        mEtCoefficientOfThePulseValue.setEnabled(false);
        mEtCoefficientOfThePulseValue.setClickable(false);
        mTvAutomaticCalibration.setTextColor(Color.BLACK);
        mTvErrorValue.setTextColor(Color.BLACK);
        mBtnAdd.setEnabled(true);
        mBtnAdd.setClickable(true);
        mBtnSub.setEnabled(true);
        mBtnSub.setClickable(true);
    }

    @Override
    public void initListener() {
        mIvback.setOnClickListener(this);
        mLlPulseSpeed.setOnClickListener(this);
        mCbPulseSpeed.setOnClickListener(this);
        mSsvAutomaticCalibrationSwitch.setOnSwitchStatusChangeListener(this);
        mBtnAdd.setOnClickListener(this);
        mBtnSub.setOnClickListener(this);
        mCbSimulationSpeedStatus.setOnClickListener(this);
        mLlSimulationSpeed.setOnClickListener(this);
        mCbSimulationSpeedStatus.setOnClickListener(this);
        mTvSave.setOnClickListener(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        int id = v.getId();
        mCurrentErrorValue = Integer.valueOf(mETErrorNumber.getText().toString());
        if (id == R.id.iv_back) {
            mSpeedSetActivity.finish();
        } else if (id == R.id.ll_pulse_speed || id == R.id.cb_pulse_speed) {
            selectCalibration();
        } else if (id == R.id.ll_simulation_speed || id == R.id.cb_simulation_speed_status) {
            simulationSpeed();
        } else if (id == R.id.btn_add) {
            int mMaxErrorVaule = 20;
            if (mCurrentErrorValue == mMaxErrorVaule) {
                Toast.makeText(mSpeedSetActivity, "已经是最大的误差值", Toast.LENGTH_SHORT).show();
                return;
            }
            mCurrentErrorValue++;
            mETErrorNumber.setText(mCurrentErrorValue + "");
        } else if (id == R.id.btn_sub) {
            int mMinErrorVaule = 1;
            if (mCurrentErrorValue == mMinErrorVaule) {
                Toast.makeText(mSpeedSetActivity, "已经是最小的误差值", Toast.LENGTH_SHORT).show();
                return;
            }
            mCurrentErrorValue--;
            mETErrorNumber.setText(mCurrentErrorValue + "");
        } else if (id == R.id.tv_save) {
            saveSpeedSetData();
        }
    }

    private void saveSpeedSetData() {
        int isCheckPulseSpeed = mCbPulseSpeed.isChecked() ? 1 : 0;
        String pulseSpeedValue = mEtCoefficientOfThePulseValue.getText().toString();
        int autoCalibration = mSsvAutomaticCalibrationSwitch.isOpen() ? 1 : 0;
        String allowErrorValue = mETErrorNumber.getText().toString();
        int isCheckSimulation = mCbSimulationSpeedStatus.isChecked() ? 1 : 0;
        int currentSpeedProgress = mSsbSpeedValue.getProgress();

        JSONObject jsonObject = new JSONObject();
        JSONObject pulseSpeed = new JSONObject();
        JSONObject simulateSpeed = new JSONObject();
        try {
            pulseSpeed.put("enable", isCheckPulseSpeed);
            pulseSpeed.put("pulseCoefficient", Integer.valueOf(pulseSpeedValue));
            pulseSpeed.put("autoCalibration", autoCalibration);
            pulseSpeed.put("allowErrorValue", Integer.valueOf(allowErrorValue));

            simulateSpeed.put("enable", isCheckSimulation);
            simulateSpeed.put("value", currentSpeedProgress);

            jsonObject.put("pulseSpeed", pulseSpeed);
            jsonObject.put("simulateSpeed", simulateSpeed);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //保存最新的设置速度的最新配置
        HomeWrapper.getInstance().updateSpeedSet(jsonObject).subscribe(new Subscriber<SpeedSetModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ExceptionUtils.exceptionHandling(mSpeedSetActivity, e);
            }

            @Override
            public void onNext(SpeedSetModel speedSetModel) {
                Toast.makeText(mSpeedSetActivity, "速度设置保存成功", Toast.LENGTH_SHORT).show();
                mSpeedSetActivity.finish();
            }
        });
    }

    private void simulationSpeed() {
        //设置文本字体颜色
        mTvAutomaticCalibration.setTextColor(mUnClickColor);
        mTvErrorValue.setTextColor(mUnClickColor);
        mTvManualCalibration.setTextColor(mUnClickColor);
        mTvCoefficientOfThePulse.setTextColor(mUnClickColor);
        mTvProportionValue.setTextColor(mUnClickColor);
        mTvPercent.setTextColor(mUnClickColor);
        //设置按钮的状态
        mCbPulseSpeed.setChecked(false);
        mCbSimulationSpeedStatus.setChecked(true);
        mSsbSpeedValue.setEnabled(true);
        mEtCoefficientOfThePulseValue.setEnabled(false);
        mEtCoefficientOfThePulseValue.setClickable(false);
        mSsvAutomaticCalibrationSwitch.setEnabled(false);
        mSsvAutomaticCalibrationSwitch.setClickable(false);
        mBtnAdd.setEnabled(false);
        mBtnAdd.setClickable(false);
        mBtnSub.setEnabled(false);
        mBtnSub.setClickable(false);
    }

    private void selectCalibration() {
        mCbPulseSpeed.setChecked(true);
        mCbSimulationSpeedStatus.setChecked(false);
        mSsbSpeedValue.setEnabled(false);
        mTvPercent.setTextColor(Color.BLACK);
        mSsvAutomaticCalibrationSwitch.setEnabled(true);
        mSsvAutomaticCalibrationSwitch.setClickable(true);
        mBtnAdd.setEnabled(true);
        mBtnAdd.setClickable(true);
        mBtnSub.setEnabled(true);
        mBtnSub.setClickable(true);
        //判断是否打开的自动校准
        if (mIsAutomaticCalibration) {
            startAutomaticCalibration();
        } else {
            stopAutomaticCalibration();
        }
    }

    @Override
    public void onSwitchStatus(boolean status) {
        //自动校准开关的状态
        mIsAutomaticCalibration = status;
        if (status) {
            startAutomaticCalibration();
        } else {
            stopAutomaticCalibration();
        }
    }

    private void stopAutomaticCalibration() {
        mTvAutomaticCalibration.setTextColor(mUnClickColor);
        mTvErrorValue.setTextColor(mUnClickColor);
        mTvManualCalibration.setTextColor(Color.BLACK);
        mTvCoefficientOfThePulse.setTextColor(Color.BLACK);
        mTvProportionValue.setTextColor(Color.BLACK);
        mEtCoefficientOfThePulseValue.setEnabled(true);
        mEtCoefficientOfThePulseValue.setClickable(true);
        mBtnAdd.setEnabled(false);
        mBtnAdd.setClickable(false);
        mBtnSub.setEnabled(false);
        mBtnSub.setClickable(false);
    }
}
