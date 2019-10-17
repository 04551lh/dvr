package com.adasplus.settings.mvp.presenter;

import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.adasplus.base.utils.ExceptionUtils;
import com.adasplus.base.utils.GsonUtils;
import com.adasplus.settings.R;
import com.adasplus.settings.activity.SoundsActivity;
import com.adasplus.settings.mvp.contract.ISoundsContract;
import com.adasplus.settings.mvp.model.SoundsModel;
import com.adasplus.settings.network.SettingsWrapper;

import org.json.JSONException;
import org.json.JSONObject;

import rx.Subscriber;

/**
 * Author:刘净辉
 * Date : 2019/9/26 15:48
 * Description : 声音设置的 Presenter 类
 */
public class SoundsPresenter implements ISoundsContract.Presenter, View.OnClickListener {

    private ISoundsContract.View mSoundsView;
    private SoundsActivity mSoundsActivity;
    private SeekBar mSbSoundsSize;
    private SoundsModel mSoundsModel;

    public SoundsPresenter(ISoundsContract.View view) {
        mSoundsView = view;
        mSoundsActivity = (SoundsActivity) view;
    }

    @Override
    public void initData() {

        mSbSoundsSize = mSoundsView.getSbSoundsSize();

        //获取设备中的默认声音大小值
        SettingsWrapper.getInstance().getSoundsSet().subscribe(new Subscriber<SoundsModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ExceptionUtils.exceptionHandling(mSoundsActivity, e);
            }

            @Override
            public void onNext(SoundsModel soundsModel) {
                mSoundsModel = soundsModel;
                //获取声音值，并设置声音值
                int soundValue = soundsModel.getSoundValue();
                mSbSoundsSize.setProgress(soundValue);
            }
        });
    }

    @Override
    public void initListener() {
        ImageView ivBack = mSoundsView.getIvBack();
        TextView tvSave = mSoundsView.getTvSave();

        ivBack.setOnClickListener(this);
        tvSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_back) {
            mSoundsActivity.finish();
        } else if (id == R.id.tv_save) {
            if (mSoundsModel != null) {
                //将 SeekBar 中滑动的最新的进度进行设置到 SoundsModel
                mSoundsModel.setSoundValue(mSbSoundsSize.getProgress());
                String json = GsonUtils.getInstance().toJson(mSoundsModel);
                try {
                    //更新设备的的声音大小设置
                    JSONObject jsonObject = new JSONObject(json);
                    SettingsWrapper.getInstance().updateSoundsSet(jsonObject).subscribe(new Subscriber<SoundsModel>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            ExceptionUtils.exceptionHandling(mSoundsActivity, e);
                        }

                        @Override
                        public void onNext(SoundsModel soundsModel) {
                            Toast.makeText(mSoundsActivity, "声音设置保存完成", Toast.LENGTH_SHORT).show();
                            mSoundsActivity.finish();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
