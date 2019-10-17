package com.adasplus.basicinfo.mvp.presenter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.adasplus.basicinfo.R;
import com.adasplus.basicinfo.activity.BasicInfoActivity;
import com.adasplus.basicinfo.activity.CarInfoActivity;
import com.adasplus.basicinfo.activity.DriverInfoActivity;
import com.adasplus.basicinfo.activity.SystemInfoActivity;
import com.adasplus.basicinfo.mvp.contract.IBasicInfoContract;

/**
 * Author:刘净辉
 * Date : 2019/10/11 17:46
 * Description :
 */
public class BasicInfoPresenter implements IBasicInfoContract.Presenter, View.OnClickListener {

    private IBasicInfoContract.View mBasicInfoView;
    private BasicInfoActivity mBasicInfoActivity;

    public BasicInfoPresenter(IBasicInfoContract.View view){
        mBasicInfoView = view;
        mBasicInfoActivity = (BasicInfoActivity) view;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        ImageView ivBack = mBasicInfoView.getIvBack();
        TextView tvCarInfo = mBasicInfoView.getTvCarInfo();
        TextView tvDriverInfo = mBasicInfoView.getTvDriverInfo();
        TextView tvSystemInfo = mBasicInfoView.getTvSystemInfo();

        ivBack.setOnClickListener(this);
        tvCarInfo.setOnClickListener(this);
        tvDriverInfo.setOnClickListener(this);
        tvSystemInfo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_back){
            mBasicInfoActivity.finish();
        }else if (id == R.id.tv_car_info){
            mBasicInfoActivity.startActivity(new Intent(mBasicInfoActivity, CarInfoActivity.class));
        }else if (id == R.id.tv_driver_info){
            mBasicInfoActivity.startActivity(new Intent(mBasicInfoActivity, DriverInfoActivity.class));
        }else if (id == R.id.tv_system_info){
            mBasicInfoActivity.startActivity(new Intent(mBasicInfoActivity, SystemInfoActivity.class));
        }
    }
}
