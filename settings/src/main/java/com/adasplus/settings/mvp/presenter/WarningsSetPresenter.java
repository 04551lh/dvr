package com.adasplus.settings.mvp.presenter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.adasplus.settings.R;
import com.adasplus.settings.activity.ADASWarningActivity;
import com.adasplus.settings.activity.DMSWarningActivity;
import com.adasplus.settings.activity.WarningsSetActivity;
import com.adasplus.settings.mvp.contract.IWarningsSetContract;

/**
 * Author:刘净辉
 * Date : 2019/9/26 17:14
 * Description :
 */
public class WarningsSetPresenter implements IWarningsSetContract.Presenter, View.OnClickListener {

    private IWarningsSetContract.View mWarningsSetView;
    private WarningsSetActivity mWarningsSetActivity;

    public WarningsSetPresenter(IWarningsSetContract.View view) {
        mWarningsSetView = view;
        mWarningsSetActivity = (WarningsSetActivity) view;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        ImageView ivBack = mWarningsSetView.getIvBack();
        TextView tvAdas = mWarningsSetView.getTvAdas();
        TextView tvDms = mWarningsSetView.getTvDms();

        ivBack.setOnClickListener(this);
        tvAdas.setOnClickListener(this);
        tvDms.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_back) {
            mWarningsSetActivity.finish();
        } else if (id == R.id.tv_adas) {
            mWarningsSetActivity.startActivity(new Intent(mWarningsSetActivity, ADASWarningActivity.class));
        } else if (id == R.id.tv_dms) {
            mWarningsSetActivity.startActivity(new Intent(mWarningsSetActivity, DMSWarningActivity.class));
        }
    }
}
