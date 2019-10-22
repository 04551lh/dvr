package com.adasplus.homepager.set.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.adasplus.base.base.BaseActivity;
import com.adasplus.homepager.R;
import com.adasplus.homepager.set.mvp.contract.ICommonSetContract;
import com.adasplus.homepager.set.mvp.presenter.CommonSetPresenter;

public class CommonSetActivity extends BaseActivity implements ICommonSetContract.View {

    private ImageView mIvBack;
    private LinearLayout mLlTimeSet;
    private LinearLayout mLlSoundSet;
    private LinearLayout mLlDormancySet;
    private LinearLayout mLlRebootSet;
    private LinearLayout mLlFactoryDataReset;
    private LinearLayout mLlDeviceFormat;

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
        mLlTimeSet = (LinearLayout) findViewById(R.id.ll_time_set);
        mLlSoundSet = (LinearLayout) findViewById(R.id.ll_sound_set);
        mLlDormancySet = (LinearLayout) findViewById(R.id.ll_dormancy_set);
        mLlRebootSet = (LinearLayout) findViewById(R.id.ll_reboot_set);
        mLlFactoryDataReset = (LinearLayout) findViewById(R.id.ll_factory_data_reset);
        mLlDeviceFormat = (LinearLayout) findViewById(R.id.ll_device_format);
    }


    @Override
    public ImageView getIvBack() {
        return mIvBack;
    }

    @Override
    public LinearLayout getLlTimeSet() {
        return mLlTimeSet;
    }

    @Override
    public LinearLayout getLlSoundSet() {
        return mLlSoundSet;
    }

    @Override
    public LinearLayout getLlDormancySet() {
        return mLlDormancySet;
    }

    @Override
    public LinearLayout getLlRebootSet() {
        return mLlRebootSet;
    }

    @Override
    public LinearLayout getLlFactoryDataReset() {
        return mLlFactoryDataReset;
    }

    @Override
    public LinearLayout getLlDeviceFormat() {
        return mLlDeviceFormat;
    }
}
