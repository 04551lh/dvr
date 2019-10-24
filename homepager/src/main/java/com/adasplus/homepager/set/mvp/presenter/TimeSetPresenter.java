package com.adasplus.homepager.set.mvp.presenter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.adasplus.base.utils.ExceptionUtils;
import com.adasplus.base.utils.GsonUtils;
import com.adasplus.homepager.R;
import com.adasplus.homepager.network.HomeWrapper;
import com.adasplus.homepager.set.activity.TimeSetActivity;
import com.adasplus.homepager.set.mvp.contract.ITimeSetContract;
import com.adasplus.homepager.set.mvp.model.TimeSetModel;

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
    private ImageView mIvNetworkTime;
    private ImageView mIvAutomaticCorrectionWhen;
    private ImageView mIvGpsTime;
    private ImageView mIvWhenManualCalibration;
    private EditText mEtYear;
    private EditText mEtMonth;
    private EditText mEtDay;
    private EditText mEtHours;
    private EditText mEtMinutes;
    private EditText mEtSeconds;
    private TimeSetModel mTimeSetModel;

    private int mIvSelectId;
    private int mIvNoSelectId;
    private int autoCalibrationTimeEnable;
    private int calibrationTimeThroughGPS;
    private int calibrationTimeThroughNet;
    private int manualCalibrationTimeEnable;

    public TimeSetPresenter(ITimeSetContract.View view) {
        mTimeSetView = view;
        mTimeSetActivity = (TimeSetActivity) view;
    }

    @Override
    public void initData() {
        mIvNetworkTime = mTimeSetView.getIvNetworkTime();
        mIvAutomaticCorrectionWhen = mTimeSetView.getIvAutomaticCorrectionWhen();
        mIvGpsTime = mTimeSetView.getIvGpsTime();
        mIvWhenManualCalibration = mTimeSetView.getIvWhenManualCalibration();
        mEtYear = mTimeSetView.getEtYear();
        mEtMonth = mTimeSetView.getEtMonth();
        mEtDay = mTimeSetView.getEtDay();
        mEtHours = mTimeSetView.getEtHours();
        mEtMinutes = mTimeSetView.getEtMinutes();
        mEtSeconds = mTimeSetView.getEtSeconds();
        mIvSelectId = R.mipmap.switch_open_icon;
        mIvNoSelectId = R.mipmap.switch_close_icon;

        //获取部标设备中的时间设置的信息
        HomeWrapper.getInstance().getTimeSet().subscribe(new Subscriber<TimeSetModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ExceptionUtils.exceptionHandling(mTimeSetActivity, e);
            }

            @Override
            public void onNext(TimeSetModel timeSetModel) {
                mTimeSetModel = timeSetModel;

                TimeSetModel.AutoCalibrationTimeBean autoCalibrationTime = timeSetModel.getAutoCalibrationTime();
                TimeSetModel.ManualCalibrationTimeBean manualCalibrationTime = timeSetModel.getManualCalibrationTime();

                autoCalibrationTimeEnable = autoCalibrationTime.getEnable();
                calibrationTimeThroughGPS = autoCalibrationTime.getCalibrationTimeThroughGPS();
                calibrationTimeThroughNet = autoCalibrationTime.getCalibrationTimeThroughNet();

                manualCalibrationTimeEnable = manualCalibrationTime.getEnable();
                String date = manualCalibrationTime.getDate();
                String time = manualCalibrationTime.getTime();
                String[] split = date.split("-");
                String[] split1 = time.split(":");

                initSelectImage();

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
        mIvAutomaticCorrectionWhen.setOnClickListener(this);
        mIvNetworkTime.setOnClickListener(this);
        mIvGpsTime.setOnClickListener(this);
        mIvWhenManualCalibration.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.iv_back) {
            mTimeSetActivity.finish();
        } else if (id == R.id.iv_automatic_correction_when) {
            autoCalibrationTimeEnable = 1;
            manualCalibrationTimeEnable = 0;
            initSelectImage();
        } else if (id == R.id.iv_network_time) {
            //选择联网时间
            if (autoCalibrationTimeEnable == 1) {
                calibrationTimeThroughNet = 1;
                calibrationTimeThroughGPS = 0;
                initSelectImage();
            }
        } else if (id == R.id.iv_gps_time) {
            //选择 gps 时间
            if (autoCalibrationTimeEnable == 1) {
                calibrationTimeThroughNet = 0;
                calibrationTimeThroughGPS = 1;
                initSelectImage();
            }
        } else if (id == R.id.iv_when_manual_calibration) {
            autoCalibrationTimeEnable = 0;
            manualCalibrationTimeEnable = 1;
            //设置是否选择了 自动校时
            mSetImageResource(mIvAutomaticCorrectionWhen, autoCalibrationTimeEnable);
            //设置是否选择了 GPS时间
            if (calibrationTimeThroughGPS == 1) {
                mIvGpsTime.setImageResource(mIvNoSelectId);
            } else {
                mIvGpsTime.setImageResource(mIvNoSelectId);
            }
            //设置是否选择了 网络时间
            if (calibrationTimeThroughGPS == 1) {
                mIvNetworkTime.setImageResource(mIvNoSelectId);
            } else {
                mIvNetworkTime.setImageResource(mIvNoSelectId);
            }
            //设置是否选择了手动设置时间
            mSetImageResource(mIvWhenManualCalibration, manualCalibrationTimeEnable);
        } else if (id == R.id.tv_save) {
            String year = mEtYear.getText().toString().trim();
            String months = mEtMonth.getText().toString().trim();
            String days = mEtDay.getText().toString().trim();

            String hours = mEtHours.getText().toString().trim();
            String minutes = mEtMinutes.getText().toString().trim();
            String seconds = mEtSeconds.getText().toString().trim();

            String date = year + "-" + months + "-" + days;
            String time = hours + ":" + minutes + ":" + seconds;

            if (mTimeSetModel != null) {
                TimeSetModel.AutoCalibrationTimeBean autoCalibrationTime = mTimeSetModel.getAutoCalibrationTime();
                autoCalibrationTime.setEnable(autoCalibrationTimeEnable);
                autoCalibrationTime.setCalibrationTimeThroughNet(calibrationTimeThroughNet);
                autoCalibrationTime.setCalibrationTimeThroughGPS(calibrationTimeThroughGPS);

                TimeSetModel.ManualCalibrationTimeBean manualCalibrationTime = mTimeSetModel.getManualCalibrationTime();
                manualCalibrationTime.setEnable(manualCalibrationTimeEnable);
                manualCalibrationTime.setDate(date);
                manualCalibrationTime.setTime(time);

                String requestParams = GsonUtils.getInstance().toJson(mTimeSetModel);
                try {
                    JSONObject jsonObject = new JSONObject(requestParams);
                    HomeWrapper.getInstance().updateTimeSet(jsonObject).subscribe(new Subscriber<TimeSetModel>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            ExceptionUtils.exceptionHandling(mTimeSetActivity, e);
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


    /*封装了根据状态1：选中，0不选择设置选中状态*/
    private void mSetImageResource(ImageView imageView, int enable) {
        if (enable == 1) {
            imageView.setImageResource(mIvSelectId);
        } else {
            imageView.setImageResource(mIvNoSelectId);
        }
    }

    private void initSelectImage() {
        //设置是否选择了 自动校时
        mSetImageResource(mIvAutomaticCorrectionWhen, autoCalibrationTimeEnable);

        //设置是否选择了 GPS时间
        mSetImageResource(mIvGpsTime, calibrationTimeThroughGPS);

        //设置是否选择了 网络时间
        mSetImageResource(mIvNetworkTime, calibrationTimeThroughNet);

        //设置是否选择了手动设置时间

        mSetImageResource(mIvWhenManualCalibration, manualCalibrationTimeEnable);
    }
}
