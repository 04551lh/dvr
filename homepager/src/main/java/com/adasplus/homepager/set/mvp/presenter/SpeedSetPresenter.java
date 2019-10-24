package com.adasplus.homepager.set.mvp.presenter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Handler;
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

import java.util.Locale;

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
    private ImageView mIvback;
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

    private int mUnClickColor;
    private int mClickColor;
    private int mClickTextColor;

    private int mCurrentErrorValue;

    private int mIvSelectId;
    private int mIvNoSelectId;
    private int mEnable;
    private int mAutoCalibration;
    private int mPulseCoefficient;
    private int mAllowErrorValue;

    public SpeedSetPresenter(ISpeedSetContract.View view) {
        mSpeedSetView = view;
        mSpeedSetActivity = (SpeedSetActivity) view;
    }

    @Override
    public void initData() {
        mIvback = mSpeedSetView.getIvback();
        mIvPulseSpeed = mSpeedSetView.getIvPulseSpeed();
        mSwipeContainer = mSpeedSetActivity.getSwipeContainer();
        mSwipeContainer.setColorSchemeResources(R.color.video_set_font_color,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeContainer.setProgressBackgroundColorSchemeResource(R.color.item_press_color);

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
        mTvSave = mSpeedSetView.getTvSave();

        mUnClickColor = mSpeedSetActivity.getResources().getColor(R.color.under_line_color);
        mClickColor = mSpeedSetActivity.getResources().getColor(R.color.system_navigation_bar_color);
        mClickTextColor = mSpeedSetActivity.getResources().getColor(R.color.font_color_333);
        mCurrentErrorValue = Integer.valueOf(mEtErrorNumber.getText().toString());

        mIvSelectId = R.mipmap.switch_open_icon;
        mIvNoSelectId = R.mipmap.switch_close_icon;

        getNetworkData();


    }

    //获取速度设置，速度设置分为 脉冲速度（分为手动校准和自动校准） 和 模拟速度两种
    private void getNetworkData(){

        HomeWrapper.getInstance().getDefaultBySpeedSet().subscribe(new Subscriber<SpeedSetModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ExceptionUtils.exceptionHandling(mSpeedSetActivity, e);
                mSwipeContainer.setVisibility(View.VISIBLE);
                mSwipeContainer.setRefreshing(false); // close refresh animator
            }

            @Override
            public void onNext(SpeedSetModel speedSetModel) {
                SpeedSetModel.PulseSpeedBean pulseSpeed = speedSetModel.getPulseSpeed();
                mEnable = pulseSpeed.getEnable();
                mAutoCalibration = pulseSpeed.getAutoCalibration();
                mPulseCoefficient = pulseSpeed.getPulseCoefficient();
                mAllowErrorValue = pulseSpeed.getAllowErrorValue();

                //设置脉冲系数
                mEtCoefficientOfThePulseValue.setText(String.valueOf(mPulseCoefficient));
                //设置误差值
                mEtErrorNumber.setText(String.valueOf(mAllowErrorValue));
                SpeedSetModel.SimulateSpeedBean simulateSpeed = speedSetModel.getSimulateSpeed();
                int value = simulateSpeed.getValue();
                //设置模拟速度的大小
                mSsbSpeedValue.setProgress(value);

                //判断是 脉冲速度 或 模拟速度 1: 脉冲速度 0: 模拟速度
                if (mEnable == 1) {
                    selectCalibration();
                } else {
                    simulationSpeed();
                }

                mSwipeContainer.setVisibility(View.VISIBLE);
                mSwipeContainer.setRefreshing(false); // close refresh animator
            }
        });
    }

    private void startAutomaticCalibration() {
        mTvAutomaticCalibration.setTextColor(  mClickTextColor);
        mIvAutomaticCalibration.setImageResource(mIvSelectId);
        mTvErrorValue.setTextColor(mClickTextColor);

        mTvManualCalibration.setTextColor(mUnClickColor);
        mIvManualCalibration.setImageResource(mIvNoSelectId);
        mTvCoefficientOfThePulse.setTextColor(mUnClickColor);
        mEtCoefficientOfThePulseValue.setTextColor(mUnClickColor);
        mEtCoefficientOfThePulseValue.setEnabled(false);
        mEtCoefficientOfThePulseValue.setClickable(false);

    }

    private void stopAutomaticCalibration() {
        mTvAutomaticCalibration.setTextColor(mUnClickColor);
        mIvAutomaticCalibration.setImageResource(mIvNoSelectId);
        mTvErrorValue.setTextColor(mUnClickColor);


        mTvManualCalibration.setTextColor(mClickTextColor);
        mIvManualCalibration.setImageResource(mIvSelectId);
        mTvCoefficientOfThePulse.setTextColor(mClickTextColor);
        mEtCoefficientOfThePulseValue.setTextColor(mClickTextColor);
        mEtCoefficientOfThePulseValue.setEnabled(true);
        mEtCoefficientOfThePulseValue.setClickable(true);

    }

    @Override
    public void initListener() {
        mIvback.setOnClickListener(this);
        mIvPulseSpeed.setOnClickListener(this);
        mSwipeContainer.setOnRefreshListener(this);
        mIvManualCalibration.setOnClickListener(this);
        mIvAutomaticCalibration.setOnClickListener(this);
        mBtnAdd.setOnClickListener(this);
        mBtnSub.setOnClickListener(this);
        mIvSimulationSpeedStatus.setOnClickListener(this);
        mTvSave.setOnClickListener(this);
        mSsbSpeedValue.setOnProgressChangedListener(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        int id = v.getId();
        mCurrentErrorValue = Integer.valueOf(mEtErrorNumber.getText().toString());
        if (id == R.id.iv_back) {
            mSpeedSetActivity.finish();
        } else if ( id == R.id.iv_pulse_speed) {
            mEnable = 1;
            selectCalibration();
        } else if ( id == R.id.iv_simulation_speed_status) {
            mEnable = 0;
            simulationSpeed();
        }else if(id == R.id.iv_manual_calibration) {
            if(mEnable == 1){
                mAutoCalibration =0;
                stopAutomaticCalibration();
            }

        }else if(id == R.id.iv_automatic_calibration) {
            if(mEnable == 1){
                mAutoCalibration =1;
                startAutomaticCalibration();
            }
        }else if (id == R.id.btn_add) {
            if(mEnable == 1 && mAutoCalibration == 1){
                int mMaxErrorVaule = 20;
                if (mCurrentErrorValue == mMaxErrorVaule) {
                    Toast.makeText(mSpeedSetActivity, "已经是最大的误差值", Toast.LENGTH_SHORT).show();
                    return;
                }
                mCurrentErrorValue++;
                mEtErrorNumber.setText(mCurrentErrorValue + "");
            }
        } else if (id == R.id.btn_sub) {
            if(mEnable == 1 && mAutoCalibration == 1) {
                int mMinErrorVaule = 0;
                if (mCurrentErrorValue == mMinErrorVaule) {
                    Toast.makeText(mSpeedSetActivity, "已经是最小的误差值", Toast.LENGTH_SHORT).show();
                    return;
                }
                mCurrentErrorValue--;
                mEtErrorNumber.setText(mCurrentErrorValue + "");
            }
        } else if (id == R.id.tv_save) {
            saveSpeedSetData();
        }
    }

    private void saveSpeedSetData() {
        String pulseSpeedValue = mEtCoefficientOfThePulseValue.getText().toString();
        String allowErrorValue = mEtErrorNumber.getText().toString();
        int currentSpeedProgress = mSsbSpeedValue.getProgress();

        JSONObject jsonObject = new JSONObject();
        JSONObject pulseSpeed = new JSONObject();
        JSONObject simulateSpeed = new JSONObject();
        try {
            pulseSpeed.put("enable", mEnable);
            pulseSpeed.put("pulseCoefficient", Integer.valueOf(pulseSpeedValue));
            pulseSpeed.put("autoCalibration", mAutoCalibration);
            pulseSpeed.put("allowErrorValue", Integer.valueOf(allowErrorValue));

            simulateSpeed.put("enable", mEnable);
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

        mSsbSpeedValue.getConfigBuilder().secondTrackColor(mClickColor);
        mSsbSpeedValue.setClickable(true);
        mSsbSpeedValue.setEnabled(true);
        mSsbSpeedValue.setSelected(true);
        mSsbSpeedValue.setFocusable(true);

        mIvPulseSpeed.setImageResource(mIvNoSelectId);
        mTvAutomaticCalibration.setTextColor(mUnClickColor);
        mTvManualCalibration.setTextColor(mUnClickColor);
        mIvAutomaticCalibration.setImageResource(mIvNoSelectId);
        mIvManualCalibration.setImageResource(mIvNoSelectId);
        mTvCoefficientOfThePulse.setTextColor(mUnClickColor);
        mEtCoefficientOfThePulseValue.setEnabled(false);
        mEtCoefficientOfThePulseValue.setClickable(false);
        mEtCoefficientOfThePulseValue.setTextColor(mUnClickColor);
        mTvErrorValue.setTextColor(mUnClickColor);

        //设置按钮的状态
    }

    private void selectCalibration() {
        mIvSimulationSpeedStatus.setImageResource(mIvNoSelectId);
        mSsbSpeedValue.getConfigBuilder().secondTrackColor(mUnClickColor);
        mTvCurrentSpeed.setTextColor(mUnClickColor);

        mSsbSpeedValue.getConfigBuilder().secondTrackColor(mUnClickColor);
        mSsbSpeedValue.setClickable(false);
        mSsbSpeedValue.setEnabled(false);
        mSsbSpeedValue.setSelected(false);
        mSsbSpeedValue.setFocusable(false);

        mIvPulseSpeed.setImageResource(mIvSelectId);

        //判断是否选择的自动校准 1: 是 0: 否
        if(mAutoCalibration == 1){
            startAutomaticCalibration();
        }else{
          stopAutomaticCalibration();
        }
    }


    @Override
    public void onProgressChanged(SignSeekBar signSeekBar, int progress, float progressFloat, boolean fromUser) {
        String s = String.format(Locale.CHINA, "%d", progress);
        mTvCurrentSpeed.setText(s + "km/h");
    }

    @Override
    public void getProgressOnActionUp(SignSeekBar signSeekBar, int progress, float progressFloat) {
        String s = String.format(Locale.CHINA, "%d", progress);
        mTvCurrentSpeed.setText(s + "km/h");
    }

    @Override
    public void getProgressOnFinally(SignSeekBar signSeekBar, int progress, float progressFloat, boolean fromUser) {
        String s = String.format(Locale.CHINA, "%d", progress);
        mTvCurrentSpeed.setText(s +"km/h");

    }



    @Override
    public void onRefresh() {
        getNetworkData();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                getNetworkData();
//                mSwipeContainer.setVisibility(View.VISIBLE);
//                mSwipeContainer.setRefreshing(false); // close refresh animator
//            }
//        }, 2000);

    }
}
