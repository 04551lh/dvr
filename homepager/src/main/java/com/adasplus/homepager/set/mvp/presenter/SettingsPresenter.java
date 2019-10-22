package com.adasplus.homepager.set.mvp.presenter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.adasplus.base.utils.ExceptionUtils;
import com.adasplus.homepager.R;
import com.adasplus.homepager.network.HomeWrapper;
import com.adasplus.homepager.set.activity.CalibrationSetActivity;
import com.adasplus.homepager.set.activity.CommonSetActivity;
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
        TextView tvSpeedSet = mSettingsView.getTvSpeedSet();
        TextView tvCanSet = mSettingsView.getTvCanSet();
        TextView tvCalibrationSet = mSettingsView.getTvCalibrationSet();
        TextView tvWarningSet = mSettingsView.getTvWarningSet();
        TextView tvCommonSet = mSettingsView.getTvCommonSet();
        TextView tvNetworkSet = mSettingsView.getTvNetworkSet();
        TextView tvVideoSet = mSettingsView.getTvVideoSet();

        ivBack.setOnClickListener(this);
        tvSpeedSet.setOnClickListener(this);
        tvCanSet.setOnClickListener(this);
        tvCalibrationSet.setOnClickListener(this);
        tvWarningSet.setOnClickListener(this);
        tvCommonSet.setOnClickListener(this);
        tvNetworkSet.setOnClickListener(this);
        tvVideoSet.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_back) {
            mSettingsActivity.finish();
        } else if (id == R.id.tv_speed_set) { //速度设置
            mSettingsActivity.startActivity(new Intent(mSettingsActivity, SpeedSetActivity.class));
        } else if (id == R.id.tv_network_set) { //网络设置
//            mSettingsActivity.startActivity(new Intent(mSettingsActivity, NetworkSetActivity.class));
        } else if (id == R.id.tv_can_set) {  // CAN 设置
            //mSettingsActivity.startActivity(new Intent(mSettingsActivity, CANSetActivity.class));
        } else if (id == R.id.tv_calibration_set) { //标定设置
            mSettingsActivity.startActivity(new Intent(mSettingsActivity, CalibrationSetActivity.class));
        } else if (id == R.id.tv_warning_set) { //报警设置
            mSettingsActivity.startActivity(new Intent(mSettingsActivity, WarningsSetActivity.class));
        } else if (id == R.id.tv_common_set) { //通用设置
            mSettingsActivity.startActivity(new Intent(mSettingsActivity, CommonSetActivity.class));
        }else if (id == R.id.tv_video_set){ //视频设置
            JSONObject jobj = new JSONObject();
            try {
                jobj.put("channelNumber",0);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            HomeWrapper.getInstance().getVideoSetData(jobj).subscribe(new Subscriber<VideoSetModel>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    ExceptionUtils.exceptionHandling(mSettingsActivity,e);
                }

                @Override
                public void onNext(VideoSetModel videoSetModel) {
                    Intent intent = new Intent(mSettingsActivity, VideoSetActivity.class);
                    intent.putExtra("videoSetModel",videoSetModel);
                    intent.putExtra("mainStream",videoSetModel.getMainStream());
                    intent.putExtra("subStream",videoSetModel.getSubStream());
                    mSettingsActivity.startActivity(intent);
                }
            });

        }
    }
}