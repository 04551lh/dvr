package com.adasplus.homepager.set.mvp.presenter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.adasplus.base.utils.GsonUtils;
import com.adasplus.homepager.R;
import com.adasplus.homepager.network.HomeWrapper;
import com.adasplus.homepager.set.activity.CameraSetActivity;
import com.adasplus.homepager.set.mvp.contract.ICameraSetContract;
import com.adasplus.homepager.set.mvp.model.AlarmTTSIntervalSwitchModel;
import com.adasplus.homepager.set.mvp.model.CameraSetModel;
import com.adasplus.homepager.set.mvp.model.ManualWarningSoundModel;

import org.json.JSONException;
import org.json.JSONObject;

import rx.Subscriber;

/**
 * Created by dell on 2019/11/15 16:40
 * Description:
 * Emain: 1187278976@qq.com
 */
public class CameraSetPresenter implements ICameraSetContract.Presenter, View.OnClickListener {
    private ICameraSetContract.View mCameraSetView;
    private CameraSetActivity mCameraSetActivity;

    private ImageView mIvCameraSetBack;
    private ImageView mIvCameraSwitch;
    private TextView mTvCameraSave;

    private ImageView mIvWarningSoundSwitch;
    private TextView mTvWarningSoundSave;

    private ImageView mIvWarningSixSwitch;
    private ImageView mIvWarningThreeSwitch;
    private ImageView mIvWarningDefaultSwitch;
    private TextView mTvWarningSecondSave;


    private CameraSetModel mCameraSetModel;
    private AlarmTTSIntervalSwitchModel mAlarmTTSIntervalSwitchModel;
    private int mSwitch;
    private int mSoundSwitch;
    private int mSoundType;

    private int mSmokingAndCallPhoneSwitchEnable;
    private int mCameraErrorAndNoDriverSwitchEnable;
    private int mReturnDefaultSwitchEnable;




    public CameraSetPresenter(ICameraSetContract.View view) {
        mCameraSetView = view;
        mCameraSetActivity = (CameraSetActivity) mCameraSetView;
    }

