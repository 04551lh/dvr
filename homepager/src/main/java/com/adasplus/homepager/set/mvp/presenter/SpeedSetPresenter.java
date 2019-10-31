package com.adasplus.homepager.set.mvp.presenter;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.adasplus.base.utils.ExceptionUtils;
import com.adasplus.base.view.SignSeekBar;
import com.adasplus.homepager.R;
import com.adasplus.homepager.network.HomeWrapper;
import com.adasplus.homepager.set.activity.SpeedSetActivity;
import com.adasplus.homepager.set.mvp.contract.ISpeedSetContract;
import com.adasplus.homepager.set.mvp.model.SpeedSetModel;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import rx.Subscriber;

/**
 * Author:刘净辉
 * Date : 2019/9/25 11:32
 * Description :
 */
public class SpeedSetPresenter implements ISpeedSetContract.Presenter, View.OnClickListener, SignSeekBar.OnProgressChangedListener, SwipeRefreshLayout.OnRefreshListener {
    private ISpeedSetContract.View mSpeedSetView;
    private SpeedSetActivity mSpeedSetActivity;
    private ImageView mIvSpeedBack;
    private SwipeRefreshLayout mSwipeRefreshLayoutSpeedSet;
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
    private TextView mTvSpeedSave;
    private int mUnClickColor;
    private int mClickColor;
    private int mClickTextColor;
    private int mCurrentErrorValue;
    private int mIvSelectId;
    private int mIvNoSelectId;
    private int mIvNoRightSelectId;
    private int mPulseSpeedEnable;
    private int mSimulateSpeedEnable;
    private int mAutoCalibration;
    private int mPulseCoefficient;
    private int mAllowErrorValue;
    private int mSpeedValue;

    private boolean mNetwork = false;

    public SpeedSetPresenter(ISpeedSetContract.View view) {
        mSpeedSetView = view;
        mSpeedSetActivity = (SpeedSetActivity) view;
    }

    @Override
    public void initData() {
        mIvSpeedBack = mSpeedSetView.getIvSpeedBack();
        mIvPulseSpeed = mSpeedSetView.getIvPulseSpeed();
        mSwipeRefreshLayoutSpeedSet = mSpeedSetActivity.getSwipeRefreshLayoutSpeedSet();
        mSwipeRefreshLayoutSpeedSet.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayoutSpeedSet.setProgressBackgroundColorSchemeResource(android.R.color.white);
        mTvManualCalibration = mSpeedSetView.getTvManualCalibration();
        mIvManualCalibration = mSpeedSetActivity.getIvManualCalibration();
        mTvCoefficientOfThePulse = mSpeedSetView.getTvCoefficientOfThePulse();
        mEtCoefficientOfThePulseValue = mSpeedSetView.getEtCoefficientOfThePulseValue();
        mTvAutomaticCalibration = mSpeedSetView.getTvAutomaticCalibration();
        mIvAutomaticCalibration = mSpeedSetView.getIvAutomaticCalibration();

        mTvErrorValue = mSpeedSetView.getTvErrorValue();
        mBtnSub = mSpeedSetView.getBtnSub();
        mEtErrorNumber = mSpeedSetActivity.getEtErrorNumber();
        mBtnAdd = mSpeedSetView.getBtnAdd();

        mIvSimulationSpeedStatus = mSpeedSetView.getIvSimulationSpeedStatus();
        mSsbSpeedValue = mSpeedSetView.getSsbSpeedValue();
        mTvCurrentSpeed = mSpeedSetActivity.getTvCurrentSpeed();
        mTvSpeedSave = mSpeedSetView.getTvSpeedSave();

        mUnClickColor = mSpeedSetActivity.getResources().getColor(R.color.under_line_color);
        mClickColor = mSpeedSetActivity.getResources().getColor(R.color.system_navigation_bar_color);
        mClickTextColor = mSpeedSetActivity.getResources().getColor(R.color.font_color_333);
        mCurrentErrorValue = Integer.valueOf(mEtErrorNumber.getText().toString());

        mIvSelectId = R.mipmap.switch_open_icon;
        mIvNoSelectId = R.mipmap.switch_close_icon;
        mIvNoRightSelectId = R.mipmap.switch_close_right_icon;
        getNetworkData();
    }

