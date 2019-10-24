package com.adasplus.homepager.set.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.adasplus.base.base.BaseActivity;
import com.adasplus.homepager.R;
import com.adasplus.homepager.set.mvp.contract.IVideoSetContract;
import com.adasplus.homepager.set.mvp.presenter.VideoSetPresenter;

public class VideoSetActivity extends BaseActivity implements IVideoSetContract.View {

    private ImageView mIvBack;
    private TextView mTvSelectChannelsNumber;
    private TextView mTvMainStreamSet;
    private TextView mTvSubStreamSet;

    private ImageView mIvStreamTotalSwitch;
    private TextView mTvVideoFrameRate;
    private TextView mTvResolutionRatio;
    private ImageView mIvDateTime;
    private ImageView mIvLicensePlateNumber;
    private ImageView mIvChannelName;
    private ImageView mIvGpsSignal;
    private ImageView mIvSpeed;
    private TextView mTvSave;
    private Button mBtnSub;
    private EditText mEtErrorNumber;
    private Button mBtnAdd;

    @Override
    protected void init(Bundle savedInstanceState) {
        VideoSetPresenter videoSetPresenter = new VideoSetPresenter(this);
        videoSetPresenter.initData();
        videoSetPresenter.initListener();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_set;
    }

    @Override
    protected void initWidget() {
        mIvBack = findViewById(R.id.iv_back);
        mTvSelectChannelsNumber = findViewById(R.id.tv_select_channels_number);
        mTvMainStreamSet = findViewById(R.id.tv_main_stream_set);
        mTvSubStreamSet =  findViewById(R.id.tv_sub_stream_set);
        mIvStreamTotalSwitch = findViewById(R.id.iv_stream_total_switch);
        mTvVideoFrameRate =  findViewById(R.id.tv_video_frame_rate);
        mTvResolutionRatio = findViewById(R.id.tv_resolution_ratio);
        mIvDateTime =  findViewById(R.id.iv_date_time);
        mIvLicensePlateNumber =  findViewById(R.id.iv_license_plate_number);
        mIvChannelName =  findViewById(R.id.iv_channel_name);
        mIvGpsSignal =  findViewById(R.id.iv_gps_signal);
        mIvSpeed =  findViewById(R.id.iv_speed);
        mTvSave =  findViewById(R.id.tv_save);
        mBtnSub =  findViewById(R.id.btn_sub);
        mEtErrorNumber =  findViewById(R.id.et_error_number);
        mEtErrorNumber.setFocusable(false);
        mBtnAdd = findViewById(R.id.btn_add);
    }

    @Override
    public ImageView getIvBack() {
        return mIvBack;
    }

    @Override
    public TextView getTvSelectChannelsNumber() {
        return mTvSelectChannelsNumber;
    }

    @Override
    public TextView getTvMainStreamSet() {
        return mTvMainStreamSet;
    }

    @Override
    public TextView getTvSubStreamSet() {
        return mTvSubStreamSet;
    }

    @Override
    public ImageView getIvStreamTotalSwitch() {
        return mIvStreamTotalSwitch;
    }

    @Override
    public TextView getTvVideoFrameRate() {
        return mTvVideoFrameRate;
    }

    @Override
    public TextView getTvResolutionRatio() {
        return mTvResolutionRatio;
    }

    @Override
    public ImageView getIvDateTime() {
        return mIvDateTime;
    }

    @Override
    public ImageView getIvLicensePlateNumber() {
        return mIvLicensePlateNumber;
    }

    @Override
    public ImageView getIvChannelName() {
        return mIvChannelName;
    }

    @Override
    public ImageView getIvGpsSignal() {
        return mIvGpsSignal;
    }

    @Override
    public ImageView getIvSpeed() {
        return mIvSpeed;
    }

    @Override
    public TextView getTvSave() {
        return mTvSave;
    }

    @Override
    public Button getBtnSub() {
        return mBtnSub;
    }

    @Override
    public EditText getEtErrorNumber() {
        return mEtErrorNumber;
    }

    @Override
    public Button getBtnAdd() {
        return mBtnAdd;
    }
}
