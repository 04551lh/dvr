package com.adasplus.homepager.params.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.adasplus.base.base.BaseActivity;
import com.adasplus.base.network.ActivityPathConstant;
import com.adasplus.homepager.R;
import com.adasplus.homepager.params.mvp.contract.IParamsSetContract;
import com.adasplus.homepager.params.mvp.presenter.ParamsSetPresenter;
import com.alibaba.android.arouter.facade.annotation.Route;

@Route(path = ActivityPathConstant.PARAMS_PATH)
public class ParamsSetActivity extends BaseActivity implements IParamsSetContract.View {

    private ImageView mIvBack;
    private EditText mEtBumperDistance;
    private EditText mEtLeftWheelDistance;
    private EditText mEtRightWheelDistance;
    private EditText mEtFrontWheelDistance;
    private TextView mTvSave;

    @Override
    protected void init(Bundle savedInstanceState) {
        ParamsSetPresenter paramsSetPresenter = new ParamsSetPresenter(this);
        paramsSetPresenter.initData();
        paramsSetPresenter.initListener();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_params_set;
    }

    @Override
    protected void initWidget() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mEtBumperDistance = (EditText) findViewById(R.id.et_bumper_distance);
        mEtLeftWheelDistance = (EditText) findViewById(R.id.et_left_wheel_distance);
        mEtRightWheelDistance = (EditText) findViewById(R.id.et_right_wheel_distance);
        mEtFrontWheelDistance = (EditText) findViewById(R.id.et_front_wheel_distance);
        mTvSave = (TextView) findViewById(R.id.tv_save);
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

    @Override
    public EditText getEtFrontWheelDistance() {
        return mEtFrontWheelDistance;
    }

    @Override
    public TextView getTvSave() {
        return mTvSave;
    }
}
