package com.adasplus.settings.mvp.presenter;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adasplus.base.dialog.BasicDialog;
import com.adasplus.base.dialog.CommonDialog;
import com.adasplus.base.utils.ExceptionUtils;
import com.adasplus.base.utils.GsonUtils;
import com.adasplus.settings.R;
import com.adasplus.settings.activity.CalibrationSetActivity;
import com.adasplus.settings.mvp.contract.ICalibrationSetContract;
import com.adasplus.settings.mvp.model.CalibrationSetModel;
import com.adasplus.settings.network.SettingsWrapper;

import org.json.JSONException;
import org.json.JSONObject;

import rx.Subscriber;

/**
 * Author:刘净辉
 * Date : 2019/9/26 19:34
 * Description :
 */
public class CalibrationSetPresenter implements ICalibrationSetContract.Presenter, View.OnClickListener {

    private static final String UP = "up";
    private static final String LEFT = "left";
    private static final String RIGHT = "right";
    private static final String DOWN = "down";

    private ICalibrationSetContract.View mCalibrationSetView;
    private CalibrationSetActivity mCalibrationSetActivity;
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
    private CalibrationSetModel mCalibrationSetModel;
    private BasicDialog mDialog;
    // 判断是否点击了返回按钮， true 代表了点击返回按钮，如果有未保存的数据我们进行弹框提示，点击弹
    // 框的是我们保存并退出当前的界面，否则，直接退出当前的界面; false 代表了选择了手动标定，在点击
    // 了上下左右标定按钮，并实时的保存标定的设置数据
    private boolean isClickBack = false;

    public CalibrationSetPresenter(ICalibrationSetContract.View view) {
        mCalibrationSetView = view;
        mCalibrationSetActivity = (CalibrationSetActivity) view;
    }

