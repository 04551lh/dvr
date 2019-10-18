package com.adasplus.homepager.set.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.adasplus.base.base.BaseActivity;
import com.adasplus.homepager.R;
import com.adasplus.homepager.set.mvp.contract.ICommonSetContract;
import com.adasplus.homepager.set.mvp.presenter.CommonSetPresenter;

public class CommonSetActivity extends BaseActivity implements ICommonSetContract.View {

    private ImageView mIvBack;
    private TextView mTvTimeSet;
    private TextView mTvSoundSet;
    private TextView mTvDormancySet;
    private TextView mTvRebootSet;
    private TextView mTvFactoryDataReset;
    private TextView mTvDeviceFormat;

    @Override
    protected void init(Bundle savedInstanceState) {
        CommonSetPresenter commonSetPresenter = new CommonSetPresenter(this);
        commonSetPresenter.initData();
        commonSetPresenter.initListener();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_common_set;
    }

    @Override
    protected void initWidget() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mTvTimeSet = (TextView) findViewById(R.id.tv_time_set);
        mTvSoundSet = (TextView) findViewById(R.id.tv_sound_set);
        mTvDormancySet = (TextView) findViewById(R.id.tv_dormancy_set);
        mTvRebootSet = (TextView) findViewById(R.id.tv_reboot_set);
        mTvFactoryDataReset = (TextView) findViewById(R.id.tv_factory_data_reset);
        mTvDeviceFormat = (TextView) findViewById(R.id.tv_device_format);
    }


    @Override
    public ImageView getIvBack() {
        return mIvBack;
    }

    @Override
    public TextView getTvTimeSet() {
        return mTvTimeSet;
    }

    @Override
    public TextView getTvSoundSet() {
        return mTvSoundSet;
    }

    @Override
    public TextView getTvDormancySet() {
        return mTvDormancySet;
    }

    @Override
    public TextView getTvRebootSet() {
        return mTvRebootSet;
    }

    @Override
    public TextView getTvFactoryDataReset() {
        return mTvFactoryDataReset;
    }

    @Override
    public TextView getTvDeviceFormat() {
        return mTvDeviceFormat;
    }
}
