package com.adasplus.homepager.set.mvp.presenter;

import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.adasplus.base.utils.ExceptionUtils;
import com.adasplus.base.utils.GsonUtils;
import com.adasplus.base.view.SignSeekBar;
import com.adasplus.homepager.R;
import com.adasplus.homepager.network.HomeWrapper;
import com.adasplus.homepager.set.activity.SoundsActivity;
import com.adasplus.homepager.set.mvp.contract.ISoundsContract;
import com.adasplus.homepager.set.mvp.model.SoundsModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import rx.Subscriber;

/**
 * Author:刘净辉
 * Date : 2019/9/26 15:48
 * Description : 声音设置的 Presenter 类
 */
public class SoundsPresenter implements ISoundsContract.Presenter, View.OnClickListener, SignSeekBar.OnProgressChangedListener {

    private ISoundsContract.View mSoundsView;
    private SoundsActivity mSoundsActivity;
    private SoundsModel mSoundsModel;

    private SignSeekBar mSsbSoundsValue;
    private TextView mTvCurrentSounds;
    private ImageView mIvSoundsAdd;

    private int mSoundValue;


    public SoundsPresenter(ISoundsContract.View view) {
        mSoundsView = view;
        mSoundsActivity = (SoundsActivity) view;
    }

    @Override
    public void initData() {

        mSsbSoundsValue = mSoundsView.getSsbSoundsValue();
        mTvCurrentSounds = mSoundsView.getTvCurrentSounds();
        //获取设备中的默认声音大小值
        HomeWrapper.getInstance().getSoundsSet().subscribe(new Subscriber<SoundsModel>() {
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
                mSoundValue = soundsModel.getSoundValue();
//                setCurrentSounds(mSoundValue);
                mSsbSoundsValue.setProgress(mSoundValue);
            }
        });
    }

    @Override
    public void initListener() {
        ImageView ivBack = mSoundsView.getIvBack();
        ImageView ivSoundsReduce = mSoundsView.getIvSoundsReduce();
        mIvSoundsAdd = mSoundsView.getIvSoundsAdd();
        TextView tvSave = mSoundsView.getTvSave();

        ivBack.setOnClickListener(this);
        ivSoundsReduce.setOnClickListener(this);
        mSsbSoundsValue.setOnProgressChangedListener(this);
        mIvSoundsAdd.setOnClickListener(this);
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
                mSoundsModel.setSoundValue(mSsbSoundsValue.getProgress());
                String json = GsonUtils.getInstance().toJson(mSoundsModel);
                try {
                    //更新设备的的声音大小设置
                    JSONObject jsonObject = new JSONObject(json);
                    HomeWrapper.getInstance().updateSoundsSet(jsonObject).subscribe(new Subscriber<SoundsModel>() {
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

    @Override
    public void onProgressChanged(SignSeekBar signSeekBar, int progress, float progressFloat, boolean fromUser) {
        String s = String.format(Locale.CHINA, "%d", progress);
        setCurrentSounds(progress);
    }

    @Override
    public void getProgressOnActionUp(SignSeekBar signSeekBar, int progress, float progressFloat) {
        String s = String.format(Locale.CHINA, "%d", progress);
        setCurrentSounds(progress);
    }

    @Override
    public void getProgressOnFinally(SignSeekBar signSeekBar, int progress, float progressFloat, boolean fromUser) {
        String s = String.format(Locale.CHINA, "%d", progress);
        setCurrentSounds(progress);
    }


    private void setCurrentSounds(int progress){
        if(progress >=50){
            mIvSoundsAdd.setImageResource(R.mipmap.sounds_max_icon);
        }else{
            mIvSoundsAdd.setImageResource(R.mipmap.sounds_middle_icon);
        }
        String s = String.format(Locale.CHINA, "%d", progress);
        mTvCurrentSounds.setText(s + "%");
    }
}
