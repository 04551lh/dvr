package com.adasplus.settings.mvp.presenter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adasplus.base.utils.ExceptionUtils;
import com.adasplus.base.utils.GsonUtils;
import com.adasplus.settings.R;
import com.adasplus.settings.activity.TimeSetActivity;
import com.adasplus.settings.mvp.contract.ITimeSetContract;
import com.adasplus.settings.mvp.model.TimeSetModel;
import com.adasplus.settings.network.SettingsWrapper;

import org.json.JSONException;
import org.json.JSONObject;

import rx.Subscriber;

/**
 * Author:刘净辉
 * Date : 2019/9/26 15:17
 * Description :
 */
public class TimeSetPresenter implements ITimeSetContract.Presenter, View.OnClickListener {

    private ITimeSetContract.View mTimeSetView;
    private TimeSetActivity mTimeSetActivity;
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
    private TimeSetModel mTimeSetModel;

    public TimeSetPresenter(ITimeSetContract.View view){
        mTimeSetView = view;
        mTimeSetActivity = (TimeSetActivity) view;
    }

    @Override
    public void initData() {

        mLlNetworkTime = mTimeSetView.getLlNetworkTime();
        mCbNetworkTime = mTimeSetView.getCbNetworkTime();
        mLlGpsTime = mTimeSetView.getLlGpsTime();
        mCbGpsTime = mTimeSetView.getCbGpsTime();
        mLlWhenManualCalibration = mTimeSetView.getLlWhenManualCalibration();
        mCbWhenManualCalibration = mTimeSetView.getCbWhenManualCalibration();
        mEtYear = mTimeSetView.getEtYear();
        mEtMonth = mTimeSetView.getEtMonth();
        mEtDay = mTimeSetView.getEtDay();
        mEtHours = mTimeSetView.getEtHours();
        mEtMinutes = mTimeSetView.getEtMinutes();
        mEtSeconds = mTimeSetView.getEtSeconds();

        //获取部标设备中的时间设置的信息
        SettingsWrapper.getInstance().getTimeSet().subscribe(new Subscriber<TimeSetModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ExceptionUtils.exceptionHandling(mTimeSetActivity,e);
            }

            @Override
            public void onNext(TimeSetModel timeSetModel) {
                mTimeSetModel = timeSetModel;

                TimeSetModel.AutoCalibrationTimeBean autoCalibrationTime = timeSetModel.getAutoCalibrationTime();
                TimeSetModel.ManualCalibrationTimeBean manualCalibrationTime = timeSetModel.getManualCalibrationTime();

                int calibrationTimeThroughGPS = autoCalibrationTime.getCalibrationTimeThroughGPS();
                int calibrationTimeThroughNet = autoCalibrationTime.getCalibrationTimeThroughNet();

                int manualCalibrationTimeEnable = manualCalibrationTime.getEnable();
                String date = manualCalibrationTime.getDate();
                String time = manualCalibrationTime.getTime();
                String[] split = date.split("-");
                String[] split1 = time.split(":");

                //设置是否选择了 GPS时间
                if (calibrationTimeThroughGPS == 1){
                    mCbGpsTime.setChecked(true);
                }else {
                    mCbGpsTime.setChecked(false);
                }

                //设置是否选择了 网络时间
                if (calibrationTimeThroughNet == 1){
                    mCbNetworkTime.setChecked(true);
                }else {
                    mCbNetworkTime.setChecked(false);
                }

                //设置是否选择了手动设置时间
                if (manualCalibrationTimeEnable == 1){
                    mCbWhenManualCalibration.setChecked(true);
                }else {
                    mCbWhenManualCalibration.setChecked(false);
                }

                mEtYear.setText(split[0]);
                mEtMonth.setText(split[1]);
                mEtDay.setText(split[2]);

                mEtHours.setText(split1[0]);
                mEtMinutes.setText(split1[1]);
                mEtSeconds.setText(split1[2]);
            }
        });
    }

    @Override
    public void initListener() {
        ImageView ivBack = mTimeSetView.getIvBack();
        TextView tvSave = mTimeSetView.getTvSave();

        ivBack.setOnClickListener(this);
        tvSave.setOnClickListener(this);
        mLlNetworkTime.setOnClickListener(this);
        mLlGpsTime.setOnClickListener(this);
        mLlWhenManualCalibration.setOnClickListener(this);
        mCbNetworkTime.setOnClickListener(this);
        mCbGpsTime.setOnClickListener(this);
        mCbWhenManualCalibration.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_back){
            mTimeSetActivity.finish();
        }else if (id == R.id.ll_network_time || id == R.id.cb_network_time){
            //选择联网时间
            mCbNetworkTime.setChecked(true);
            mCbGpsTime.setChecked(false);
            mCbWhenManualCalibration.setChecked(false);
        }else if (id == R.id.ll_gps_time || id == R.id.cb_gps_time){
            //选择 gps 时间
            mCbNetworkTime.setChecked(false);
            mCbGpsTime.setChecked(true);
            mCbWhenManualCalibration.setChecked(false);
        }else if (id == R.id.ll_when_manual_calibration || id == R.id.cb_when_manual_calibration){
            mCbNetworkTime.setChecked(false);
            mCbGpsTime.setChecked(false);
            mCbWhenManualCalibration.setChecked(true);
        }else if (id == R.id.tv_save){

            boolean cbNetworkTimeChecked = mCbNetworkTime.isChecked();
            boolean cbGpsTimeChecked = mCbGpsTime.isChecked();

            //判断 GPS时间 或 网络时间 其中一个是否选中，如果选中，是选择的自动校时
            // ，否则选择的是手动校时
            int enable = (cbNetworkTimeChecked || cbGpsTimeChecked) ? 1 : 0;
            int networkTimeEnable = cbNetworkTimeChecked ? 1 : 0;
            int gpsTimeEnable = cbGpsTimeChecked ? 1 : 0;
            int manualCalibrationEnable = mCbWhenManualCalibration.isChecked() ? 1 : 0;

            String year = mEtYear.getText().toString().trim();
            String months = mEtMonth.getText().toString().trim();
            String days = mEtDay.getText().toString().trim();

            String hours = mEtHours.getText().toString().trim();
            String minutes = mEtMinutes.getText().toString().trim();
            String seconds = mEtSeconds.getText().toString().trim();

            String date = year + "-" + months + "-" +days;
            String time = hours + ":" + minutes + ":"+seconds;

            if (mTimeSetModel != null){
                TimeSetModel.AutoCalibrationTimeBean autoCalibrationTime = mTimeSetModel.getAutoCalibrationTime();
                autoCalibrationTime.setEnable(enable);
                autoCalibrationTime.setCalibrationTimeThroughNet(networkTimeEnable);
                autoCalibrationTime.setCalibrationTimeThroughGPS(gpsTimeEnable);

                TimeSetModel.ManualCalibrationTimeBean manualCalibrationTime = mTimeSetModel.getManualCalibrationTime();
                manualCalibrationTime.setEnable(manualCalibrationEnable);
                manualCalibrationTime.setDate(date);
                manualCalibrationTime.setTime(time);

                String requestParams = GsonUtils.getInstance().toJson(mTimeSetModel);
                try {
                    JSONObject jsonObject = new JSONObject(requestParams);
                    SettingsWrapper.getInstance().updateTimeSet(jsonObject).subscribe(new Subscriber<TimeSetModel>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            ExceptionUtils.exceptionHandling(mTimeSetActivity,e);
                        }

                        @Override
                        public void onNext(TimeSetModel timeSetModel) {
                            Toast.makeText(mTimeSetActivity, "时间设置保存成功", Toast.LENGTH_SHORT).show();
                            mTimeSetActivity.finish();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }
    }
}
