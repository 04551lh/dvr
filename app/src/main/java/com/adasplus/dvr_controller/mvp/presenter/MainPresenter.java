package com.adasplus.dvr_controller.mvp.presenter;

import android.graphics.Color;
import android.net.wifi.WifiInfo;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.adasplus.base.network.BaseWrapper;
import com.adasplus.base.network.HttpConstant;
import com.adasplus.base.network.model.SystemInfoModel;

import com.adasplus.base.utils.ExceptionUtils;
import com.adasplus.base.utils.StatusBarUtil;
import com.adasplus.base.utils.ToastUtil;
import com.adasplus.base.utils.UIClickUtils;
import com.adasplus.base.utils.WifiHelper;
import com.adasplus.dvr_controller.R;
import com.adasplus.dvr_controller.activity.MainActivity;
import com.adasplus.dvr_controller.fragment.BasicInfoFragment;
import com.adasplus.dvr_controller.fragment.FileExportFragment;
import com.adasplus.dvr_controller.fragment.HomeFragment;
import com.adasplus.dvr_controller.mvp.contract.IMainContract;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.header.TwoLevelHeader;
import com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener;

import java.util.List;

import rx.Subscriber;

/**
 * Author:刘净辉
 * Date : 2019/9/10
 * Description :
 */
public class MainPresenter implements IMainContract.Presenter, View.OnClickListener {

    private IMainContract.View mMainView;

    private MainActivity mMainActivity;
    private HomeFragment mHomeFragment = new HomeFragment();
    private BasicInfoFragment mBasicInfoFragment = new BasicInfoFragment();
    private FileExportFragment mFileExportFragment = new FileExportFragment();

    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int FOUR = 4;
    private static final int FIVE = 5;

    private ImageView mIvHomePager;
    private TextView mTvHomePager;
    private LinearLayout mLlHomePager;
    private LinearLayout mLlBasicInfoPager;
    private LinearLayout mLlFileExportPager;
    private ImageView mIvBasicInfoPager;
    private TextView mTvBasicInfoPager;
    private ImageView mIvFileExportPager;
    private TextView mTvFileExportPager;
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

    //判断是否USB
    private boolean mUSB = true;

    public MainPresenter(IMainContract.View view) {
        mMainView = view;
        mMainActivity = (MainActivity) mMainView;
    }

    @Override
    public void initWidget() {
        mLlHomePager = mMainView.getLlHomePager();
        mLlBasicInfoPager = mMainView.getLlBasicInfoPager();
        mLlFileExportPager = mMainView.getLlFileExportPager();
        mIvHomePager = mMainView.getIvHomePager();
        mTvHomePager = mMainView.getTvHomePager();
        mIvBasicInfoPager = mMainView.getIvBasicInfoPager();
        mTvBasicInfoPager = mMainView.getTvBasicInfoPager();
        mIvFileExportPager = mMainView.getIvFileExportPager();
        mTvFileExportPager = mMainView.getTvFileExportPager();
        mIvFourGSignalStatus = mMainView.getIvFourGSignalStatus();
        mTvFourGSignalLevel = mMainView.getTvFourGSignalLevel();
        mIvLocationStatus = mMainView.getIvLocationStatus();
        mTvLocationStatus = mMainView.getTvLocationStatus();
        mIvFarLightStatus = mMainView.getIvFarLightStatus();
        mTvFarLightStatus = mMainView.getTvFarLightStatus();
        mIvNearLightStatus = mMainView.getIvNearLightStatus();
        mTvNearLightStatus = mMainView.getTvNearLightStatus();
        mIvLeftTurnStatus = mMainView.getIvLeftTurnStatus();
        mTvLeftTurnStatus = mMainView.getTvLeftTurnStatus();
        mIvRightTurnStatus = mMainView.getIvRightTurnStatus();
        mTvRightTurnStatus = mMainView.getTvRightTurnStatus();
        mIvBrakeStatus = mMainView.getIvBrakeStatus();
        mTvBrakeStatus = mMainView.getTvBrakeStatus();
        mIvTargetsPlatformStatus = mMainView.getIvTargetsPlatformStatus();
        mTvTargetsPlatformStatus = mMainView.getTvTargetsPlatformStatus();
        mIvCloseMenu = mMainView.getIvCloseMenu();
        mSrlRefreshLayout = mMainView.getSrlRefreshLayout();
        mTlhHeaderView = mMainView.getTlhHeaderView();
        mLlCloseMenu = mMainView.getLlCloseMenu();

        initFragment();

        mSrlRefreshLayout.setOnMultiPurposeListener(new OnMultiPurposeListener() {
            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {

            }

            @Override
            public void onHeaderReleased(RefreshHeader header, int headerHeight, int maxDragHeight) {

            }

            @Override
            public void onHeaderStartAnimator(RefreshHeader header, int headerHeight, int maxDragHeight) {

            }

            @Override
            public void onHeaderFinish(RefreshHeader header, boolean success) {

            }

            @Override
            public void onFooterMoving(RefreshFooter footer, boolean isDragging, float percent, int offset, int footerHeight, int maxDragHeight) {

            }

            @Override
            public void onFooterReleased(RefreshFooter footer, int footerHeight, int maxDragHeight) {

            }

            @Override
            public void onFooterStartAnimator(RefreshFooter footer, int footerHeight, int maxDragHeight) {

            }

            @Override
            public void onFooterFinish(RefreshFooter footer, boolean success) {

            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                initData();
            }

            @Override
            public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
                if (oldState == RefreshState.TwoLevelReleased) {
                    mMainActivity.findViewById(R.id.fl_plate_info_status).animate().alpha(1).setDuration(300);
                } else if (oldState == RefreshState.TwoLevel) {
                    mMainActivity.findViewById(R.id.fl_plate_info_status).animate().alpha(0).setDuration(300);
                }

            }
        });

