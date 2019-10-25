package com.adasplus.homepager.set.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.adasplus.base.base.BaseActivity;
import com.adasplus.base.view.SignSeekBar;
import com.adasplus.homepager.R;
import com.adasplus.homepager.set.mvp.contract.ISoundsContract;
import com.adasplus.homepager.set.mvp.presenter.SoundsPresenter;

public class SoundsActivity extends BaseActivity implements ISoundsContract.View {

    private ImageView mIvBack;
    private ImageView mIvSoundsReduce;
    private ImageView mIvSoundsAdd;
    private SignSeekBar mSsbSoundsValue;
    private TextView mTvCurrentSounds;
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
        mIvBack = findViewById(R.id.iv_back);
        mIvSoundsReduce = findViewById(R.id.iv_sounds_reduce);
        mIvSoundsAdd = findViewById(R.id.iv_sounds_add);
        mSsbSoundsValue = findViewById(R.id.ssb_sounds_value);
        mTvCurrentSounds = findViewById(R.id.tv_current_sounds);
        mTvSave = findViewById(R.id.tv_save);
    }

    @Override
    public ImageView getIvBack() {
        return mIvBack;
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
    public TextView getTvSave() {
        return mTvSave;
    }
}
