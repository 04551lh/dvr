package com.adasplus.homepager.params.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.adasplus.base.base.BaseActivity;
import com.adasplus.homepager.R;
import com.adasplus.homepager.params.mvp.contract.IParamsSetContract;
import com.adasplus.homepager.params.mvp.presenter.ParamsSetPresenter;

public class ParamsSetActivity extends BaseActivity implements IParamsSetContract.View {

    private ImageView mIvBack;
    private EditText mEtCameraHeight;
    private EditText mEtBumperDistance;
    private EditText mEtLeftWheelDistance;
    private EditText mEtRightWheelDistance;
//    private EditText mEtFrontWheelDistance;
    private TextView mTvSaveParamsSetInfo;
    private TextView mTvRestoreTheDefaultParams;

    @Override
    protected void init(Bundle savedInstanceState) {
        ParamsSetPresenter paramsSetPresenter = new ParamsSetPresenter(this);
        paramsSetPresenter.initWidget();
        paramsSetPresenter.initData();
        paramsSetPresenter.initListener();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_params_set;
    }

    @Override
    protected void initWidget() {
        mIvBack = findViewById(R.id.iv_back);
        mEtCameraHeight = findViewById(R.id.et_camera_height);
        mEtBumperDistance = findViewById(R.id.et_bumper_distance);
        mEtLeftWheelDistance = findViewById(R.id.et_left_wheel_distance);
        mEtRightWheelDistance = findViewById(R.id.et_right_wheel_distance);
//        mEtFrontWheelDistance = findViewById(R.id.et_front_wheel_distance);
        mTvSaveParamsSetInfo = findViewById(R.id.tv_save_params_set_info);
        mTvRestoreTheDefaultParams = findViewById(R.id.tv_restore_the_default_params);
    }

    @Override
    public ImageView getIvBack() {
        return mIvBack;
    }

    @Override
    public EditText getEtBumperDistance() {
        return mEtBumperDistance;
    }

    @Override
    public EditText getEtLeftWheelDistance() {
        return mEtLeftWheelDistance;
    }

    @Override
    public EditText getEtRightWheelDistance() {
        return mEtRightWheelDistance;
    }

//    @Override
//    public EditText getEtFrontWheelDistance() {
//        return mEtFrontWheelDistance;
//    }

    @Override
    public EditText getEtCameraHeight() {
        return mEtCameraHeight;
    }

    @Override
    public TextView getTvSaveParamsSet() {
        return mTvSaveParamsSetInfo;
    }

    @Override
    public TextView getTvRestoreTheDefaultParams() {
        return mTvRestoreTheDefaultParams;
    }

}