    //获取速度设置，速度设置分为 脉冲速度（分为手动校准和自动校准） 和 模拟速度两种
    private void getNetworkData() {
        HomeWrapper.getInstance().getDefaultBySpeedSet().subscribe(new Subscriber<SpeedSetModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mNetwork = false;
                mSwipeRefreshLayoutSpeedSet.setRefreshing(false); // close refresh animator
                mEtCoefficientOfThePulseValue.setClickable(false);
                mEtCoefficientOfThePulseValue.setEnabled(false);
                mEtErrorNumber.setEnabled(false);
                mEtErrorNumber.setClickable(false);
                ExceptionUtils.exceptionHandling(mSpeedSetActivity, e);
            }

            @Override
            public void onNext(SpeedSetModel speedSetModel) {
                mNetwork = true;
                mSwipeRefreshLayoutSpeedSet.setRefreshing(false); // close refresh animator
                SpeedSetModel.PulseSpeedBean pulseSpeed = speedSetModel.getPulseSpeed();
                mPulseSpeedEnable = pulseSpeed.getEnable();
                mAutoCalibration = pulseSpeed.getAutoCalibration();
                mPulseCoefficient = pulseSpeed.getPulseCoefficient();
                mAllowErrorValue = pulseSpeed.getAllowErrorValue();
                //设置脉冲系数
                mEtCoefficientOfThePulseValue.setText(String.valueOf(mPulseCoefficient));
                //设置误差值
                mEtErrorNumber.setText(String.valueOf(mAllowErrorValue));
                SpeedSetModel.SimulateSpeedBean simulateSpeed = speedSetModel.getSimulateSpeed();
                mSimulateSpeedEnable = simulateSpeed.getEnable();
                mSpeedValue = simulateSpeed.getValue();
                //设置模拟速度的大小
                mSsbSpeedValue.setProgress(mSpeedValue);
                mTvCurrentSpeed.setText(String.format("%s", mSpeedValue + mSpeedSetActivity.getResources().getString(R.string.set_speed_unit)));

                //判断是 脉冲速度 或 模拟速度 1: 脉冲速度 0: 模拟速度
                if (mPulseSpeedEnable == 0 && mSimulateSpeedEnable == 0) {
                    simulationSpeedClose();
                    selectCalibrationClose();
                } else if (mPulseSpeedEnable == 1) {
                    selectCalibration();
                } else if (mSimulateSpeedEnable == 1) {
                    simulationSpeed();
                }
            }
        });
    }

    private void startAutomaticCalibration() {
        if(mPulseCoefficient != 0){
            mTvAutomaticCalibration.setText(mSpeedSetActivity.getString(R.string.automatic_calibration) + "（" + (mPulseCoefficient) + "）");
        }
        mTvAutomaticCalibration.setTextColor(mClickTextColor);
        mIvAutomaticCalibration.setImageResource(mIvSelectId);
        mTvErrorValue.setTextColor(mClickTextColor);

        mTvManualCalibration.setTextColor(mUnClickColor);
        mIvManualCalibration.setImageResource(mIvNoSelectId);
        mTvCoefficientOfThePulse.setTextColor(mUnClickColor);
        mEtCoefficientOfThePulseValue.setTextColor(mUnClickColor);
        mEtCoefficientOfThePulseValue.setEnabled(false);
        mEtCoefficientOfThePulseValue.setClickable(false);
        mEtErrorNumber.setEnabled(true);
        mEtErrorNumber.setClickable(true);

    }

    private void stopAutomaticCalibration() {
        mTvAutomaticCalibration.setText(R.string.automatic_calibration);
        mTvAutomaticCalibration.setTextColor(mUnClickColor);
        mIvAutomaticCalibration.setImageResource(mIvNoSelectId);
        mTvErrorValue.setTextColor(mUnClickColor);

        mEtCoefficientOfThePulseValue.setClickable(true);
        mEtCoefficientOfThePulseValue.setEnabled(true);
        mEtErrorNumber.setEnabled(false);
        mEtErrorNumber.setClickable(false);

        mTvManualCalibration.setTextColor(mClickTextColor);
        mIvManualCalibration.setImageResource(mIvSelectId);
        mTvCoefficientOfThePulse.setTextColor(mClickTextColor);
        mEtCoefficientOfThePulseValue.setTextColor(mClickTextColor);

    }

    @Override
    public void initListener() {
        mIvSpeedBack.setOnClickListener(this);
        mIvPulseSpeed.setOnClickListener(this);
        mSwipeRefreshLayoutSpeedSet.setOnRefreshListener(this);
        mIvManualCalibration.setOnClickListener(this);
        mIvAutomaticCalibration.setOnClickListener(this);
        mBtnAdd.setOnClickListener(this);
        mBtnSub.setOnClickListener(this);
        mIvSimulationSpeedStatus.setOnClickListener(this);
        mTvSpeedSave.setOnClickListener(this);
        mSsbSpeedValue.setOnProgressChangedListener(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        int id = v.getId();
        mCurrentErrorValue = Integer.valueOf(mEtErrorNumber.getText().toString());
        if (id == R.id.iv_speed_back) {
            mSpeedSetActivity.finish();
        } else if (mNetwork) {
            if (id == R.id.iv_pulse_speed) {
                if (mPulseSpeedEnable == 1) {
                    mPulseSpeedEnable = 0;
                    selectCalibrationClose();
                } else {
                    mPulseSpeedEnable = 1;
                    mSimulateSpeedEnable = 0;
                    selectCalibration();
                }
            } else if (id == R.id.iv_simulation_speed_status) {
                if (mSimulateSpeedEnable == 1) {
                    mSimulateSpeedEnable = 0;
                    simulationSpeedClose();
                } else {
                    mSimulateSpeedEnable = 1;
                    mPulseSpeedEnable = 0;
                    simulationSpeed();
                }
            } else if (id == R.id.iv_manual_calibration) {
                if (mPulseSpeedEnable == 1) {
                    mAutoCalibration = 0;
                    stopAutomaticCalibration();
                }

            } else if (id == R.id.iv_automatic_calibration) {
                if (mPulseSpeedEnable == 1) {
                    mAutoCalibration = 1;
                    startAutomaticCalibration();
                }
            } else if (id == R.id.btn_add) {
                if (mPulseSpeedEnable == 1 && mAutoCalibration == 1) {
                    int maxErrorValue = 20;
                    if (mCurrentErrorValue == maxErrorValue) {
                        mSpeedSetActivity.showToast(R.string.speed_have_been_max_value);
                        return;
                    }
                    mCurrentErrorValue++;
                    mEtErrorNumber.setText(mCurrentErrorValue + "");
                }
            } else if (id == R.id.btn_sub) {
                if (mPulseSpeedEnable == 1 && mAutoCalibration == 1) {
                    int minErrorValue = 0;
                    if (mCurrentErrorValue == minErrorValue) {
                        mSpeedSetActivity.showToast(R.string.speed_have_been_min_value);
                        return;
                    }
                    mCurrentErrorValue--;
                    mEtErrorNumber.setText(mCurrentErrorValue + "");
                }
            } else if (id == R.id.tv_speed_save) {
                saveSpeedSetData();
            }
        } else {
            mSpeedSetActivity.showToast( R.string.disconnect_from_terminal_equipment);
        }
    }

    private void saveSpeedSetData() {
        if (TextUtils.isEmpty(mEtCoefficientOfThePulseValue.getText())) {
            mSpeedSetActivity.showToast(R.string.please_fill_in_coefficient_of_the_pulse);
            return;
        }
        int pulseSpeedValue = Integer.valueOf(mEtCoefficientOfThePulseValue.getText().toString());
        String allowErrorValue = mEtErrorNumber.getText().toString();
        int currentSpeedProgress = mSsbSpeedValue.getProgress();
        if (pulseSpeedValue < 0 || pulseSpeedValue > 100) {
            mSpeedSetActivity.showToast(R.string.coefficient_of_the_pulse);
            return;
        }
        JSONObject jsonObject = new JSONObject();
        JSONObject pulseSpeed = new JSONObject();
        JSONObject simulateSpeed = new JSONObject();
        try {
            pulseSpeed.put("enable", mPulseSpeedEnable);
            pulseSpeed.put("pulseCoefficient", pulseSpeedValue);
            pulseSpeed.put("autoCalibration", mAutoCalibration);
            pulseSpeed.put("allowErrorValue", Integer.valueOf(allowErrorValue));

            simulateSpeed.put("enable", mSimulateSpeedEnable);
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
        mIvSimulationSpeedStatus.setImageResource(mIvSelectId);
        mSsbSpeedValue.getConfigBuilder().secondTrackColor(mClickColor);
        mTvCurrentSpeed.setTextColor(mClickColor);
        //设置按钮的状态

        mSsbSpeedValue.setClickable(true);
        mSsbSpeedValue.setEnabled(true);
        mSsbSpeedValue.setSelected(true);
        mSsbSpeedValue.setFocusable(true);

        selectCalibrationClose();
    }

    private void simulationSpeedClose() {
        //设置文本字体颜色
        mIvSimulationSpeedStatus.setImageResource(mIvNoSelectId);
        mSsbSpeedValue.getConfigBuilder().secondTrackColor(mUnClickColor);
        mTvCurrentSpeed.setTextColor(mUnClickColor);

        mSsbSpeedValue.setClickable(false);
        mSsbSpeedValue.setEnabled(false);
        mSsbSpeedValue.setSelected(false);
        mSsbSpeedValue.setFocusable(false);

    }


    private void selectCalibrationClose() {
        //设置按钮的状态
        if (mAutoCalibration == 1) {
            mIvAutomaticCalibration.setImageResource(mIvNoRightSelectId);
            mIvManualCalibration.setImageResource(mIvNoSelectId);
        } else {
            mIvAutomaticCalibration.setImageResource(mIvNoSelectId);
            mIvManualCalibration.setImageResource(mIvNoRightSelectId);
        }
        mIvPulseSpeed.setImageResource(mIvNoSelectId);
        mTvAutomaticCalibration.setTextColor(mUnClickColor);
        mTvManualCalibration.setTextColor(mUnClickColor);

        mTvCoefficientOfThePulse.setTextColor(mUnClickColor);
        mEtCoefficientOfThePulseValue.setEnabled(false);
        mEtCoefficientOfThePulseValue.setClickable(false);
        mEtErrorNumber.setClickable(false);
        mEtErrorNumber.setEnabled(false);

        mEtCoefficientOfThePulseValue.setTextColor(mUnClickColor);
        mTvErrorValue.setTextColor(mUnClickColor);
    }

    private void selectCalibration() {
        simulationSpeedClose();

        mIvPulseSpeed.setImageResource(mIvSelectId);

        //判断是否选择的自动校准 1: 是 0: 否
        if (mAutoCalibration == 1) {
            startAutomaticCalibration();
        } else {
            stopAutomaticCalibration();
        }
    }


    @Override
    public void onProgressChanged(SignSeekBar signSeekBar, int progress, float progressFloat, boolean fromUser) {
        mTvCurrentSpeed.setText(String.format("%s", progress + mSpeedSetActivity.getResources().getString(R.string.set_speed_unit)));
    }

    @Override
    public void getProgressOnActionUp(SignSeekBar signSeekBar, int progress, float progressFloat) {
        mTvCurrentSpeed.setText(String.format("%s", progress + mSpeedSetActivity.getResources().getString(R.string.set_speed_unit)));
    }

    @Override
    public void getProgressOnFinally(SignSeekBar signSeekBar, int progress, float progressFloat, boolean fromUser) {
        mTvCurrentSpeed.setText(String.format("%s", progress + mSpeedSetActivity.getResources().getString(R.string.set_speed_unit)));
    }


    @Override
    public void onRefresh() {
        getNetworkData();
    }
}
