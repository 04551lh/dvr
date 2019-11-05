package com.adasplus.dvr_controller.activity;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adasplus.dvr_controller.R;
import com.adasplus.base.base.BaseActivity;
import com.adasplus.dvr_controller.mvp.contract.IMainContract;
import com.adasplus.dvr_controller.mvp.presenter.MainPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.header.TwoLevelHeader;


public class MainActivity extends BaseActivity implements IMainContract.View {

    private LinearLayout mLlHomePager;
    private LinearLayout mLlBasicInfoPager;
    private LinearLayout mLlFileExportPager;
    private ImageView mIvHomePager;
    private TextView mTvHomePager;
    private ImageView mIvBasicInfoPager;
    private TextView mTvBasicInfoPager;
    private ImageView mIvFileExportPager;
    private TextView mTvFileExportPager;
    private MainPresenter mMainPresenter;
    private ImageView mIvFourGSignalStatus;
    private TextView mTvFourGSignalLevel;
    private ImageView mIvLocationStatus;
    private TextView mTvLocationStatus;
    private ImageView mIvFarLightStatus;
    private TextView mTvFarLightStatus;
    private ImageView mIvNearLightStatus;
    private TextView mTvNearLightStatus;
    private ImageView mIvLeftTurnStatus;
    private TextView mTvLeftTurnStatus;
    private ImageView mIvRightTurnStatus;
    private TextView mTvRightTurnStatus;
    private ImageView mIvBrakeStatus;
    private TextView mTvBrakeStatus;
    private ImageView mIvTargetsPlatformStatus;
    private TextView mTvTargetsPlatformStatus;
    private ImageView mIvCloseMenu;
    private SmartRefreshLayout mSrlRefreshLayout;
    private TwoLevelHeader mTlhHeaderView;
    private LinearLayout mLlCloseMenu;

    @Override
    protected void init(Bundle savedInstanceState) {
        mMainPresenter = new MainPresenter(this);
        mMainPresenter.initWidget();
        mMainPresenter.initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMainPresenter.initData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        mLlHomePager = findViewById(R.id.ll_home_pager);
        mLlBasicInfoPager = findViewById(R.id.ll_basic_info_pager);
        mLlFileExportPager = findViewById(R.id.ll_file_export_pager);
        mIvHomePager = findViewById(R.id.iv_home_pager);
        mTvHomePager = findViewById(R.id.tv_home_pager);
        mIvBasicInfoPager = findViewById(R.id.iv_basic_info_pager);
        mTvBasicInfoPager = findViewById(R.id.tv_basic_info_pager);
        mIvFileExportPager = findViewById(R.id.iv_file_export_pager);
        mTvFileExportPager = findViewById(R.id.tv_file_export_pager);
        mIvFourGSignalStatus = findViewById(R.id.iv_four_g_signal_status);
        mTvFourGSignalLevel = findViewById(R.id.tv_four_g_signal_level);
        mIvLocationStatus = findViewById(R.id.iv_location_status);
        mTvLocationStatus = findViewById(R.id.tv_location_status);
        mIvFarLightStatus = findViewById(R.id.iv_far_light_status);
        mTvFarLightStatus = findViewById(R.id.tv_far_light_status);
        mIvNearLightStatus = findViewById(R.id.iv_near_light_status);
        mTvNearLightStatus = findViewById(R.id.tv_near_light_status);
        mIvLeftTurnStatus = findViewById(R.id.iv_left_turn_status);
        mTvLeftTurnStatus = findViewById(R.id.tv_left_turn_status);
        mIvRightTurnStatus = findViewById(R.id.iv_right_turn_status);
        mTvRightTurnStatus = findViewById(R.id.tv_right_turn_status);
        mIvBrakeStatus = findViewById(R.id.iv_brake_status);
        mTvBrakeStatus = findViewById(R.id.tv_brake_status);
        mIvTargetsPlatformStatus = findViewById(R.id.iv_targets_platform_status);
        mTvTargetsPlatformStatus = findViewById(R.id.tv_targets_platform_status);
        mIvCloseMenu = findViewById(R.id.iv_close_menu);
        mSrlRefreshLayout = findViewById(R.id.srl_refresh_layout);
        mTlhHeaderView = findViewById(R.id.tlh_header_view);
        mLlCloseMenu = findViewById(R.id.ll_close_menu);
        this.getSupportFragmentManager();
    }

    @Override
    public LinearLayout getLlHomePager() {
        return mLlHomePager;
    }

    @Override
    public LinearLayout getLlBasicInfoPager() {
        return mLlBasicInfoPager;
    }

    @Override
    public LinearLayout getLlFileExportPager() {
        return mLlFileExportPager;
    }

    @Override
    public ImageView getIvHomePager() {
        return mIvHomePager;
    }

    @Override
    public TextView getTvHomePager() {
        return mTvHomePager;
    }

    @Override
    public ImageView getIvBasicInfoPager() {
        return mIvBasicInfoPager;
    }

    @Override
    public TextView getTvBasicInfoPager() {
        return mTvBasicInfoPager;
    }

    @Override
    public ImageView getIvFileExportPager() {
        return mIvFileExportPager;
    }

    @Override
    public TextView getTvFileExportPager() {
        return mTvFileExportPager;
    }

    @Override
    public ImageView getIvFourGSignalStatus() {
        return mIvFourGSignalStatus;
    }

    @Override
    public TextView getTvFourGSignalLevel() {
        return mTvFourGSignalLevel;
    }

    @Override
    public ImageView getIvLocationStatus() {
        return mIvLocationStatus;
    }

    @Override
    public TextView getTvLocationStatus() {
        return mTvLocationStatus;
    }

    @Override
    public ImageView getIvFarLightStatus() {
        return mIvFarLightStatus;
    }

    @Override
    public TextView getTvFarLightStatus() {
        return mTvFarLightStatus;
    }

    @Override
    public ImageView getIvNearLightStatus() {
        return mIvNearLightStatus;
    }

    @Override
    public TextView getTvNearLightStatus() {
        return mTvNearLightStatus;
    }

    @Override
    public ImageView getIvLeftTurnStatus() {
        return mIvLeftTurnStatus;
    }

    @Override
    public TextView getTvLeftTurnStatus() {
        return mTvLeftTurnStatus;
    }

    @Override
    public ImageView getIvRightTurnStatus() {
        return mIvRightTurnStatus;
    }

    @Override
    public TextView getTvRightTurnStatus() {
        return mTvRightTurnStatus;
    }

    @Override
    public ImageView getIvBrakeStatus() {
        return mIvBrakeStatus;
    }

    @Override
    public TextView getTvBrakeStatus() {
        return mTvBrakeStatus;
    }

    @Override
    public ImageView getIvTargetsPlatformStatus() {
        return mIvTargetsPlatformStatus;
    }

    @Override
    public TextView getTvTargetsPlatformStatus() {
        return mTvTargetsPlatformStatus;
    }

    @Override
    public ImageView getIvCloseMenu() {
        return mIvCloseMenu;
    }

    @Override
    public SmartRefreshLayout getSrlRefreshLayout() {
        return mSrlRefreshLayout;
    }

    @Override
    public TwoLevelHeader getTlhHeaderView() {
        return mTlhHeaderView;
    }

    @Override
    public LinearLayout getLlCloseMenu() {
        return mLlCloseMenu;
    }
}
