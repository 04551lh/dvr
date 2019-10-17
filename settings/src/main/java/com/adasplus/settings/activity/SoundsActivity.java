package com.adasplus.settings.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.adasplus.base.base.BaseActivity;
import com.adasplus.settings.R;
import com.adasplus.settings.mvp.contract.ISoundsContract;
import com.adasplus.settings.mvp.presenter.SoundsPresenter;

public class SoundsActivity extends BaseActivity implements ISoundsContract.View {

    private ImageView mIvBack;
    private ImageView mIvMuteIcon;
    private SeekBar mSbSoundsSize;
    private ImageView mIvSoundsIcon;
    private TextView mTvSave;

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
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvMuteIcon = (ImageView) findViewById(R.id.iv_mute_icon);
        mSbSoundsSize = (SeekBar) findViewById(R.id.sb_sounds_size);
        mIvSoundsIcon = (ImageView) findViewById(R.id.iv_sounds_icon);
        mTvSave = (TextView) findViewById(R.id.tv_save);
    }

    @Override
    public ImageView getIvBack() {
        return mIvBack;
    }

    @Override
    public SeekBar getSbSoundsSize() {
        return mSbSoundsSize;
    }

    @Override
    public TextView getTvSave() {
        return mTvSave;
    }
}
