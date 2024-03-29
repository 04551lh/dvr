package com.adasplus.homepager.set.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.adasplus.base.base.BaseActivity;
import com.adasplus.base.view.SignSeekBar;
import com.adasplus.homepager.R;
import com.adasplus.homepager.set.mvp.contract.ISoundsContract;
import com.adasplus.homepager.set.mvp.presenter.SoundsPresenter;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class SoundsActivity extends BaseActivity implements ISoundsContract.View {

    private ImageView mIvSoundBack;
    private ImageView mIvSoundsReduce;
    private ImageView mIvSoundsAdd;
    private SignSeekBar mSsbSoundsValue;
    private TextView mTvCurrentSounds;
    private TextView mTvSoundSave;
    private TextView mTvSoundType;
    private ImageView mIvSoundType;


    @Override
    protected void init(Bundle savedInstanceState) {
        SoundsPresenter soundsPresenter = new SoundsPresenter(this);
        soundsPresenter.initData();
        soundsPresenter.initListener();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sounds;
    }

    @Override
    protected void initWidget() {
        mTvSoundType = findViewById(R.id.tv_sound_type);
        mIvSoundType = findViewById(R.id.iv_sound_type);
        mIvSoundBack = findViewById(R.id.iv_sound_back);
        mIvSoundsReduce = findViewById(R.id.iv_sounds_reduce);
        mIvSoundsAdd = findViewById(R.id.iv_sounds_add);
        mSsbSoundsValue = findViewById(R.id.ssb_sounds_value);
        mTvCurrentSounds = findViewById(R.id.tv_current_sounds);
        mTvSoundSave = findViewById(R.id.tv_sound_save);
    }

    @Override
    public ImageView getIvSoundBack() {
        return mIvSoundBack;
    }

    @Override
    public ImageView getIvSoundsReduce() {
        return mIvSoundsReduce;
    }

    @Override
    public ImageView getIvSoundsAdd() {
        return mIvSoundsAdd;
    }

    @Override
    public SignSeekBar getSsbSoundsValue() {
        return mSsbSoundsValue;
    }

    @Override
    public TextView getTvCurrentSounds() {
        return mTvCurrentSounds;
    }
    @Override
    public TextView getTvSoundSave() {
        return mTvSoundSave;
    }

    @Override
    public TextView getTvSoundType() {
        return mTvSoundType;
    }

    @Override
    public ImageView getIvSoundType() {
        return mIvSoundType;
    }
}
