package com.adasplus.homepager.set.activity;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.adasplus.base.base.BaseActivity;
import com.adasplus.homepager.R;
import com.adasplus.homepager.set.mvp.contract.IDMSWarningContract;
import com.adasplus.homepager.set.mvp.presenter.DMSWarningPresenter;

public class DMSWarningActivity extends BaseActivity implements IDMSWarningContract.View {


    private ImageView mIvBack;
    private SwipeRefreshLayout mSwipeRefreshLayoutDMSSet;
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
        mSwipeRefreshLayoutDMSSet = findViewById(R.id.swipeRefreshLayout_DMS_set);
        mRvDmsList = findViewById(R.id.rv_dms_list);
    }

    @Override
    public ImageView getIvBack() {
        return mIvBack;
    }

    @Override
    public SwipeRefreshLayout getSwipeRefreshLayoutDMSSet() {
        return mSwipeRefreshLayoutDMSSet;
    }

    @Override
    public RecyclerView getRvDmsList() {
        return mRvDmsList;
    }
}
