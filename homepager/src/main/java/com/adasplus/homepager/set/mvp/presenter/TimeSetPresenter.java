package com.adasplus.homepager.set.mvp.presenter;

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

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import rx.Subscriber;

/**
 * Author:刘净辉
 * Date : 2019/9/26 15:17
 * Description :
 */
public class TimeSetPresenter implements ITimeSetContract.Presenter, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private ITimeSetContract.View mTimeSetView;
    private SwipeRefreshLayout mSwipeContainer;
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
    private String[] splitDate;
    private String[] splitTime;
    private int mAutoCalibrationTimeEnable;
    private int mManualCalibrationTimeEnable = 0;

    public TimeSetPresenter(ITimeSetContract.View view) {
        mTimeSetView = view;
        mTimeSetActivity = (TimeSetActivity) view;
    }

    @Override
    public void initData() {
        mIvNetworkTime = mTimeSetView.getIvNetworkTime();
        mSwipeContainer = mTimeSetActivity.getSwipeContainer();
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
        getNetworkData();
    }

    private void getNetworkData() {
        HomeWrapper.getInstance().getTimeSet().subscribe(new Subscriber<TimeSetModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ExceptionUtils.exceptionHandling(mTimeSetActivity, e);
                mSwipeContainer.setVisibility(View.VISIBLE);
                mSwipeContainer.setRefreshing(false); // close refresh animator
            }

            @Override
            public void onNext(TimeSetModel timeSetModel) {
                mTimeSetModel = timeSetModel;

                mAutoCalibrationTimeEnable = mTimeSetModel.getTimeCalibration();
                String date = mTimeSetModel.getDate();
                String time = mTimeSetModel.getTime();
                splitDate = date.split("-");
                splitTime = time.split(":");

                initSelectImage();

                mEtYear.setText(splitDate[0]);
                mEtMonth.setText(splitDate[1]);
                mEtDay.setText(splitDate[2]);

                mEtHours.setText(splitTime[0]);
                mEtMinutes.setText(splitTime[1]);
                mEtSeconds.setText(splitTime[2]);

                mSwipeContainer.setVisibility(View.VISIBLE);
                mSwipeContainer.setRefreshing(false); // close refresh animator
            }
        });
    }

    @Override
    public void initListener() {
        ImageView ivBack = mTimeSetView.getIvBack();
        TextView tvSave = mTimeSetView.getTvSave();

        ivBack.setOnClickListener(this);
        tvSave.setOnClickListener(this);
        mSwipeContainer.setOnRefreshListener(this);
        mIvAutomaticCorrectionWhen.setOnClickListener(this);
        mIvNetworkTime.setOnClickListener(this);
        mIvGpsTime.setOnClickListener(this);
        mIvWhenManualCalibration.setOnClickListener(this);
    }


    private void judgeTime(String old,String newly){
        if(!old.equals(newly)){
            mManualCalibrationTimeEnable = 1;
        }
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.iv_back) {
            mTimeSetActivity.finish();
        } else if (id == R.id.iv_automatic_correction_when) {
            initSelectImage();
        } else if (id == R.id.iv_network_time) {
            //选择联网时间

            initSelectImage();
        } else if (id == R.id.iv_gps_time) {
            //选择 gps 时间
            initSelectImage();
        } else if (id == R.id.iv_when_manual_calibration) {
            //设置是否选择了手动设置时间
            if(mManualCalibrationTimeEnable == 1){
                mManualCalibrationTimeEnable = 0;
            }else{
                mManualCalibrationTimeEnable = 1;
            }
            mSetImageResource(mIvAutomaticCorrectionWhen,mManualCalibrationTimeEnable);
        } else if (id == R.id.tv_save) {
            String year = mEtYear.getText().toString().trim();
            judgeTime(splitDate[0],year);
            String months = mEtMonth.getText().toString().trim();
            judgeTime(splitDate[0],months);
            String days = mEtDay.getText().toString().trim();
            judgeTime(splitDate[0],days);

            String hours = mEtHours.getText().toString().trim();
            judgeTime(splitDate[0],hours);
            String minutes = mEtMinutes.getText().toString().trim();
            judgeTime(splitDate[0],minutes);
            String seconds = mEtSeconds.getText().toString().trim();
            judgeTime(splitDate[0],seconds);


            String date = year + "-" + months + "-" + days;
            String time = hours + ":" + minutes + ":" + seconds;

            if (mTimeSetModel != null) {
                JSONObject jobj = new JSONObject();
                try {
                    jobj.put("timeCalibration", mAutoCalibrationTimeEnable);
                    jobj.put("timeSetFlag", mManualCalibrationTimeEnable);
                    jobj.put("date", date);
                    jobj.put("time", time);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String requestParams = GsonUtils.getInstance().toJson(jobj);
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
//            if (mCalibrationTimeThroughGPS == 1 || mCalibrationTimeThroughNet == 1) {
//                imageView.setImageResource(mIvNoRightSelectId);
//            }
            imageView.setImageResource(mIvNoSelectId);
        }
    }

    private void initSelectImage() {

        if (mAutoCalibrationTimeEnable == 1) {
            mIvNetworkTime.setImageResource(mIvSelectId);
        } else {
            mIvGpsTime.setImageResource(mIvNoSelectId);
        }
//        //设置是否选择了 自动校时
//        mSetImageResource(mIvAutomaticCorrectionWhen, mAutoCalibrationTimeEnable);
//        //设置是否选择了 GPS时间
//        mSetImageResource(mIvGpsTime, mAutoCalibrationTimeEnable);
//        //设置是否选择了 网络时间
//        mSetImageResource(mIvNetworkTime, mAutoCalibrationTimeEnable);
//        //设置是否选择了修改时间
//        mSetImageResource(mIvWhenManualCalibration, mManualCalibrationTimeEnable);
    }

    @Override
    public void onRefresh() {
        getNetworkData();
    }
}
