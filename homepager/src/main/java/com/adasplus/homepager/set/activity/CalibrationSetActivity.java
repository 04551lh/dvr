package com.adasplus.homepager.set.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.TextView;

import com.adasplus.base.base.BaseActivity;
import com.adasplus.homepager.R;
import com.adasplus.homepager.set.mvp.contract.ICalibrationSetContract;
import com.adasplus.homepager.set.mvp.presenter.CalibrationSetPresenter;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class CalibrationSetActivity extends BaseActivity implements ICalibrationSetContract.View {
    private ImageView mIvCalibrationBack;
    private SwipeRefreshLayout mSwipeRefreshLayoutCalibrationSet;
    private ImageView mIvAutoCalibration;
    private ImageView mIvManualCalibrate;
    private TextView mTvStep;
    private EditText mEtStep;
    private ImageView mIvUp;
    private ImageView mIvLeft;
    private ImageView mIvRight;
    private ImageView mIvDown;
    private TextView mTvCalibrationSave;
    CalibrationSetPresenter calibrationSetPresenter;

    @Override
    protected void init(Bundle savedInstanceState) {
        calibrationSetPresenter = new CalibrationSetPresenter(this);
        calibrationSetPresenter.initData();
        calibrationSetPresenter.initListener();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_calibration_set;
    }

    @Override
    protected void initWidget() {
        mIvCalibrationBack =  findViewById(R.id.iv_calibration_back);
        mSwipeRefreshLayoutCalibrationSet = findViewById(R.id.swipeRefreshLayout_calibration_set);
        mIvAutoCalibration =  findViewById(R.id.iv_auto_calibration);
        mIvManualCalibrate =  findViewById(R.id.iv_manual_calibrate);
        mTvStep= findViewById(R.id.tv_step);
        mEtStep =  findViewById(R.id.et_step);
        mIvUp =  findViewById(R.id.iv_up);
        mIvLeft =  findViewById(R.id.iv_left);
        mIvRight =  findViewById(R.id.iv_right);
        mIvDown =  findViewById(R.id.iv_down);
        mTvCalibrationSave =  findViewById(R.id.tv_calibration_save);
    }

    @Override
    public ImageView getIvCalibrationBack() {
        return mIvCalibrationBack;
    }

    @Override
    public SwipeRefreshLayout getSwipeRefreshLayoutCalibrationSet() {
        return mSwipeRefreshLayoutCalibrationSet;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        calibrationSetPresenter.onMyDestroy();
    }

    @Override
    public ImageView getIvAutoCalibration() {
        return mIvAutoCalibration;
    }

    @Override
    public ImageView getIvManualCalibrate() {
        return mIvManualCalibrate;
    }

    @Override
    public TextView getTvStep() {
        return mTvStep;
    }

    @Override
    public EditText getEtStep() {
        return mEtStep;
    }

    @Override
    public ImageView getIvUp() {
        return mIvUp;
    }

    @Override
    public ImageView getIvLeft() {
        return mIvLeft;
    }

    @Override
    public ImageView getIvRight() {
        return mIvRight;
    }

    @Override
    public ImageView getIvDown() {
        return mIvDown;
    }

    @Override
    public TextView getTvCalibrationSave() {
        return mTvCalibrationSave;
    }

}
