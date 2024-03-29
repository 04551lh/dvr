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

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class VideoSetActivity extends BaseActivity implements IVideoSetContract.View {

    private ImageView mIvVideoBack;
    private TextView mTvSelectChannelsNumber;
    private SwipeRefreshLayout mSwipeRefreshLayoutVideoSet;
    private TextView mTvMainStreamSet;
    private TextView mTvSubStreamSet;
    private ImageView mIvStreamTotalSwitch;
    private EditText mEtVideoFrameRate;
    private TextView mTvResolutionRatio;
    private ImageView mIvDateTime;
    private ImageView mIvLicensePlateNumber;
    private ImageView mIvChannelName;
    private ImageView mIvGpsSignal;
    private ImageView mIvSpeed;
    private TextView mTvVideoSave;
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
        mIvVideoBack = findViewById(R.id.iv_video_back);
        mSwipeRefreshLayoutVideoSet = findViewById(R.id.swipeRefreshLayout_video_set);
        mTvSelectChannelsNumber = findViewById(R.id.tv_select_channels_number);
        mTvMainStreamSet = findViewById(R.id.tv_main_stream_set);
        mTvSubStreamSet =  findViewById(R.id.tv_sub_stream_set);
        mIvStreamTotalSwitch = findViewById(R.id.iv_stream_total_switch);
        mEtVideoFrameRate =  findViewById(R.id.et_video_frame_rate);
        mTvResolutionRatio = findViewById(R.id.tv_resolution_ratio);
        mIvDateTime =  findViewById(R.id.iv_date_time);
        mIvLicensePlateNumber =  findViewById(R.id.iv_license_plate_number);
        mIvChannelName =  findViewById(R.id.iv_channel_name);
        mIvGpsSignal =  findViewById(R.id.iv_gps_signal);
        mIvSpeed =  findViewById(R.id.iv_speed);
        mTvVideoSave =  findViewById(R.id.tv_video_save);
        mBtnSub =  findViewById(R.id.btn_sub);
        mEtErrorNumber =  findViewById(R.id.et_error_number);
        mEtErrorNumber.setFocusable(false);
        mBtnAdd = findViewById(R.id.btn_add);
    }

    @Override
    public ImageView getIvVideoBack() {
        return mIvVideoBack;
    }

    @Override
    public SwipeRefreshLayout getSwipeRefreshLayoutVideoSet() {
        return mSwipeRefreshLayoutVideoSet;
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
    public EditText getEtVideoFrameRate() {
        return mEtVideoFrameRate;
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
    public TextView getTvVideoSave() {
        return mTvVideoSave;
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
