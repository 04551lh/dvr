package com.adasplus.dvr_controller.mvp.presenter;


import android.app.Activity;
import android.graphics.Color;

import android.net.wifi.WifiInfo;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.adasplus.base.network.HttpConstant;
import com.adasplus.base.utils.DisplayUtils;
import com.adasplus.base.utils.WifiHelper;
import com.adasplus.dvr_controller.R;
import com.adasplus.dvr_controller.activity.MainActivity;
import com.adasplus.dvr_controller.fragment.BasicInfoFragment;
import com.adasplus.dvr_controller.fragment.FileExportFragment;
import com.adasplus.dvr_controller.fragment.HomeFragment;
import com.adasplus.dvr_controller.mvp.contract.IMainContract;
import com.adasplus.menudrawer.OverlayDrawer;
import com.adasplus.menudrawer.Position;

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

    private ImageView mIvHomePager;
    private TextView mTvHomePager;
    private LinearLayout mLlHomePager;
    private LinearLayout mLlBasicInfoPager;
    private LinearLayout mLlFileExportPager;
    private ImageView mIvBasicInfoPager;
    private TextView mTvBasicInfoPager;
    private ImageView mIvFileExportPager;
    private TextView mTvFileExportPager;
    private OverlayDrawer mOdTopSlideView;

    public MainPresenter(IMainContract.View view) {
        mMainView = view;
        mMainActivity = (MainActivity) mMainView;
    }


    @Override
    public void initData() {
        mLlHomePager = mMainView.getLlHomePager();
        mLlBasicInfoPager = mMainView.getLlBasicInfoPager();
        mLlFileExportPager = mMainView.getLlFileExportPager();
        mIvHomePager = mMainView.getIvHomePager();
        mTvHomePager = mMainView.getTvHomePager();
        mIvBasicInfoPager = mMainView.getIvBasicInfoPager();
        mTvBasicInfoPager = mMainView.getTvBasicInfoPager();
        mIvFileExportPager = mMainView.getIvFileExportPager();
        mTvFileExportPager = mMainView.getTvFileExportPager();
        mOdTopSlideView = mMainView.getOdTopSlideView();

        topSlidePager();
        mOdTopSlideView.setPosition(Position.TOP);

        initFragment();
    }

    private void topSlidePager() {
        int stateBarHeight = getStateBarHeight();
        if (stateBarHeight > 0){
            mOdTopSlideView.setMenuSize(DisplayUtils.getScreenHeight(mMainActivity) - stateBarHeight);
        }else {
            mOdTopSlideView.setMenuSize(DisplayUtils.getScreenHeight(mMainActivity) - getDefaultStateBarHeight());
        }
    }

    private int getStateBarHeight(){
        int result = 0;
        int resourceId = mMainActivity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0){
            result =  mMainActivity.getResources().getDimensionPixelSize(resourceId);;
        }
        return result;
    }

    private int getDefaultStateBarHeight(){
        double statusBarHeight = Math.ceil(20 * mMainActivity.getResources().getDisplayMetrics().density);
        return (int) statusBarHeight;
    }

    @Override
    public void initListener() {
        mLlHomePager.setOnClickListener(this);
        mLlBasicInfoPager.setOnClickListener(this);
        mLlFileExportPager.setOnClickListener(this);
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
        WifiInfo wifiInfo = WifiHelper.getInstance().getConnectionWifiInfo();
        String wifiName = wifiInfo.getSSID();
        boolean isDeviceWifi = !wifiName.contains(HttpConstant.DEVICE_WIFI_TAG);

        switch (v.getId()) {
            case R.id.ll_home_pager:
                topSlidePager();
                clickTab(mHomeFragment);
                break;
            case R.id.ll_basic_info_pager:
                if (isDeviceWifi) {
                    Toast.makeText(mMainActivity, R.string.please_connect_device, Toast.LENGTH_SHORT).show();
                    return;
                }
                mOdTopSlideView.setMenuSize(0);
                clickTab(mBasicInfoFragment);
                break;
            case R.id.ll_file_export_pager:
                if (isDeviceWifi) {
                    Toast.makeText(mMainActivity, R.string.please_connect_device, Toast.LENGTH_SHORT).show();
                    return;
                }
                mOdTopSlideView.setMenuSize(0);
                clickTab(mFileExportFragment);
                break;
        }
    }
}
