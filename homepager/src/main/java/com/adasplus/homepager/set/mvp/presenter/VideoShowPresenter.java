package com.adasplus.homepager.set.mvp.presenter;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.adasplus.base.utils.ExceptionUtils;
import com.adasplus.homepager.R;
import com.adasplus.homepager.network.HomeWrapper;
import com.adasplus.homepager.set.activity.VideoShowActivity;
import com.adasplus.homepager.set.mvp.contract.IVideoShowContract;
import com.adasplus.homepager.set.mvp.model.VideoSetModel;
import com.adasplus.homepager.set.mvp.model.VideoShowModel;

import org.json.JSONException;
import org.json.JSONObject;

import rx.Subscriber;

public class VideoShowPresenter implements IVideoShowContract.Presenter, View.OnClickListener {

    private VideoShowActivity mVideoShowActivity;
    private IVideoShowContract.View mVideoShowView;

    private ImageView mImVideoShowBack;
    private EditText mEtVideoShowPlatform;
    private ImageView mImFullScreen;
    private TextView mTvVideoShowSave;

    private int mFullScreen;

    public VideoShowPresenter(IVideoShowContract.View view){
        mVideoShowView = view;
        mVideoShowActivity = (VideoShowActivity) view;
    }

    @Override
    public void initData() {
        mImVideoShowBack = mVideoShowView.getImVideoShowBack();
        mEtVideoShowPlatform = mVideoShowView.getEtVideoShowPlatform();
        mImFullScreen = mVideoShowView.getImFullScreen();
        mTvVideoShowSave = mVideoShowView.getTvVideoShowSave();
        getNetWork();
    }

    @Override
    public void initListener() {
        mImVideoShowBack.setOnClickListener(this);
        mImFullScreen.setOnClickListener(this);
        mTvVideoShowSave.setOnClickListener(this);
    }

    @Override
    public void getNetWork() {
        HomeWrapper.getInstance().getVideoShowData().subscribe(new Subscriber<VideoShowModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ExceptionUtils.exceptionHandling(mVideoShowActivity, e);
            }

            @Override
            public void onNext(VideoShowModel videoShowModel) {
                mFullScreen = videoShowModel.getShowFullScreen();
                mEtVideoShowPlatform.setText(videoShowModel.getChannelNumber()+"");
                if(mFullScreen == 1){
                    mImFullScreen.setImageResource(R.mipmap.switch_open_icon);
                }else{
                    mImFullScreen.setImageResource(R.mipmap.switch_close_icon);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.iv_video_show_back){
            mVideoShowActivity.finish();
        }
        if(id == R.id.iv_full_screen){
            if(mFullScreen == 1){
                mImFullScreen.setImageResource(R.mipmap.switch_close_icon);
                mFullScreen = 0;
            }else{
                mImFullScreen.setImageResource(R.mipmap.switch_open_icon);
                mFullScreen = 1;
            }
        }
        if(id == R.id.tv_video_show_save){
            int channelNumber = Integer.valueOf(mEtVideoShowPlatform.getText().toString());
            if(channelNumber<0 || channelNumber >6){
                mVideoShowActivity.showToast(R.string.channel_number_one_six);
            }
            JSONObject jobj = new JSONObject();
            try {
                jobj.put("channelNumber", channelNumber);
                jobj.put("showFullScreen", mFullScreen);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            HomeWrapper.getInstance().updateVideoShowData(jobj).subscribe(new Subscriber<VideoSetModel>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(VideoSetModel videoSetModel) {
                    mVideoShowActivity.showToast(R.string.hannel_number_set_save_success);
                    mVideoShowActivity.finish();
                }
            });
        }
    }
}
