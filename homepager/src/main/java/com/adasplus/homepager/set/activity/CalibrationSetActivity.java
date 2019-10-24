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
    private ImageView mIvBack;
    private SwipeRefreshLayout mSwipeContainer;
    private ImageView mIvAutoCalibration;
    private ImageView mIvManualCalibrate;
    private TextView mTvCameraHeight;
    private EditText mEtCameraHeight;
    private ImageView mIvUp;
    private ImageView mIvLeft;
    private ImageView mIvRight;
    private ImageView mIvDown;
    private TextView mTvSave;

    @Override
    protected void init(Bundle savedInstanceState) {
        CalibrationSetPresenter calibrationSetPresenter = new CalibrationSetPresenter(this);
        calibrationSetPresenter.initData();
        calibrationSetPresenter.initListener();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_calibration_set;
    }

    @Override
    protected void initWidget() {
        mIvBack =  findViewById(R.id.iv_back);
        mSwipeContainer = findViewById(R.id.swipe_container);
        mIvAutoCalibration =  findViewById(R.id.iv_auto_calibration);
        mIvManualCalibrate =  findViewById(R.id.iv_manual_calibrate);
        mTvCameraHeight= findViewById(R.id.tv_camera_height);
        mEtCameraHeight =  findViewById(R.id.et_camera_height);
        mIvUp =  findViewById(R.id.iv_up);
        mIvLeft =  findViewById(R.id.iv_left);
        mIvRight =  findViewById(R.id.iv_right);
        mIvDown =  findViewById(R.id.iv_down);
        mTvSave =  findViewById(R.id.tv_save);
    }

    @Override
    public ImageView getIvBack() {
        return mIvBack;
    }

    @Override
    public SwipeRefreshLayout getSwipeContainer() {
        return mSwipeContainer;
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
    public TextView getTvCameraHeight() {
        return mTvCameraHeight;
    }

    @Override
    public EditText getEtCameraHeight() {
        return mEtCameraHeight;
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
    public TextView getTvSave() {
        return mTvSave;
    }

}
