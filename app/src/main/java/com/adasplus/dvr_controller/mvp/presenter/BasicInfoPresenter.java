package com.adasplus.dvr_controller.mvp.presenter;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.adasplus.base.network.ActivityPathConstant;
import com.adasplus.dvr_controller.R;
import com.adasplus.dvr_controller.mvp.contract.IBasicInfoContract;
import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Author:刘净辉
 * Date : 2019/10/18 16:14
 * Description :
 */
public class BasicInfoPresenter implements IBasicInfoContract.Presenter, View.OnClickListener {

    private IBasicInfoContract.View mBasicInfoView;
    private Activity mActivity;
    private TextView mTvCarInfo;
    private TextView mTvDriverInfo;
    private TextView mTvSystemInfo;
    
    public BasicInfoPresenter(Activity activity,IBasicInfoContract.View view){
        mActivity = activity;
        mBasicInfoView = view;
    }

    @Override
    public void findViewById(View view) {
        mTvCarInfo = (TextView) view.findViewById(R.id.tv_car_info);
        mTvDriverInfo = (TextView) view.findViewById(R.id.tv_driver_info);
        mTvSystemInfo = (TextView) view.findViewById(R.id.tv_system_info);
    }

    @Override
    public void setClickEvent(View view) {
        mTvCarInfo.setOnClickListener(this);
        mTvDriverInfo.setOnClickListener(this);
        mTvSystemInfo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_car_info:
                startActivity(ActivityPathConstant.CAR_INFO_PATH);
                break;
            case R.id.tv_driver_info:
                startActivity(ActivityPathConstant.DRIVER_INFO_PATH);
                break;
            case R.id.tv_system_info:
                startActivity(ActivityPathConstant.SYSTEM_INFO_PATH);
                break;
        }
    }

    private void startActivity(String path){
        ARouter.getInstance()
                .build(path)
                .navigation();
    }
}
