package com.adasplus.dvr_controller.mvp.presenter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adasplus.base.network.ActivityPathConstant;
import com.adasplus.dvr_controller.R;
import com.adasplus.dvr_controller.activity.FileExportActivity;
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
    private LinearLayout mLlCarInfo;
    private LinearLayout mLlDriverInfo;
    private LinearLayout mLlFileExport;
    private LinearLayout mLlSystemInfo;

    public BasicInfoPresenter(Activity activity,IBasicInfoContract.View view){
        mActivity = activity;
        mBasicInfoView = view;
    }

    @Override
    public void findViewById(View view) {
        mLlCarInfo = view.findViewById(R.id.ll_car_info);
        mLlDriverInfo = view.findViewById(R.id.ll_driver_info);
        mLlFileExport = view.findViewById(R.id.ll_file_export);
        mLlSystemInfo = view.findViewById(R.id.ll_system_info);
    }

    @Override
    public void setClickEvent(View view) {
        mLlCarInfo.setOnClickListener(this);
        mLlDriverInfo.setOnClickListener(this);
        mLlSystemInfo.setOnClickListener(this);
        mLlFileExport.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_car_info:
                startActivity(ActivityPathConstant.CAR_INFO_PATH);
                break;
            case R.id.ll_driver_info:
                startActivity(ActivityPathConstant.DRIVER_INFO_PATH);
                break;
            case R.id.ll_file_export:
                mActivity.startActivity(new Intent(mActivity, FileExportActivity.class));
                break;
            case R.id.ll_system_info:
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
