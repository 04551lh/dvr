package com.adasplus.settings.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.adasplus.base.base.BaseActivity;
import com.adasplus.base.view.SlideSwitchView;
import com.adasplus.settings.R;
import com.adasplus.settings.mvp.contract.IVideoSetContract;
import com.adasplus.settings.mvp.presenter.VideoSetPresenter;

public class VideoSetActivity extends BaseActivity implements IVideoSetContract.View {

    private ImageView mIvBack;
    private TextView mTvSelectChannelsNumber;
    private TextView mTvMainStreamSet;
    private TextView mTvSubStreamSet;
    private SlideSwitchView mSsvStreamTotalSwitch;
    private TextView mTvVideoFrameRate;
    private TextView mTvResolutionRatio;
    private SlideSwitchView mSsvDateTime;
    private SlideSwitchView mSsvLicensePlateNumber;
    private SlideSwitchView mSsvChannelName;
    private SlideSwitchView mSsvGpsSignal;
    private SlideSwitchView mSsvSpeed;
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
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mTvSelectChannelsNumber = (TextView) findViewById(R.id.tv_select_channels_number);
        mTvMainStreamSet = (TextView) findViewById(R.id.tv_main_stream_set);
        mTvSubStreamSet = (TextView) findViewById(R.id.tv_sub_stream_set);
        mSsvStreamTotalSwitch = (SlideSwitchView) findViewById(R.id.ssv_stream_total_switch);
        mTvVideoFrameRate = (TextView) findViewById(R.id.tv_video_frame_rate);
        mTvResolutionRatio = (TextView) findViewById(R.id.tv_resolution_ratio);
        mSsvDateTime = (SlideSwitchView) findViewById(R.id.ssv_date_time);
        mSsvLicensePlateNumber = (SlideSwitchView) findViewById(R.id.ssv_license_plate_number);
        mSsvChannelName = (SlideSwitchView) findViewById(R.id.ssv_channel_name);
        mSsvGpsSignal = (SlideSwitchView) findViewById(R.id.ssv_gps_signal);
        mSsvSpeed = (SlideSwitchView) findViewById(R.id.ssv_speed);
        mTvSave = (TextView) findViewById(R.id.tv_save);
        mBtnSub = (Button) findViewById(R.id.btn_sub);
        mEtErrorNumber = (EditText) findViewById(R.id.et_error_number);
        mEtErrorNumber.setFocusable(false);
        mBtnAdd = (Button) findViewById(R.id.btn_add);
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
    public SlideSwitchView getSsvStreamTotalSwitch() {
        return mSsvStreamTotalSwitch;
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
    public SlideSwitchView getSsvDateTime() {
        return mSsvDateTime;
    }

    @Override
    public SlideSwitchView getSsvLicensePlateNumber() {
        return mSsvLicensePlateNumber;
    }

    @Override
    public SlideSwitchView getSsvChannelName() {
        return mSsvChannelName;
    }

    @Override
    public SlideSwitchView getSsvGpsSignal() {
        return mSsvGpsSignal;
    }

    @Override
    public SlideSwitchView getSsvSpeed() {
        return mSsvSpeed;
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
