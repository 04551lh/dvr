package com.adasplus.dvr_controller.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.adasplus.base.base.BaseActivity;
import com.adasplus.dvr_controller.R;
import com.adasplus.dvr_controller.mvp.contract.IExportFileContract;
import com.adasplus.dvr_controller.mvp.presenter.ExportFilePresenter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class FileExportActivity extends BaseActivity implements IExportFileContract.View {

    private ImageView mIvExportBack;
    private SwipeRefreshLayout mSrlRefreshFileExportData;
    private TextView mTvFileType;
    private TextView mTvChannelValue;
    private TextView mTvStreamTypeValue;
    private TextView mTvStartDate;
    private TextView mTvStartTime;
    private TextView mTvEndData;
    private TextView mTvEndTime;
    private TextView mTvStorageTypeValue;
    private TextView mTvStorageNameValue;
    private TextView mTvExportFile;

    @Override
    protected void init(Bundle savedInstanceState) {
        ExportFilePresenter mExportFilePresenter = new ExportFilePresenter(this);
        mExportFilePresenter.initData();
        mExportFilePresenter.initListener();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_file_export;
    }

    @Override
    protected void initWidget() {
        mIvExportBack = findViewById(R.id.iv_export_back);
        mTvFileType = findViewById(R.id.tv_file_type);
        mTvChannelValue = findViewById(R.id.tv_channel_value);
        mTvStreamTypeValue =findViewById(R.id.tv_stream_type_value);
        mTvStartDate =findViewById(R.id.tv_start_date);
        mTvStartTime =findViewById(R.id.tv_start_time);
        mTvEndData = findViewById(R.id.tv_end_date);
        mTvEndTime = findViewById(R.id.tv_end_time);
        mTvStorageTypeValue = findViewById(R.id.tv_storage_type_value);
        mTvStorageNameValue = findViewById(R.id.tv_storage_name_value);
        mTvExportFile =findViewById(R.id.tv_export_file);
        mSrlRefreshFileExportData = findViewById(R.id.srl_refresh_file_export_data);
    }

    @Override
    public ImageView getIvExportBack() {
        return mIvExportBack;
    }

    @Override
    public SwipeRefreshLayout getSrlRefreshFileExportData() {
        return mSrlRefreshFileExportData;
    }

    @Override
    public TextView getTvFileType() {
        return mTvFileType;
    }

    @Override
    public TextView getTvChannelValue() {
        return mTvChannelValue;
    }

    @Override
    public TextView getTvStreamTypeValue() {
        return mTvStreamTypeValue;
    }

    @Override
    public TextView getTvStartDate() {
        return mTvStartDate;
    }

    @Override
    public TextView getTvStartTime() {
        return mTvStartTime;
    }

    @Override
    public TextView getTvEndData() {
        return mTvEndData;
    }

    @Override
    public TextView getTvEndTime() {
        return mTvEndTime;
    }

    @Override
    public TextView getTvStorageTypeValue() {
        return mTvStorageTypeValue;
    }

    @Override
    public TextView getTvStorageNameValue() {
        return mTvStorageNameValue;
    }

    @Override
    public TextView getTvExportFile() {
        return mTvExportFile;
    }
}
