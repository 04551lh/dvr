package com.adasplus.dvr_controller.mvp.presenter;


import android.graphics.Color;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.adasplus.dvr_controller.R;
import com.adasplus.dvr_controller.activity.MainActivity;
import com.adasplus.dvr_controller.fragment.BasicInfoFragment;
import com.adasplus.dvr_controller.fragment.FileExportFragment;
import com.adasplus.dvr_controller.fragment.HomeFragment;
import com.adasplus.dvr_controller.mvp.contract.IMainContract;

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

        initFragment();
    }

    @Override
    public void initListener() {
        mLlHomePager.setOnClickListener(this);
        mLlBasicInfoPager.setOnClickListener(this);
        mLlFileExportPager.setOnClickListener(this);
    }

    private void initFragment() {
        FragmentTransaction fragmentTransaction = mMainActivity.getSupportFragmentManager().beginTransaction();
        if (!mHomeFragment.isAdded()){
            fragmentTransaction.add(R.id.fl_frame_layout,mHomeFragment);
            fragmentTransaction.hide(mHomeFragment);
        }

        if (!mBasicInfoFragment.isAdded()){
            fragmentTransaction.add(R.id.fl_frame_layout,mBasicInfoFragment);
            fragmentTransaction.hide(mBasicInfoFragment);
        }

        if (!mFileExportFragment.isAdded()){
            fragmentTransaction.add(R.id.fl_frame_layout,mFileExportFragment);
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


    private void clickTab(Fragment fragment){
        clearSelected();

        FragmentTransaction fragmentTransaction = mMainActivity.getSupportFragmentManager().beginTransaction();
        hideAllFragment(fragmentTransaction);

        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();
        changeTabStyle(fragment);
    }


    //TODO 这是选中的逻辑处理

    private void changeTabStyle(Fragment fragment) {
        if (fragment instanceof HomeFragment){
            mIvHomePager.setImageResource(R.mipmap.home_checked_icon);
            mTvHomePager.setTextColor(Color.parseColor("#5677FC"));
        }

        if (fragment instanceof  BasicInfoFragment){
            //TODO 图标需要更换成选中的图标
            mIvBasicInfoPager.setImageResource(R.mipmap.basic_info_unchecked_icon);
            mTvBasicInfoPager.setTextColor(Color.parseColor("#5677FC"));
        }

        if (fragment instanceof  FileExportFragment){
            //TODO 图标需要更换成选中的图标
            mIvFileExportPager.setImageResource(R.mipmap.file_export_unchecked_icon);
            mTvFileExportPager.setTextColor(Color.parseColor("#5677FC"));
        }
    }

    //TODO 这是未选中的逻辑处理

    private void clearSelected() {
        if (!mHomeFragment.isHidden()){
            //TODO 图标需要更换成未选中的图标
            mIvHomePager.setImageResource(R.mipmap.home_checked_icon);
            mTvHomePager.setTextColor(Color.BLACK);
        }

        if (!mBasicInfoFragment.isHidden()){
            mIvBasicInfoPager.setImageResource(R.mipmap.basic_info_unchecked_icon);
            mTvBasicInfoPager.setTextColor(Color.BLACK);
        }

        if (!mFileExportFragment.isHidden()){
            mIvFileExportPager.setImageResource(R.mipmap.file_export_unchecked_icon);
            mTvFileExportPager.setTextColor(Color.BLACK);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_home_pager:
                clickTab(mHomeFragment);
                break;
            case R.id.ll_basic_info_pager:
                clickTab(mBasicInfoFragment);
                break;
            case R.id.ll_file_export_pager:
                clickTab(mFileExportFragment);
                break;
        }
    }
}
