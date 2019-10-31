package com.adasplus.homepager.set.mvp.presenter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.adasplus.homepager.R;
import com.adasplus.homepager.set.activity.ADASWarningActivity;
import com.adasplus.homepager.set.activity.DMSWarningActivity;
import com.adasplus.homepager.set.activity.WarningsSetActivity;
import com.adasplus.homepager.set.mvp.contract.IWarningsSetContract;

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
        ImageView ivWarningsBack = mWarningsSetView.getIvWarningsBack();
        LinearLayout llAdas = mWarningsSetView.getLlAdas();
        LinearLayout llDms = mWarningsSetView.getLlDms();

        ivWarningsBack.setOnClickListener(this);
        llAdas.setOnClickListener(this);
        llDms.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_warnings_back) {
            mWarningsSetActivity.finish();
        } else if (id == R.id.ll_adas) {
            mWarningsSetActivity.startActivity(new Intent(mWarningsSetActivity, ADASWarningActivity.class));
        } else if (id == R.id.ll_dms) {
            mWarningsSetActivity.startActivity(new Intent(mWarningsSetActivity, DMSWarningActivity.class));
        }
    }
}
