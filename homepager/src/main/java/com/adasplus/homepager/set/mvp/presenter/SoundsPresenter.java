package com.adasplus.homepager.set.mvp.presenter;

import android.view.View;
import android.widget.ImageView;
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

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import rx.Subscriber;

/**
 * Author:刘净辉
 * Date : 2019/9/26 15:48
 * Description : 声音设置的 Presenter 类
 */
public class SoundsPresenter implements ISoundsContract.Presenter, View.OnClickListener, SignSeekBar.OnProgressChangedListener, SwipeRefreshLayout.OnRefreshListener {

    private ISoundsContract.View mSoundsView;
    private SoundsActivity mSoundsActivity;
    private SoundsModel mSoundsModel;

    private SignSeekBar mSsbSoundsValue;
    private TextView mTvCurrentSounds;
    private ImageView mIvSoundsAdd;
    private TextView mTvSoundType;
    private ImageView mIvSoundType;


    private int mSoundValue;
    private int mSoundType;


    public SoundsPresenter(ISoundsContract.View view) {
        mSoundsView = view;
        mSoundsActivity = (SoundsActivity) view;
    }

    @Override
    public void initData() {
        mSsbSoundsValue = mSoundsView.getSsbSoundsValue();
        mTvCurrentSounds = mSoundsView.getTvCurrentSounds();
        mIvSoundType = mSoundsView.getIvSoundType();
        mTvSoundType = mSoundsView.getTvSoundType();
        getNetwork();
    }


    private void getNetwork(){
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
                mSoundType = soundsModel.getSoundType();
                if(mSoundType == 0){
                    mTvSoundType.setText((String.format("%s：%s",mSoundsActivity.getString(R.string.sound_type),"滴滴声")));
                    mIvSoundType.setImageResource(R.mipmap.switch_open_icon);
                }else{
                    mTvSoundType.setText((String.format("%s：%s",mSoundsActivity.getString(R.string.sound_type),"语音")));
                    mIvSoundType.setImageResource(R.mipmap.switch_close_icon);
                }
                mSsbSoundsValue.setProgress(mSoundValue);
                mTvCurrentSounds.setText((String.format("%s%%",String.valueOf(mSoundValue*10))));
            }
        });

    }

    @Override
    public void initListener() {
        ImageView ivSoundBack = mSoundsView.getIvSoundBack();
        ImageView ivSoundsReduce = mSoundsView.getIvSoundsReduce();
        mIvSoundsAdd = mSoundsView.getIvSoundsAdd();
        TextView tvSoundSave = mSoundsView.getTvSoundSave();

        ivSoundBack.setOnClickListener(this);
        ivSoundsReduce.setOnClickListener(this);
        mSsbSoundsValue.setOnProgressChangedListener(this);
        mIvSoundsAdd.setOnClickListener(this);
        tvSoundSave.setOnClickListener(this);
        mIvSoundType.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_sound_back) {
            mSoundsActivity.finish();
        } else if (id == R.id.tv_sound_save) {
            if (mSoundsModel != null) {
                //将 SeekBar 中滑动的最新的进度进行设置到 SoundsModel
                mSoundsModel.setSoundValue(mSsbSoundsValue.getProgress());

                mSoundsModel.setSoundType(mSoundType);

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
                            Toast.makeText(mSoundsActivity, R.string.sounds_set_save_success, Toast.LENGTH_SHORT).show();
                            mSoundsActivity.finish();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
        else if(id == R.id.iv_sound_type){
            if(mSoundType == 1){
                mTvSoundType.setText((String.format("%s：%s",mSoundsActivity.getString(R.string.sound_type),"滴滴声")));
                mIvSoundType.setImageResource(R.mipmap.switch_open_icon);
                mSoundType = 0;
            }else{
                mTvSoundType.setText((String.format("%s：%s",mSoundsActivity.getString(R.string.sound_type),"语音")));
                mIvSoundType.setImageResource(R.mipmap.switch_close_icon);
                mSoundType = 1;
            }
        }
    }

    @Override
    public void onProgressChanged(SignSeekBar signSeekBar, int progress, float progressFloat, boolean fromUser) {
        setCurrentSounds(progress);
    }

    @Override
    public void getProgressOnActionUp(SignSeekBar signSeekBar, int progress, float progressFloat) {
        setCurrentSounds(progress);
    }

    @Override
    public void getProgressOnFinally(SignSeekBar signSeekBar, int progress, float progressFloat, boolean fromUser) {
        setCurrentSounds(progress);
    }


    private void setCurrentSounds(int progress){
        if(progress == 10){
            mIvSoundsAdd.setImageResource(R.mipmap.sounds_max_icon);
        }else{
            mIvSoundsAdd.setImageResource(R.mipmap.sounds_middle_icon);
        }
        mTvCurrentSounds.setText(String.format("%s%%",String.valueOf(progress*10)));
    }

    @Override
    public void onRefresh() {
        getNetwork();
    }
}
