package com.adasplus.settings.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adasplus.base.base.BaseActivity;
import com.adasplus.settings.R;
import com.adasplus.settings.mvp.contract.ICalibrationSetContract;
import com.adasplus.settings.mvp.presenter.CalibrationSetPresenter;

public class CalibrationSetActivity extends BaseActivity implements ICalibrationSetContract.View {

    private ImageView mIvBack;
    private CheckBox mCbAutoCalibration;
    private CheckBox mCbManualCalibrate;
    private EditText mEtCameraHigh;
    private Button mBtnUp;
    private Button mBtnLeft;
    private Button mBtnRight;
    private Button mBtnDown;
    private TextView mTvSave;
    private LinearLayout mLlAutoCalibrate;
    private LinearLayout mLlManualCalibrate;

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
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mCbAutoCalibration = (CheckBox) findViewById(R.id.cb_auto_calibration);
        mCbManualCalibrate = (CheckBox) findViewById(R.id.cb_manual_calibrate);
        mEtCameraHigh = (EditText) findViewById(R.id.et_camera_high);
        mBtnUp = (Button) findViewById(R.id.btn_up);
        mBtnLeft = (Button) findViewById(R.id.btn_left);
        mBtnRight = (Button) findViewById(R.id.btn_right);
        mBtnDown = (Button) findViewById(R.id.btn_down);
        mTvSave = (TextView) findViewById(R.id.tv_save);
        mLlAutoCalibrate = findViewById(R.id.ll_auto_calibrate);
        mLlManualCalibrate = findViewById(R.id.ll_manual_calibrate);
    }

    @Override
    public ImageView getIvBack() {
        return mIvBack;
    }

    @Override
    public CheckBox getCbAutoCalibration() {
        return mCbAutoCalibration;
    }

    @Override
    public CheckBox getCbManualCalibrate() {
        return mCbManualCalibrate;
    }

    @Override
    public EditText getEtCameraHigh() {
        return mEtCameraHigh;
    }

    @Override
    public Button getBtnUp() {
        return mBtnUp;
    }

    @Override
    public Button getBtnLeft() {
        return mBtnLeft;
    }

    @Override
    public Button getBtnRight() {
        return mBtnRight;
    }

    @Override
    public Button getBtnDown() {
        return mBtnDown;
    }

    @Override
    public TextView getTvSave() {
        return mTvSave;
    }

    @Override
    public LinearLayout getLlAutoCalibrate() {
        return mLlAutoCalibrate;
    }

    @Override
    public LinearLayout getLlManualCalibrate() {
        return mLlManualCalibrate;
    }
}
