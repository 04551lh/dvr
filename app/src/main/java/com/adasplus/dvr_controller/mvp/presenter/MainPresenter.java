package com.adasplus.dvr_controller.mvp.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adasplus.dvr_controller.R;
import com.adasplus.dvr_controller.activity.MainActivity;
import com.adasplus.dvr_controller.adapter.NavigationMenuAdapter;
import com.adasplus.dvr_controller.mvp.contract.IMainContract;

import java.lang.ref.WeakReference;

/**
 * Author:刘净辉
 * Date : 2019/9/10
 * Description :
 */
public class MainPresenter implements IMainContract.Presenter{

    private static final int COLUMNS_COUNT = 4;

    private int[] mNavigationBarText = new int[]{R.string.connect_device, R.string.connect_platform,R.string.params_write,R.string.settings,R.string.basic_info, R.string.files_export};

    private int[] mNavigationBarIcon = new int[]{R.mipmap.connect_device, R.mipmap.activate_device,R.mipmap.activate_device,R.mipmap.activate_device,R.mipmap.activate_device, R.mipmap.files_export};

    private IMainContract.View mMainView;
    private Context mContext;
    private RecyclerView mRvNavigationBar;
    private NavigationMenuAdapter mNavigationMenuAdapter;
    private ImageView mIvHighBeam;
    private ImageView mIvDippedHeadlight;
    private ImageView mIvBrake;
    private ImageView mIvNetworkDevice;
    private ImageView mIvPhoneSignal;
    private ImageView mIvActivate;
    private ImageView mIvGpsStatus;
    private MainActivity mMainActivity;
    private  DeviceStateHandler mDeviceStateHandler;

    private static class DeviceStateHandler extends Handler{

        private WeakReference<MainActivity> mMainWeakReference;
        private DeviceStateHandler(MainActivity mainActivity){
            mMainWeakReference = new WeakReference<>(mainActivity);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }
    }

    public MainPresenter(IMainContract.View view) {
        mMainView = view;
        mContext = (Context) mMainView;
        mMainActivity = (MainActivity) mMainView;
        mDeviceStateHandler = new DeviceStateHandler(mMainActivity);

        initWidget();
    }

    private void initWidget() {
        mRvNavigationBar = mMainView.getRvNavigationBar();
        mIvHighBeam = mMainView.getIvHighBeam();
        mIvDippedHeadlight = mMainView.getIvDippedHeadlight();
        mIvBrake = mMainView.getIvBrake();
        mIvNetworkDevice = mMainView.getIvNetworkDevice();
        mIvPhoneSignal = mMainView.getIvPhoneSignal();
        mIvActivate = mMainView.getIvActivate();
        mIvGpsStatus = mMainView.getIvGpsStatus();

        mRvNavigationBar.setLayoutManager(new GridLayoutManager(mContext, COLUMNS_COUNT));
        mNavigationMenuAdapter = new NavigationMenuAdapter();
    }

    @Override
    public void initData() {
        mNavigationMenuAdapter.setData(mNavigationBarText, mNavigationBarIcon);
        mRvNavigationBar.setAdapter(mNavigationMenuAdapter);
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onStop() {
    }

}