        StatusBarUtil.setMargin(mMainActivity, mMainActivity.findViewById(R.id.ch_header));
    }

    private Fragment getCurrentFragment() {
        List<Fragment> fragments = mMainActivity.getSupportFragmentManager().getFragments();
        for (int i = 0; i < fragments.size(); i++) {
            Fragment fragment = fragments.get(i);
            if (fragment != null && fragment.isAdded()) {
                return fragment;
            }
        }
        return null;
    }

    @Override
    public void initData() {
        Fragment currentFragment = getCurrentFragment();
        if (currentFragment instanceof HomeFragment) {
                BaseWrapper.getInstance().getSystemInfo().subscribe(new Subscriber<SystemInfoModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mUSB = true;
                        mHomeFragment.setUSBStatus(true);
                        mSrlRefreshLayout.finishRefresh();
                        ExceptionUtils.exceptionHandling(mMainActivity, e);
                    }

                    @Override
                    public void onNext(SystemInfoModel systemInfoModel) {
                        mUSB = false;
                        mHomeFragment.setUSBStatus(false);
                        mSrlRefreshLayout.finishRefresh();
                        mHomeFragment.initData(systemInfoModel);
                        initSlideCarInfoData(systemInfoModel);
                    }
                });
        }
    }

    private void initSlideCarInfoData(SystemInfoModel systemInfoModel) {
        SystemInfoModel.GpsBean gps = systemInfoModel.getGps();
        List<SystemInfoModel.PlatformStatusArrayBean> platformStatusArray = systemInfoModel.getPlatformStatusArray();
        SystemInfoModel.VehicleStatusInfoBean vehicleStatusInfo = systemInfoModel.getVehicleStatusInfo();
        int nearTurnLightStatus = vehicleStatusInfo.getNearTurnLightStatus();
        int farTurnLightStatus = vehicleStatusInfo.getFarTurnLightStatus();
        int leftTurnLightStatus = vehicleStatusInfo.getLeftTurnLightStatus();
        int rightTurnLightStatus = vehicleStatusInfo.getRightTurnLightStatus();
        int brakeStatus = vehicleStatusInfo.getBrakeStatus();
        SystemInfoModel.FourGBean fourG = systemInfoModel.getFourG();
        int fourGLSignalLevel = fourG.getFourGSignalLevel() / 6;

        //定位状态
        int gpsValid = gps.getGpsValid();
        if (ONE == gpsValid) {
            mTvLocationStatus.setText(R.string.have_location);
            mIvLocationStatus.setImageResource(R.drawable.location_signal_checked_icon);
        } else if (ZERO == gpsValid) {
            mTvLocationStatus.setText(R.string.no_location);
            mIvLocationStatus.setImageResource(R.drawable.location_signal_unchecked_icon);
        }

        //近光灯状态 0:已接入，未开启 1: 已接入，打开 2: 已接入，关闭
        if (ZERO == nearTurnLightStatus || TWO == nearTurnLightStatus) {
            mIvNearLightStatus.setImageResource(R.drawable.near_light_unchecked_icon);
            mTvNearLightStatus.setText(R.string.close);
        } else if (ONE == nearTurnLightStatus) {
            mTvNearLightStatus.setText(R.string.open);
            mIvNearLightStatus.setImageResource(R.drawable.near_light_checked_icon);
        }

        //远光灯状态
        if (ZERO == farTurnLightStatus || TWO == farTurnLightStatus) {
            mIvFarLightStatus.setImageResource(R.drawable.far_light_unchecked_icon);
            mTvFarLightStatus.setText(R.string.close);
        } else if (ONE == farTurnLightStatus) {
            mTvFarLightStatus.setText(R.string.open);
            mIvFarLightStatus.setImageResource(R.drawable.far_light_checked_icon);
        }

        //左转向灯状态
        if (ZERO == leftTurnLightStatus || TWO == leftTurnLightStatus) {
            mTvLeftTurnStatus.setText(R.string.close);
            mIvLeftTurnStatus.setImageResource(R.drawable.left_turn_signal_unchecked_icon);
        } else if (ONE == leftTurnLightStatus) {
            mTvLeftTurnStatus.setText(R.string.open);
            mIvLeftTurnStatus.setImageResource(R.drawable.left_turn_signal_checked_icon);
        }

        //右转向灯状态
        if (ZERO == rightTurnLightStatus || TWO == rightTurnLightStatus) {
            mTvRightTurnStatus.setText(R.string.close);
            mIvRightTurnStatus.setImageResource(R.drawable.right_turn_signal_unchecked_icon);
        } else if (ONE == rightTurnLightStatus) {
            mTvRightTurnStatus.setText(R.string.open);
            mIvRightTurnStatus.setImageResource(R.drawable.right_turn_signal_checked_icon);
        }

        //刹车状态
        if (ONE == brakeStatus) {
            mTvBrakeStatus.setText(R.string.open);
            mIvBrakeStatus.setImageResource(R.drawable.brake_checked_icon);
        } else if (0 == brakeStatus) {
            mTvBrakeStatus.setText(R.string.close);
            mIvBrakeStatus.setImageResource(R.drawable.brake_unchecked_icon);
        }

        //部标平台是否有连接的状态
        boolean isConnectTargetsPlatforms = false;
        for (int i = 0; i < platformStatusArray.size(); i++) {
            SystemInfoModel.PlatformStatusArrayBean platformStatusArrayBean = platformStatusArray.get(i);
            int connectStatus = platformStatusArrayBean.getConnectStatus();
            if (connectStatus == 1) {
                isConnectTargetsPlatforms = true;
                break;
            }
        }

        if (isConnectTargetsPlatforms) {
            mTvTargetsPlatformStatus.setText(R.string.open);
            mIvTargetsPlatformStatus.setImageResource(R.drawable.targets_platform_checked_icon);
        } else {
            mTvTargetsPlatformStatus.setText(R.string.close);
            mIvTargetsPlatformStatus.setImageResource(R.drawable.targets_platform_unchecked_icon);
        }

        if (ZERO == fourGLSignalLevel) {
            mTvFourGSignalLevel.setText(R.string.no_signal);
        } else if (fourGLSignalLevel <= TWO) {
            mTvFourGSignalLevel.setText(R.string.signal_weak);
        } else if (fourGLSignalLevel <= FIVE) {
            mTvFourGSignalLevel.setText(R.string.signal_strong);
        }

        if (ZERO == fourGLSignalLevel) {
            mIvFourGSignalStatus.setImageResource(R.mipmap.four_g_no_signal);
        } else if (ONE == fourGLSignalLevel) {
            mIvFourGSignalStatus.setImageResource(R.mipmap.four_g_signal_1);
        } else if (TWO == fourGLSignalLevel) {
            mIvFourGSignalStatus.setImageResource(R.mipmap.four_g_signal_2);
        } else if (THREE == fourGLSignalLevel) {
            mIvFourGSignalStatus.setImageResource(R.mipmap.four_g_signal_3);
        } else if (FOUR == fourGLSignalLevel) {
            mIvFourGSignalStatus.setImageResource(R.mipmap.four_g_signal_4);
        } else if (FIVE == fourGLSignalLevel) {
            mIvFourGSignalStatus.setImageResource(R.mipmap.four_g_signal_full_icon);
        }
    }

    @Override
    public void initListener() {
        mLlHomePager.setOnClickListener(this);
        mLlBasicInfoPager.setOnClickListener(this);
        mLlFileExportPager.setOnClickListener(this);
        mIvCloseMenu.setOnClickListener(this);
        mLlCloseMenu.setOnClickListener(this);
    }

    private void initFragment() {
        FragmentTransaction fragmentTransaction = mMainActivity.getSupportFragmentManager().beginTransaction();
        if (!mHomeFragment.isAdded()) {
            fragmentTransaction.add(R.id.fl_frame_layout, mHomeFragment);
            fragmentTransaction.hide(mHomeFragment);
        }

        if (!mBasicInfoFragment.isAdded()) {
            fragmentTransaction.add(R.id.fl_frame_layout, mBasicInfoFragment);
            fragmentTransaction.hide(mBasicInfoFragment);
        }

        if (!mFileExportFragment.isAdded()) {
            fragmentTransaction.add(R.id.fl_frame_layout, mFileExportFragment);
            fragmentTransaction.hide(mFileExportFragment);
        }


        hideAllFragment(fragmentTransaction);

        fragmentTransaction.commit();
        clickTab(mHomeFragment);
    }

    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        fragmentTransaction.hide(mHomeFragment);
        fragmentTransaction.hide(mBasicInfoFragment);
        fragmentTransaction.hide(mFileExportFragment);
    }

    private void clickTab(Fragment fragment) {
        clearSelected();

        FragmentTransaction fragmentTransaction = mMainActivity.getSupportFragmentManager().beginTransaction();
        hideAllFragment(fragmentTransaction);
        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();

        changeTabStyle(fragment);
    }

    private void changeTabStyle(Fragment fragment) {
        if (fragment instanceof HomeFragment) {
            mIvHomePager.setImageResource(R.mipmap.home_checked_icon);
            mTvHomePager.setTextColor(Color.parseColor("#5677FC"));
        }

        if (fragment instanceof BasicInfoFragment) {
            mIvBasicInfoPager.setImageResource(R.mipmap.basic_info_checked_icon);
            mTvBasicInfoPager.setTextColor(Color.parseColor("#5677FC"));
        }

        if (fragment instanceof FileExportFragment) {
            mIvFileExportPager.setImageResource(R.mipmap.file_export_checked_icon);
            mTvFileExportPager.setTextColor(Color.parseColor("#5677FC"));
        }
    }

    private void clearSelected() {
        if (!mHomeFragment.isHidden()) {
            mIvHomePager.setImageResource(R.mipmap.home_unchecked);
            mTvHomePager.setTextColor(Color.BLACK);
        }

        if (!mBasicInfoFragment.isHidden()) {
            mIvBasicInfoPager.setImageResource(R.mipmap.basic_info_unchecked_icon);
            mTvBasicInfoPager.setTextColor(Color.BLACK);
        }

        if (!mFileExportFragment.isHidden()) {
            mIvFileExportPager.setImageResource(R.mipmap.file_export_unchecked_icon);
            mTvFileExportPager.setTextColor(Color.BLACK);
        }
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_home_pager:
                    initData();
                    mSrlRefreshLayout.setEnableRefresh(true);
                    clickTab(mHomeFragment);
                    break;
                case R.id.ll_basic_info_pager:
                    if (mUSB) {
                        ToastUtil.showToast(mMainActivity,R.string.terminal_communication_services_disconnect);
//                    mMainActivity.showToast(R.string.please_open_usb_network_share);
                        return;
                    }
                    mSrlRefreshLayout.setEnableRefresh(false);
                    clickTab(mBasicInfoFragment);
                    break;
                case R.id.ll_file_export_pager:
                    if (mUSB) {
                        ToastUtil.showToast(mMainActivity,R.string.terminal_communication_services_disconnect);
//                    mMainActivity.showToast(R.string.please_open_usb_network_share);
                        return;
                    }
                    mSrlRefreshLayout.setEnableRefresh(false);
                    clickTab(mFileExportFragment);
                    break;
                case R.id.iv_close_menu:
                case R.id.ll_close_menu:
                    mTlhHeaderView.finishTwoLevel();
                    break;
            }
    }


}
