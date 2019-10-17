package com.adasplus.settings.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.adasplus.base.base.BaseActivity;
import com.adasplus.settings.R;
import com.adasplus.settings.mvp.contract.IDMSWarningContract;
import com.adasplus.settings.mvp.presenter.DMSWarningPresenter;

public class DMSWarningActivity extends BaseActivity implements IDMSWarningContract.View {


    private ImageView mIvBack;
    private RecyclerView mRvDmsList;

    @Override
    protected void init(Bundle savedInstanceState) {
        DMSWarningPresenter dmsWarningPresenter = new DMSWarningPresenter(this);
        dmsWarningPresenter.initData();
        dmsWarningPresenter.initListener();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dmswarning;
    }

    @Override
    protected void initWidget() {
        mIvBack = findViewById(R.id.iv_back);
        mRvDmsList = findViewById(R.id.rv_dms_list);
    }

    @Override
    public ImageView getIvBack() {
        return mIvBack;
    }

    @Override
    public RecyclerView getRvDmsList() {
        return mRvDmsList;
    }
}
