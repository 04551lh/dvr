package com.adasplus.homepager.params.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.adasplus.base.base.BaseActivity;
import com.adasplus.base.network.ActivityPathConstant;
import com.adasplus.homepager.R;
import com.adasplus.homepager.set.mvp.contract.IADASCalibrationSetContract;
import com.adasplus.homepager.set.mvp.presenter.ADASCalibrationPresenter;
import com.alibaba.android.arouter.facade.annotation.Route;

@Route(path = ActivityPathConstant.PARAMS_PATH)
public class ADASCalibrationActivity extends BaseActivity implements IADASCalibrationSetContract.View {

    private ImageView mIvADASCalibrationBack;
    private LinearLayout mLlADASCalibrationSet;
    private LinearLayout mLlADASCalibrationParamSet;

    @Override
    protected void init(Bundle savedInstanceState) {
        ADASCalibrationPresenter adasCalibrationPresenter = new ADASCalibrationPresenter(this);
        adasCalibrationPresenter.initData();
        adasCalibrationPresenter.initListener();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_adascalibration;
    }

    @Override
    protected void initWidget() {
        mIvADASCalibrationBack = findViewById(R.id.iv_adas_calibration_back);
        mLlADASCalibrationSet= findViewById(R.id.ll_adas_calibration_set);
        mLlADASCalibrationParamSet= findViewById(R.id.ll_adas_calibration_param_set);
    }

    @Override
    public ImageView getIvADASCalibrationBack() {
        return mIvADASCalibrationBack;
    }

    @Override
    public LinearLayout getLlADASCalibrationSet() {
        return mLlADASCalibrationSet;
    }

    @Override
    public LinearLayout getLlADASCalibrationParamSet() {
        return mLlADASCalibrationParamSet;
    }
}
