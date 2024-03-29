package com.adasplus.homepager.set.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.adasplus.base.base.BaseActivity;
import com.adasplus.base.network.ActivityPathConstant;
import com.adasplus.homepager.R;
import com.adasplus.homepager.set.mvp.contract.ICameraSetContract;
import com.adasplus.homepager.set.mvp.presenter.CameraSetPresenter;
import com.alibaba.android.arouter.facade.annotation.Route;

@Route(path = ActivityPathConstant.CAMERA_SET_PATH)
public class CameraSetActivity extends BaseActivity implements ICameraSetContract.View {
    private ImageView mIvCameraSetBack;

    private ImageView mIvCameraSwitch;
    private TextView mTvCameraSave;

    private ImageView mIvWarningSoundSwitch;
    private TextView mTvWarningSoundSave;

    private ImageView mIvWarningSixSwitch;
    private ImageView mIvWarningThreeSwitch;
    private ImageView mIvWarningDefaultSwitch;
    private TextView mTvWarningSecondSave;

    @Override
    protected void init(Bundle savedInstanceState) {
        CameraSetPresenter cameraSetPresenter = new CameraSetPresenter(this);
        cameraSetPresenter.initData();
        cameraSetPresenter.initListener();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_camera_set;
    }

    @Override
    protected void initWidget() {
        mIvCameraSetBack = findViewById(R.id.iv_camera_set_back);
        mIvCameraSwitch = findViewById(R.id.iv_camera_switch);
        mTvCameraSave = findViewById(R.id.tv_camera_save);
        mIvWarningSoundSwitch = findViewById(R.id.iv_warning_sound_switch);
        mTvWarningSoundSave = findViewById(R.id.tv_warning_sound__save);
        mIvWarningSixSwitch = findViewById(R.id.iv_warning_six_switch);
        mIvWarningThreeSwitch = findViewById(R.id.iv_warning_three_switch);
        mIvWarningDefaultSwitch = findViewById(R.id.iv_warning_default_switch);
        mTvWarningSecondSave = findViewById(R.id.tv_warning_second_save);
    }

    @Override
    public ImageView getIvCameraSetBack() {
        return mIvCameraSetBack;
    }

    @Override
    public ImageView getIvCameraSwitch() {
        return mIvCameraSwitch;
    }

    @Override
    public TextView getTvCameraSave() {
        return mTvCameraSave;
    }

    @Override
    public ImageView getIvWarningSoundSwitch() {
        return mIvWarningSoundSwitch;
    }

    @Override
    public TextView getWarningSoundSave() {
        return mTvWarningSoundSave;
    }

    @Override
    public ImageView getIvWarningSixSwitch() {
        return mIvWarningSixSwitch;
    }

    @Override
    public ImageView getIvWarningThreeSwitch() {
        return mIvWarningThreeSwitch;
    }

    @Override
    public ImageView getIvWarningDefaultSwitch() {
        return mIvWarningDefaultSwitch;
    }

    @Override
    public TextView getWarningSecondSave() {
        return mTvWarningSecondSave;
    }
}
