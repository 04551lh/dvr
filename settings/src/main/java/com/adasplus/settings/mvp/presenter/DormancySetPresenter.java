package com.adasplus.settings.mvp.presenter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adasplus.base.utils.ExceptionUtils;
import com.adasplus.base.utils.GsonUtils;
import com.adasplus.settings.R;
import com.adasplus.settings.activity.DormancySetActivity;
import com.adasplus.settings.mvp.contract.IDormancySetContract;
import com.adasplus.settings.mvp.model.DormancySetModel;
import com.adasplus.settings.network.SettingsWrapper;

import org.json.JSONException;
import org.json.JSONObject;

import rx.Subscriber;

/**
 * Author:刘净辉
 * Date : 2019/9/26 16:02
 * Description :
 */
public class DormancySetPresenter implements IDormancySetContract.Presenter, View.OnClickListener {

    private IDormancySetContract.View mDormancySetView;
    private DormancySetActivity mDormancySetActivity;
    private EditText mEtErrorNumber;
    private DormancySetModel mDormancySetModel;

    public DormancySetPresenter(IDormancySetContract.View view) {
        mDormancySetView = view;
        mDormancySetActivity = (DormancySetActivity) view;
    }

    @Override
    public void initData() {

        mEtErrorNumber = mDormancySetView.getEtErrorNumber();
        //获取设备中默认设置的休眠时间
        SettingsWrapper.getInstance().getSleepSet().subscribe(new Subscriber<DormancySetModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ExceptionUtils.exceptionHandling(mDormancySetActivity, e);
            }

            @Override
            public void onNext(DormancySetModel dormancySetModel) {
                mDormancySetModel = dormancySetModel;
                int timeoutValue = dormancySetModel.getTimeoutValue();
                String sleep = String.valueOf(timeoutValue);
                mEtErrorNumber.setText(sleep);
                mEtErrorNumber.setSelection(sleep.length());
            }
        });
    }

    @Override
    public void initListener() {
        ImageView ivBack = mDormancySetView.getIvBack();
        TextView tvSave = mDormancySetView.getTvSave();
        Button btnAdd = mDormancySetView.getBtnAdd();
        Button btnSub = mDormancySetView.getBtnSub();

        ivBack.setOnClickListener(this);
        tvSave.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnSub.setOnClickListener(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        int id = v.getId();
        //休眠时间的设置范围是 60 - 600 s
        int mMinSleepValue = 60;
        int mMaxSleepValue = 600;
        if (id == R.id.iv_back) { //返回
            mDormancySetActivity.finish();
        } else if (id == R.id.btn_add) {
            //获取当前的休眠值
            String sleep = mEtErrorNumber.getText().toString().trim();
            int currentSleepValue = Integer.parseInt(sleep);
            //判断当前获取的休眠值是否大于 600 ,如果大于等于600，进行提示已经达到最大的休眠时间
            if (currentSleepValue >= mMaxSleepValue) {
                Toast.makeText(mDormancySetActivity, R.string.max_sleep_time_tips, Toast.LENGTH_SHORT).show();
                return;
            }
            //在界面上刷新显示最新的休眠值
            String currentSleep = currentSleepValue + 1 + "";
            mEtErrorNumber.setText(currentSleep);
            mEtErrorNumber.setSelection(currentSleep.length());
        } else if (id == R.id.btn_sub) {
            //获取当前的休眠值
            String sleep = mEtErrorNumber.getText().toString().trim();
            int currentSleepValue = Integer.parseInt(sleep);
            //判断当前获取的休眠值是否小于等于 60，如果小于等于 60，进行提示已经达到了最小的休眠时间
            if (currentSleepValue <= mMinSleepValue) {
                Toast.makeText(mDormancySetActivity, R.string.min_sleep_time_tips, Toast.LENGTH_SHORT).show();
                return;
            }
            //在界面上刷新显示最新的休眠值
            String currentSleep = currentSleepValue - 1 + "";
            mEtErrorNumber.setText(currentSleep);
            mEtErrorNumber.setSelection(currentSleep.length());
        } else if (id == R.id.tv_save) {
            String sleep = mEtErrorNumber.getText().toString().trim();
            int currentSleepValue = Integer.parseInt(sleep);
            //判断休眠值是否小于等于60或大于等于600，如果超出这个范围，进行做友好的提示
            if (currentSleepValue <= mMinSleepValue || currentSleepValue >= mMaxSleepValue) {
                Toast.makeText(mDormancySetActivity, R.string.sleep_value_between_60_600_range, Toast.LENGTH_SHORT).show();
                return;
            }

            if (mDormancySetModel != null) {
                mDormancySetModel.setTimeoutValue(currentSleepValue);
                String json = GsonUtils.getInstance().toJson(mDormancySetModel);
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    //更改保存最新的休眠值的接口
                    SettingsWrapper.getInstance().updateSleepSet(jsonObject).subscribe(new Subscriber<DormancySetModel>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            ExceptionUtils.exceptionHandling(mDormancySetActivity, e);
                        }

                        @Override
                        public void onNext(DormancySetModel dormancySetModel) {
                            Toast.makeText(mDormancySetActivity, R.string.sleep_time_set_successfully, Toast.LENGTH_SHORT).show();
                            mDormancySetActivity.finish();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