    @Override
    public void initData() {
        mIvCameraSetBack = mCameraSetView.getIvCameraSetBack();
        mIvCameraSwitch = mCameraSetView.getIvCameraSwitch();
        mTvCameraSave = mCameraSetView.getTvCameraSave();

        mIvWarningSoundSwitch = mCameraSetView.getIvWarningSoundSwitch();
        mTvWarningSoundSave = mCameraSetView.getWarningSoundSave();

        mIvWarningSixSwitch = mCameraSetView.getIvWarningSixSwitch();
        mIvWarningThreeSwitch = mCameraSetView.getIvWarningThreeSwitch();
        mIvWarningDefaultSwitch = mCameraSetView.getIvWarningDefaultSwitch();
        mTvWarningSecondSave = mCameraSetView.getWarningSecondSave();

        mCameraSetModel = new CameraSetModel();
        mSwitch = 0;
        mSoundSwitch = 1;
        mSoundType = 0;
        mTvWarningSoundSave.setText("滴滴");
        mTvWarningSoundSave.setVisibility(View.GONE);

        HomeWrapper.getInstance().getManualWarningSound().subscribe(new Subscriber<ManualWarningSoundModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ManualWarningSoundModel warningSoundModel) {
                if (warningSoundModel.getSwitchStatus() == 1) {
                    mIvWarningSoundSwitch.setImageResource(R.mipmap.switch_open_icon);
                    mSoundSwitch = 1;
                    mSoundType = 0;
                    mTvWarningSoundSave.setVisibility(View.VISIBLE);
                } else {
                    mIvWarningSoundSwitch.setImageResource(R.mipmap.switch_close_icon);
                    mSoundSwitch = 0;
                    mSoundType = 0;
                    mTvWarningSoundSave.setVisibility(View.GONE);
                }
            }
        });
        HomeWrapper.getInstance().getAlarmSwitchData().subscribe(new Subscriber<AlarmTTSIntervalSwitchModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(AlarmTTSIntervalSwitchModel alarmTTSIntervalSwitchModel) {
                mAlarmTTSIntervalSwitchModel = alarmTTSIntervalSwitchModel;
                if (alarmTTSIntervalSwitchModel.getCameraErrorAndNoDriverSwitchEnable() == 1) {
                    mIvWarningThreeSwitch.setImageResource(R.mipmap.switch_open_icon);
                    mCameraErrorAndNoDriverSwitchEnable = 1;
                } else {
                    mIvWarningThreeSwitch.setImageResource(R.mipmap.switch_close_icon);
                    mCameraErrorAndNoDriverSwitchEnable = 0;
                }
                if (alarmTTSIntervalSwitchModel.getReturnDefaultSwitchEnable() == 1) {
                    mIvWarningDefaultSwitch.setImageResource(R.mipmap.switch_open_icon);
                    mReturnDefaultSwitchEnable = 1;
                } else {
                    mIvWarningDefaultSwitch.setImageResource(R.mipmap.switch_close_icon);
                    mReturnDefaultSwitchEnable = 0;
                }
                if (alarmTTSIntervalSwitchModel.getSmokingAndCallPhoneSwitchEnable() == 1) {
                    mIvWarningSixSwitch.setImageResource(R.mipmap.switch_open_icon);
                    mSmokingAndCallPhoneSwitchEnable = 1;
                } else {
                    mIvWarningSixSwitch.setImageResource(R.mipmap.switch_close_icon);
                    mSmokingAndCallPhoneSwitchEnable = 0;
                }
            }
        });
    }

    @Override
    public void initListener() {
        mIvCameraSetBack.setOnClickListener(this);

        mIvCameraSwitch.setOnClickListener(this);
        mTvCameraSave.setOnClickListener(this);

        mIvWarningSoundSwitch.setOnClickListener(this);
        mTvWarningSoundSave.setOnClickListener(this);

        mIvWarningSixSwitch.setOnClickListener(this);
        mIvWarningThreeSwitch.setOnClickListener(this);
        mIvWarningDefaultSwitch.setOnClickListener(this);
        mTvWarningSecondSave.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_camera_set_back) {
            mCameraSetActivity.finish();
        } else if (id == R.id.iv_camera_switch) {
            if (mSwitch == 0) {
                mIvCameraSwitch.setImageResource(R.mipmap.switch_open_icon);
                mSwitch = 1;
            } else {
                mIvCameraSwitch.setImageResource(R.mipmap.switch_close_icon);
                mSwitch = 0;
            }
        } else if (id == R.id.tv_camera_save) {
            mCameraSetModel.setSwitchX(mSwitch);
            String json = GsonUtils.getInstance().toJson(mCameraSetModel);
            try {
                JSONObject jobj = new JSONObject(json);
                HomeWrapper.getInstance().updateCameraSet(jobj).subscribe(new Subscriber<CameraSetModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CameraSetModel cameraSetModel) {
                        mCameraSetActivity.showToast(R.string.setting_success);
                        mCameraSetActivity.finish();
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else if (id == R.id.iv_warning_sound_switch) {
            if (mSoundSwitch == 0) {
                mIvWarningSoundSwitch.setImageResource(R.mipmap.switch_open_icon);
                mSoundSwitch = 1;
                mSoundType = 0;
                mTvWarningSoundSave.setVisibility(View.VISIBLE);
            } else {
                mIvWarningSoundSwitch.setImageResource(R.mipmap.switch_close_icon);
                mSoundSwitch = 0;
                mSoundType = 0;
                mTvWarningSoundSave.setVisibility(View.GONE);
            }
            try {
                JSONObject jobj = new JSONObject();
                jobj.put("switchStatus", mSoundSwitch);
                jobj.put("do", mSoundType);
                HomeWrapper.getInstance().updateManualWarningSound(jobj).subscribe(new Subscriber<ManualWarningSoundModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ManualWarningSoundModel warningSoundModel) {
                        mCameraSetActivity.showToast(R.string.setting_success);
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (id == R.id.tv_warning_sound__save) {
            mSoundType = 1;
            try {
                JSONObject jobj = new JSONObject();
                jobj.put("switchStatus", mSoundSwitch);
                jobj.put("do", mSoundType);
                HomeWrapper.getInstance().updateManualWarningSound(jobj).subscribe(new Subscriber<ManualWarningSoundModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ManualWarningSoundModel warningSoundModel) {
                        if (mTvWarningSoundSave.getText().toString().trim().equals("保存")) {
                            mCameraSetActivity.showToast(R.string.setting_success);
                            mCameraSetActivity.finish();
                        }
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else if (id == R.id.iv_warning_six_switch) {
            if (mSmokingAndCallPhoneSwitchEnable == 0) {
                mIvWarningSixSwitch.setImageResource(R.mipmap.switch_open_icon);
                mIvWarningDefaultSwitch.setImageResource(R.mipmap.switch_close_icon);
                mSmokingAndCallPhoneSwitchEnable = 1;
                mReturnDefaultSwitchEnable = 0;
                saveWarningAlarm();
            } else {
                mIvWarningSixSwitch.setImageResource(R.mipmap.switch_close_icon);
                mSmokingAndCallPhoneSwitchEnable = 0;
                saveWarningAlarm();
            }
        } else if (id == R.id.iv_warning_three_switch) {
            if (mCameraErrorAndNoDriverSwitchEnable == 0) {
                mIvWarningThreeSwitch.setImageResource(R.mipmap.switch_open_icon);
                mIvWarningDefaultSwitch.setImageResource(R.mipmap.switch_close_icon);
                mCameraErrorAndNoDriverSwitchEnable = 1;
                mReturnDefaultSwitchEnable = 0;
                saveWarningAlarm();
            } else {
                mIvWarningThreeSwitch.setImageResource(R.mipmap.switch_close_icon);
                mCameraErrorAndNoDriverSwitchEnable = 0;
                saveWarningAlarm();
            }
        } else if (id == R.id.iv_warning_default_switch) {
            if (mReturnDefaultSwitchEnable == 0) {
                mIvWarningDefaultSwitch.setImageResource(R.mipmap.switch_open_icon);
                mIvWarningThreeSwitch.setImageResource(R.mipmap.switch_close_icon);
                mIvWarningSixSwitch.setImageResource(R.mipmap.switch_close_icon);
                mReturnDefaultSwitchEnable = 1;
                mCameraErrorAndNoDriverSwitchEnable = 0;
                mSmokingAndCallPhoneSwitchEnable = 0;
                saveWarningAlarm();
            } else {
                mIvWarningDefaultSwitch.setImageResource(R.mipmap.switch_close_icon);
                mReturnDefaultSwitchEnable = 0;
                saveWarningAlarm();
            }
        } else if (id == R.id.tv_warning_second_save) {
            saveWarningAlarm();
        }
    }

    private void saveWarningAlarm(){
        mAlarmTTSIntervalSwitchModel.setCameraErrorAndNoDriverSwitchEnable(mCameraErrorAndNoDriverSwitchEnable);
        mAlarmTTSIntervalSwitchModel.setReturnDefaultSwitchEnable(mReturnDefaultSwitchEnable);
        mAlarmTTSIntervalSwitchModel.setSmokingAndCallPhoneSwitchEnable(mSmokingAndCallPhoneSwitchEnable);

        String json = GsonUtils.getInstance().toJson(mAlarmTTSIntervalSwitchModel);
        try {
            JSONObject jobj = new JSONObject(json);
            HomeWrapper.getInstance().updateAlarmSwitchData(jobj).subscribe(new Subscriber<AlarmTTSIntervalSwitchModel>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(AlarmTTSIntervalSwitchModel alarmTTSIntervalSwitchModel) {
                    mCameraSetActivity.showToast(R.string.setting_success);
//                        mCameraSetActivity.finish();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