    @Override
    public void initData() {
        mCbAutoCalibration = mCalibrationSetView.getCbAutoCalibration();
        mCbManualCalibrate = mCalibrationSetView.getCbManualCalibrate();
        mEtCameraHigh = mCalibrationSetView.getEtCameraHigh();
        mBtnUp = mCalibrationSetView.getBtnUp();
        mBtnLeft = mCalibrationSetView.getBtnLeft();
        mBtnRight = mCalibrationSetView.getBtnRight();
        mBtnDown = mCalibrationSetView.getBtnDown();
        mTvSave = mCalibrationSetView.getTvSave();
        mLlAutoCalibrate = mCalibrationSetView.getLlAutoCalibrate();
        mLlManualCalibrate = mCalibrationSetView.getLlManualCalibrate();

        //获取设备的标定设置的数据
        SettingsWrapper.getInstance().getCalibrationSet().subscribe(new Subscriber<CalibrationSetModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ExceptionUtils.exceptionHandling(mCalibrationSetActivity, e);
            }

            @Override
            public void onNext(CalibrationSetModel calibrationSetModel) {
                mCalibrationSetModel = calibrationSetModel;
                int autoReferenceLineEnable = calibrationSetModel.getAutoReferenceLineEnable();
                int cameraHigh = calibrationSetModel.getCameraHight();

                //判断是否是自动标定
                if (autoReferenceLineEnable == 1) {
                    isAutoCalibration(true);
                } else {
                    isAutoCalibration(false);
                }
                //设置镜头高度
                mEtCameraHigh.setText(String.valueOf(cameraHigh));
            }
        });
    }

    private void isAutoCalibration(boolean isAutoCalibration) {
        if (isAutoCalibration) {
            mCbAutoCalibration.setChecked(true);
            mCbManualCalibrate.setChecked(false);
            mEtCameraHigh.setEnabled(false);
            mEtCameraHigh.setClickable(false);
            mBtnUp.setClickable(false);
            mBtnUp.setEnabled(false);
            mBtnLeft.setEnabled(false);
            mBtnLeft.setClickable(false);
            mBtnRight.setEnabled(false);
            mBtnRight.setClickable(false);
            mBtnDown.setClickable(false);
            mBtnDown.setEnabled(false);
            mBtnUp.setClickable(false);
            mBtnUp.setEnabled(false);
            mTvSave.setVisibility(View.VISIBLE);
        } else {
            mCbAutoCalibration.setChecked(false);
            mCbManualCalibrate.setChecked(true);
            mEtCameraHigh.setEnabled(true);
            mEtCameraHigh.setClickable(true);
            mBtnUp.setClickable(true);
            mBtnUp.setEnabled(true);
            mBtnLeft.setEnabled(true);
            mBtnLeft.setClickable(true);
            mBtnRight.setEnabled(true);
            mBtnRight.setClickable(true);
            mBtnDown.setClickable(true);
            mBtnDown.setEnabled(true);
            mBtnUp.setClickable(true);
            mBtnUp.setEnabled(true);
            mTvSave.setVisibility(View.GONE);
        }
    }

    @Override
    public void initListener() {
        ImageView ivBack = mCalibrationSetView.getIvBack();
        ivBack.setOnClickListener(this);
        mCbAutoCalibration.setOnClickListener(this);
        mCbManualCalibrate.setOnClickListener(this);
        mLlAutoCalibrate.setOnClickListener(this);
        mLlManualCalibrate.setOnClickListener(this);
        mTvSave.setOnClickListener(this);
        mBtnUp.setOnClickListener(this);
        mBtnDown.setOnClickListener(this);
        mBtnLeft.setOnClickListener(this);
        mBtnRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        boolean autoCalibrationChecked = mCbAutoCalibration.isChecked();
        int autoCalibrationValue = autoCalibrationChecked ? 1 : 0;

        if (id == R.id.iv_back) { //返回按钮
            isClickBack = true;
            if (mCalibrationSetModel != null) {
                // 判断是否有更改的标定设置未保存，如果有未保存的进行弹出对话框提示
                if (autoCalibrationChecked) {
                    if (autoCalibrationValue == mCalibrationSetModel.getAutoReferenceLineEnable()) {
                        mCalibrationSetActivity.finish();
                    } else {
                        showSaveCalibrationSetDialog();
                    }
                }
            }
        } else if (id == R.id.tv_cancel) { //取消保存
            if (mDialog != null && mDialog.isAdded()) {
                mDialog.dismiss();
            }
            mCalibrationSetActivity.finish();
        } else if (id == R.id.ll_auto_calibrate || id == R.id.cb_auto_calibration) { //选择自动标定
            isAutoCalibration(true);
        } else if (id == R.id.ll_manual_calibrate || id == R.id.cb_manual_calibrate) { //选择手动标定
            isAutoCalibration(false);
        } else if (id == R.id.btn_up) { //手动标定:上
            updateCalibrationSet(autoCalibrationChecked, UP);
        } else if (id == R.id.btn_left) { //手动标定：左
            updateCalibrationSet(autoCalibrationChecked, LEFT);
        } else if (id == R.id.btn_right) {//手动标定 : 右
            updateCalibrationSet(autoCalibrationChecked, RIGHT);
        } else if (id == R.id.btn_down) { //手动标定:下
            updateCalibrationSet(autoCalibrationChecked, DOWN);
        } else if (id == R.id.tv_save || id == R.id.tv_confirm) {  // 保存 或者  确认保存并退出
            if (mDialog != null && mDialog.isAdded()) {
                mDialog.dismiss();
            }
            updateCalibrationSet(autoCalibrationChecked, " ");
        }
    }

    private void showSaveCalibrationSetDialog() {
        View view = View.inflate(mCalibrationSetActivity, R.layout.dialog_common_styles, null);
        TextView tv_dialog_title = view.findViewById(R.id.tv_dialog_title);
        TextView tv_dialog_description = view.findViewById(R.id.tv_dialog_description);
        TextView tv_cancel = view.findViewById(R.id.tv_cancel);
        TextView tv_confirm = view.findViewById(R.id.tv_confirm);

        String description = mCalibrationSetActivity.getString(R.string.save_params_write_dialog_description);
        String no = mCalibrationSetActivity.getString(R.string.no);
        String yes = mCalibrationSetActivity.getString(R.string.yes);

        float margin = mCalibrationSetActivity.getResources().getDimension(R.dimen.dp_20);
        int padding = (int) margin;

        tv_dialog_title.setVisibility(View.GONE);
        tv_dialog_description.setText(description);
        tv_dialog_description.setTextColor(Color.BLACK);
        tv_dialog_description.setPadding(padding, padding, padding, padding);
        tv_cancel.setText(no);
        tv_confirm.setText(yes);

        mDialog = CommonDialog.init()
                .setView(view)
                .setMargin(margin)
                .setOutCancel(false)
                .setAnimStyle(R.style.BottomAnimStyle)
                .setDimAmount(0.8f)
                .show(mCalibrationSetActivity.getSupportFragmentManager());

        tv_cancel.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);
    }

    private void updateCalibrationSet(boolean autoCalibrationChecked, String cmd) {
        String cameraHigh = mEtCameraHigh.getText().toString();
        if (mCalibrationSetModel != null) {
            if (autoCalibrationChecked) {
                mCalibrationSetModel.setAutoReferenceLineEnable(1);
                mCalibrationSetModel.setCameraHight(0);
                mCalibrationSetModel.setManualReferenceLineEnable(0);
                mCalibrationSetModel.setCmd("");
            } else {
                mCalibrationSetModel.setAutoReferenceLineEnable(0);
                mCalibrationSetModel.setCameraHight(TextUtils.isEmpty(cameraHigh) ? 0 : Integer.parseInt(cameraHigh));
                mCalibrationSetModel.setManualReferenceLineEnable(1);
                mCalibrationSetModel.setCmd(cmd);
            }

            String json = GsonUtils.getInstance().toJson(mCalibrationSetModel);
            try {
                JSONObject jsonObject = new JSONObject(json);
                SettingsWrapper.getInstance().updateCalibrationSet(jsonObject).subscribe(new Subscriber<CalibrationSetModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ExceptionUtils.exceptionHandling(mCalibrationSetActivity, e);
                    }

                    @Override
                    public void onNext(CalibrationSetModel calibrationSetModel) {
                        Toast.makeText(mCalibrationSetActivity, R.string.targets_set_save_success, Toast.LENGTH_SHORT).show();

                        if (isClickBack) {
                            mCalibrationSetActivity.finish();
                        }
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }
}
