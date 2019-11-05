package com.adasplus.homepager.set.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.adasplus.base.base.BaseActivity;
import com.adasplus.base.network.ActivityPathConstant;
import com.adasplus.homepager.R;
import com.adasplus.homepager.set.mvp.contract.IVideoShowContract;
import com.adasplus.homepager.set.mvp.presenter.VideoShowPresenter;
import com.alibaba.android.arouter.facade.annotation.Route;


@Route(path = ActivityPathConstant.VIDEO_SHOW_PATH)
public class VideoShowActivity extends BaseActivity implements IVideoShowContract.View {
    private ImageView mImVideoShowBack;
    private TextView mTvVideoShowChannel;
    private ImageView mImFullScreen;
    private TextView mTvVideoShowSave;
    @Override
    protected void init(Bundle savedInstanceState) {
        VideoShowPresenter videoShowPresenter = new VideoShowPresenter(this);
        videoShowPresenter.initData();
        videoShowPresenter.initListener();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_show;
    }

    @Override
    protected void initWidget() {
        mImVideoShowBack = findViewById(R.id.iv_video_show_back);
        mTvVideoShowChannel = findViewById(R.id.tv_video_show_channel);
        mImFullScreen = findViewById(R.id.iv_full_screen);
        mTvVideoShowSave = findViewById(R.id.tv_video_show_save);
    }


    @Override
    public ImageView getImVideoShowBack() {
        return mImVideoShowBack;
    }

    @Override
    public TextView getTvVideoShowChannel() {
        return mTvVideoShowChannel;
    }

    @Override
    public ImageView getImFullScreen() {
        return mImFullScreen;
    }

    @Override
    public TextView getTvVideoShowSave() {
        return mTvVideoShowSave;
    }
}
