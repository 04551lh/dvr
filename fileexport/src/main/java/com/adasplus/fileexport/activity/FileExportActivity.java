package com.adasplus.fileexport.activity;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.adasplus.base.base.BaseActivity;
import com.adasplus.base.network.ActivityPathConstant;
import com.adasplus.fileexport.R;
import com.adasplus.fileexport.mvp.contract.IFileExportContract;
import com.adasplus.fileexport.mvp.presenter.FileExportPresenter;
import com.alibaba.android.arouter.facade.annotation.Route;

@Route(path = ActivityPathConstant.FILE_EXPORT_PATH)
public class FileExportActivity extends BaseActivity implements IFileExportContract.View {

    private ImageView mIvBack;
    private TextView mTvFileType;
    private TextView mTvSelectDate;
    private TextView mTvStartTime;
    private TextView mTvEndTime;
    private TextView mTvChannel;
    private TextView mTvStreamType;
    private TextView mTvExportFile;
    private ProgressBar mPbExportProgressbar;
    private TextView mTvCurrentExportSize;
    private TextView mTvChannelValue;
    private TextView mTvStreamTypeValue;

    @Override
    protected void init(Bundle savedInstanceState) {
        FileExportPresenter fileExportPresenter = new FileExportPresenter(this);
        fileExportPresenter.initData();
        fileExportPresenter.initListener();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_file_export;
    }

    @Override
    protected void initWidget() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mTvFileType = (TextView) findViewById(R.id.tv_file_type);
        mTvSelectDate = (TextView) findViewById(R.id.tv_select_date);
        mTvStartTime = (TextView) findViewById(R.id.tv_start_time);
        mTvEndTime = (TextView) findViewById(R.id.tv_end_time);
        mTvChannel = (TextView) findViewById(R.id.tv_channel);
        mTvStreamType = (TextView) findViewById(R.id.tv_stream_type);
        mTvExportFile = (TextView) findViewById(R.id.tv_export_file);
        mPbExportProgressbar = (ProgressBar) findViewById(R.id.pb_export_progressbar);
        mTvCurrentExportSize = (TextView) findViewById(R.id.tv_current_export_size);
        mTvChannelValue = (TextView) findViewById(R.id.tv_channel_value);
        mTvStreamTypeValue = findViewById(R.id.tv_stream_type_value);
    }

    @Override
    public ImageView getIvBack() {
        return mIvBack;
    }

    @Override
    public TextView getTvFileType() {
        return mTvFileType;
    }

    @Override
    public TextView getTvSelectDate() {
        return mTvSelectDate;
    }

    @Override
    public TextView getTvStartTime() {
        return mTvStartTime;
    }

    @Override
    public TextView getTvEndTime() {
        return mTvEndTime;
    }

    @Override
    public TextView getTvChannel() {
        return mTvChannel;
    }

    @Override
    public TextView getTvStreamType() {
        return mTvStreamType;
    }

    @Override
    public TextView getTvExportFile() {
        return mTvExportFile;
    }

    @Override
    public ProgressBar getPbExportProgressbar() {
        return mPbExportProgressbar;
    }

    @Override
    public TextView getTvCurrentExportSize() {
        return mTvCurrentExportSize;
    }

    @Override
    public TextView getTvChannelValue() {
        return mTvChannelValue;
    }

    @Override
    public TextView getTvStreamTypeValue() {
        return mTvStreamTypeValue;
    }
}
