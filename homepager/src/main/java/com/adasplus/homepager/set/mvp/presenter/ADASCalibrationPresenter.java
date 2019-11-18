package com.adasplus.homepager.set.mvp.presenter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.adasplus.homepager.R;
import com.adasplus.homepager.params.activity.ADASCalibrationActivity;
import com.adasplus.homepager.params.activity.ParamsSetActivity;
import com.adasplus.homepager.set.activity.CalibrationSetActivity;
import com.adasplus.homepager.set.mvp.contract.IADASCalibrationSetContract;

/**
 * Author:刘净辉
 * Date : 2019/9/26 17:14
 * Description :
 */
public class ADASCalibrationPresenter implements IADASCalibrationSetContract.Presenter, View.OnClickListener {

    private IADASCalibrationSetContract.View mWarningsSetView;
    private ADASCalibrationActivity mADASCalibrationActivity;

    public ADASCalibrationPresenter(IADASCalibrationSetContract.View view) {
        mWarningsSetView = view;
        mADASCalibrationActivity = (ADASCalibrationActivity) view;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        ImageView ivADASCalibrationBack = mWarningsSetView.getIvADASCalibrationBack();
        LinearLayout llADASCalibrationParamSet = mWarningsSetView.getLlADASCalibrationParamSet();
        LinearLayout llADASCalibrationSet = mWarningsSetView.getLlADASCalibrationSet();

        ivADASCalibrationBack.setOnClickListener(this);
        llADASCalibrationParamSet.setOnClickListener(this);
        llADASCalibrationSet.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_adas_calibration_back) {
            mADASCalibrationActivity.finish();
        } else if (id == R.id.ll_adas_calibration_param_set) {
            mADASCalibrationActivity.startActivity(new Intent(mADASCalibrationActivity, ParamsSetActivity.class));
        } else if (id == R.id.ll_adas_calibration_set) {
            mADASCalibrationActivity.startActivity(new Intent(mADASCalibrationActivity, CalibrationSetActivity.class));
        }
    }
}
