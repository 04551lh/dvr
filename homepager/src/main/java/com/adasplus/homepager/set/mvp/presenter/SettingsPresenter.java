package com.adasplus.homepager.set.mvp.presenter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.adasplus.base.utils.ExceptionUtils;
import com.adasplus.homepager.R;
import com.adasplus.homepager.network.HomeWrapper;
import com.adasplus.homepager.set.activity.CANSetActivity;
import com.adasplus.homepager.set.activity.CalibrationSetActivity;
import com.adasplus.homepager.set.activity.CommonSetActivity;
import com.adasplus.homepager.set.activity.NetworkSetActivity;
import com.adasplus.homepager.set.activity.SettingsActivity;
import com.adasplus.homepager.set.activity.SpeedSetActivity;
import com.adasplus.homepager.set.activity.VideoSetActivity;
import com.adasplus.homepager.set.activity.WarningsSetActivity;
import com.adasplus.homepager.set.mvp.contract.ISettingsContract;
import com.adasplus.homepager.set.mvp.model.VideoSetModel;

import org.json.JSONException;
import org.json.JSONObject;

import rx.Subscriber;

/**
 * Author:刘净辉
 * Date : 2019/9/25 10:50
 * Description :
 */
public class SettingsPresenter implements ISettingsContract.Presenter, View.OnClickListener {

    private ISettingsContract.View mSettingsView;
    private SettingsActivity mSettingsActivity;

    public SettingsPresenter(ISettingsContract.View view) {
        mSettingsView = view;
        mSettingsActivity = (SettingsActivity) view;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        ImageView ivBack = mSettingsView.getIvBack();
        LinearLayout llSpeedSet = mSettingsView.getLlSpeedSet();
        LinearLayout llCanSet = mSettingsView.getLlCanSet();
        LinearLayout llCalibrationSet = mSettingsView.getLlCalibrationSet();
        LinearLayout llWarningSet = mSettingsView.getLlWarningSet();
        LinearLayout llCommonSet = mSettingsView.getLlCommonSet();
        LinearLayout llNetworkSet = mSettingsView.getLlNetworkSet();
        LinearLayout llVideoSet = mSettingsView.getLlVideoSet();

        ivBack.setOnClickListener(this);
        llSpeedSet.setOnClickListener(this);
        llCanSet.setOnClickListener(this);
        llCalibrationSet.setOnClickListener(this);
        llWarningSet.setOnClickListener(this);
        llCommonSet.setOnClickListener(this);
        llNetworkSet.setOnClickListener(this);
        llVideoSet.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_back) {
            mSettingsActivity.finish();
        } else if (id == R.id.ll_speed_set) { //速度设置
            mSettingsActivity.startActivity(new Intent(mSettingsActivity, SpeedSetActivity.class));
        } else if (id == R.id.ll_network_set) { //网络设置
            mSettingsActivity.startActivity(new Intent(mSettingsActivity, NetworkSetActivity.class));
        } else if (id == R.id.ll_can_set) {  // CAN 设置
            mSettingsActivity.startActivity(new Intent(mSettingsActivity, CANSetActivity.class));
        } else if (id == R.id.ll_calibration_set) { //标定设置
            mSettingsActivity.startActivity(new Intent(mSettingsActivity, CalibrationSetActivity.class));
        } else if (id == R.id.ll_warning_set) { //报警设置
            mSettingsActivity.startActivity(new Intent(mSettingsActivity, WarningsSetActivity.class));
        } else if (id == R.id.ll_common_set) { //通用设置
            mSettingsActivity.startActivity(new Intent(mSettingsActivity, CommonSetActivity.class));
        }else if (id == R.id.ll_video_set){ //视频设置
            Intent intent = new Intent(mSettingsActivity, VideoSetActivity.class);
            mSettingsActivity.startActivity(intent);
        }
    }
}
