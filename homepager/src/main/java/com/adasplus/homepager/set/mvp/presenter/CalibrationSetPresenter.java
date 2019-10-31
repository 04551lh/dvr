package com.adasplus.homepager.set.mvp.presenter;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.adasplus.base.dialog.BasicDialog;
import com.adasplus.base.dialog.CommonDialog;
import com.adasplus.base.utils.ExceptionUtils;
import com.adasplus.base.utils.GsonUtils;
import com.adasplus.homepager.R;
import com.adasplus.homepager.network.HomeWrapper;
import com.adasplus.homepager.set.activity.CalibrationSetActivity;
import com.adasplus.homepager.set.mvp.contract.ICalibrationSetContract;
import com.adasplus.homepager.set.mvp.model.CalibrationSetModel;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import rx.Subscriber;

/**
 * Author:刘净辉
 * Date : 2019/9/26 19:34
 * Description :
 */
public class CalibrationSetPresenter implements ICalibrationSetContract.Presenter, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String UP = "up";
    private static final String LEFT = "left";
    private static final String RIGHT = "right";
    private static final String DOWN = "down";

    private ICalibrationSetContract.View mCalibrationSetView;
    private CalibrationSetActivity mCalibrationSetActivity;

    private SwipeRefreshLayout mSwipeRefreshLayoutCalibrationSet;
    private ImageView mIvAutoCalibration;
    private ImageView mIvManualCalibrate;
    private TextView mTvCameraHeight;
    private EditText mEtCameraHeight;
    private ImageView mIvUp;
    private ImageView mIvLeft;
    private ImageView mIvRight;
    private ImageView mIvDown;
    private TextView mTvCalibrationSave;
    private CalibrationSetModel mCalibrationSetModel;
    private BasicDialog mDialog;
    // 判断是否点击了返回按钮， true 代表了点击返回按钮，如果有未保存的数据我们进行弹框提示，点击弹
    // 框的是我们保存并退出当前的界面，否则，直接退出当前的界面; false 代表了选择了手动标定，在点击
    // 了上下左右标定按钮，并实时的保存标定的设置数据
    private int mAutoReferenceLineEnable;

    public CalibrationSetPresenter(ICalibrationSetContract.View view) {
        mCalibrationSetView = view;
        mCalibrationSetActivity = (CalibrationSetActivity) view;
    }

    @Override
    public void initData() {
        mSwipeRefreshLayoutCalibrationSet = mCalibrationSetActivity.getSwipeRefreshLayoutCalibrationSet();
        mIvAutoCalibration = mCalibrationSetView.getIvAutoCalibration();
        mIvManualCalibrate = mCalibrationSetView.getIvManualCalibrate();
        mTvCameraHeight = mCalibrationSetActivity.getTvCameraHeight();
        mEtCameraHeight = mCalibrationSetView.getEtCameraHeight();
        mIvUp = mCalibrationSetView.getIvUp();
        mIvLeft = mCalibrationSetView.getIvLeft();
        mIvRight = mCalibrationSetView.getIvRight();
        mIvDown = mCalibrationSetView.getIvDown();
        mTvCalibrationSave = mCalibrationSetView.getTvCalibrationSave();

        getNetworkData();

    }

    //获取设备的标定设置的数据
    private void getNetworkData() {
        HomeWrapper.getInstance().getCalibrationSet().subscribe(new Subscriber<CalibrationSetModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ExceptionUtils.exceptionHandling(mCalibrationSetActivity, e);
                mSwipeRefreshLayoutCalibrationSet.setRefreshing(false); // close refresh animator

            }

            @Override
            public void onNext(CalibrationSetModel calibrationSetModel) {
                mSwipeRefreshLayoutCalibrationSet.setRefreshing(false); // close refresh animator
                mCalibrationSetModel = calibrationSetModel;
                mAutoReferenceLineEnable = calibrationSetModel.getAutoReferenceLineEnable();
                //判断是否是自动标定
                if (mAutoReferenceLineEnable == 1) {
                    isAutoCalibration(true);
                } else {
                    isAutoCalibration(false);
                }
                //设置镜头高度
                int cameraHigh = calibrationSetModel.getCameraHight();
                mEtCameraHeight.setText(String.valueOf(cameraHigh));
            }
        });

    }

    private void isAutoCalibration(boolean isAutoCalibration) {
        if (isAutoCalibration) {
            mIvAutoCalibration.setImageResource(R.mipmap.switch_open_icon);
            mIvManualCalibrate.setImageResource(R.mipmap.switch_close_icon);
            mEtCameraHeight.setEnabled(false);
            mEtCameraHeight.setClickable(false);
            mTvCameraHeight.setTextColor(mCalibrationSetActivity.getResources().getColor(R.color.under_line_color));
            mEtCameraHeight.setTextColor(mCalibrationSetActivity.getResources().getColor(R.color.under_line_color));
            mIvUp.setClickable(false);
            mIvUp.setEnabled(false);
            mIvUp.setImageResource(R.mipmap.manual_calibrate_no_select_up);
            mIvLeft.setEnabled(false);
            mIvLeft.setClickable(false);
            mIvLeft.setImageResource(R.mipmap.manual_calibrate_no_select_left);
            mIvRight.setEnabled(false);
            mIvRight.setClickable(false);
            mIvRight.setImageResource(R.mipmap.manual_calibrate_no_select_right);
            mIvDown.setClickable(false);
            mIvDown.setEnabled(false);
            mIvDown.setImageResource(R.mipmap.manual_calibrate_no_select_down);
        } else {
            mIvAutoCalibration.setImageResource(R.mipmap.switch_close_icon);
            mIvManualCalibrate.setImageResource(R.mipmap.switch_open_icon);
            mEtCameraHeight.setEnabled(true);
            mEtCameraHeight.setClickable(true);
            mTvCameraHeight.setTextColor(mCalibrationSetActivity.getResources().getColor(R.color.font_color_333));
            mEtCameraHeight.setTextColor(mCalibrationSetActivity.getResources().getColor(R.color.font_color_333));
            mIvUp.setClickable(true);
            mIvUp.setEnabled(true);
            mIvUp.setImageResource(R.mipmap.manual_calibrate_select_up);
            mIvLeft.setEnabled(true);
            mIvLeft.setClickable(true);
            mIvLeft.setImageResource(R.mipmap.manual_calibrate_select_left);
            mIvRight.setEnabled(true);
            mIvRight.setClickable(true);
            mIvRight.setImageResource(R.mipmap.manual_calibrate_select_right);
            mIvDown.setClickable(true);
            mIvDown.setEnabled(true);
            mIvDown.setImageResource(R.mipmap.manual_calibrate_select_down);
        }
    }

    @Override
    public void initListener() {
        ImageView ivCalibrationBack = mCalibrationSetView.getIvCalibrationBack();
        ivCalibrationBack.setOnClickListener(this);
        mSwipeRefreshLayoutCalibrationSet.setOnRefreshListener(this);
        mIvAutoCalibration.setOnClickListener(this);
        mIvManualCalibrate.setOnClickListener(this);
        mTvCalibrationSave.setOnClickListener(this);
        mIvUp.setOnClickListener(this);
        mIvDown.setOnClickListener(this);
        mIvLeft.setOnClickListener(this);
        mIvRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_calibration_back) { //返回按钮
            if (mCalibrationSetModel != null) {
                // 判断是否有更改的标定设置未保存，如果有未保存的进行弹出对话框提示
                if (mAutoReferenceLineEnable == mCalibrationSetModel.getAutoReferenceLineEnable()) {
                    mCalibrationSetActivity.finish();
                } else {
                    showSaveCalibrationSetDialog();
                }
            }
        } else if (id == R.id.tv_cancel) { //取消保存
            if (mDialog != null && mDialog.isAdded()) {
                mDialog.dismiss();
            }
            mCalibrationSetActivity.finish();
        } else if (id == R.id.iv_auto_calibration) { //选择自动标定
            mAutoReferenceLineEnable = 1;
            isAutoCalibration(true);
        } else if (id == R.id.iv_manual_calibrate) { //选择手动标定
            mAutoReferenceLineEnable = 0;
            isAutoCalibration(false);
        } else if (id == R.id.iv_up) { //手动标定:上
            if (mAutoReferenceLineEnable == 0) {
                updateCalibrationSet(UP);
            }

        } else if (id == R.id.iv_left) { //手动标定：左
            if (mAutoReferenceLineEnable == 0) {
                updateCalibrationSet(LEFT);
            }
        } else if (id == R.id.iv_right) {//手动标定 : 右
            if (mAutoReferenceLineEnable == 0) {
                updateCalibrationSet(RIGHT);
            }
        } else if (id == R.id.iv_down) { //手动标定:下
            if (mAutoReferenceLineEnable == 0) {
                updateCalibrationSet(DOWN);
            }
        } else if (id == R.id.tv_calibration_save || id == R.id.tv_confirm) {  // 保存 或者  确认保存并退出
            if (mDialog != null && mDialog.isAdded()) {
                mDialog.dismiss();
            }
            updateCalibrationSet(" ");
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

        float margin = mCalibrationSetActivity.getResources().getDimension(R.dimen.dp_12);
        int paddingTop = (int) mCalibrationSetActivity.getResources().getDimension(R.dimen.dp_28);
        int paddingLeft = (int) mCalibrationSetActivity.getResources().getDimension(R.dimen.dp_19);
        int paddingRight = (int) mCalibrationSetActivity.getResources().getDimension(R.dimen.dp_19);
        int paddingBottom = (int) mCalibrationSetActivity.getResources().getDimension(R.dimen.dp_28);

        tv_dialog_title.setVisibility(View.GONE);
        tv_dialog_description.setText(description);
        tv_dialog_description.setTextColor(mCalibrationSetActivity.getResources().getColor(R.color.font_color_333));
        tv_dialog_description.setPadding(paddingTop, paddingLeft, paddingRight, paddingBottom);

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

    private void updateCalibrationSet(String cmd) {
        String cameraHigh = mEtCameraHeight.getText().toString();
        if (mCalibrationSetModel != null) {
            if (mAutoReferenceLineEnable == 1) {
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
        }


        String json = GsonUtils.getInstance().toJson(mCalibrationSetModel);
        try {
            JSONObject jsonObject = new JSONObject(json);
            HomeWrapper.getInstance().updateCalibrationSet(jsonObject).subscribe(new Subscriber<CalibrationSetModel>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    ExceptionUtils.exceptionHandling(mCalibrationSetActivity, e);
                }

                @Override
                public void onNext(CalibrationSetModel calibrationSetModel) {
                    mCalibrationSetActivity.showToast(R.string.targets_set_save_success);
                    mCalibrationSetActivity.finish();

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRefresh() {
        getNetworkData();
    }
}
