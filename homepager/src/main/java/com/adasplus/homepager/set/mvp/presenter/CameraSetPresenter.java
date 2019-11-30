package com.adasplus.homepager.set.mvp.presenter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.adasplus.base.utils.GsonUtils;
import com.adasplus.homepager.R;
import com.adasplus.homepager.network.HomeWrapper;
import com.adasplus.homepager.set.activity.CameraSetActivity;
import com.adasplus.homepager.set.mvp.contract.ICameraSetContract;
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


    private CameraSetModel mCameraSetModel;
    private int mSwitch;
    private int mSoundSwitch;
    private int mSoundType;


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
    }

    @Override
    public void initListener() {
        mIvCameraSetBack.setOnClickListener(this);
        mIvCameraSwitch.setOnClickListener(this);
        mTvCameraSave.setOnClickListener(this);
        mIvWarningSoundSwitch.setOnClickListener(this);
        mTvWarningSoundSave.setOnClickListener(this);
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

        }
    }
}
