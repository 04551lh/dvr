package com.adasplus.homepager.set.activity;

import android.os.Bundle;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.adasplus.base.base.BaseActivity;
import com.adasplus.homepager.R;
import com.adasplus.homepager.set.mvp.contract.IADASWarningContract;
import com.adasplus.homepager.set.mvp.presenter.ADASWarningPresenter;

public class ADASWarningActivity extends BaseActivity implements IADASWarningContract.View {

    private ImageView mIvBack;
    private SwipeRefreshLayout mSwipeContainer;
    private RecyclerView mRvAdasList;

    @Override
    protected void init(Bundle savedInstanceState) {
        ADASWarningPresenter adasWarningPresenter = new ADASWarningPresenter(this);
        adasWarningPresenter.initData();
        adasWarningPresenter.initListener();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_adaswarning;
    }

    @Override
    protected void initWidget() {
        mIvBack =  findViewById(R.id.iv_back);
        mSwipeContainer = findViewById(R.id.swipe_container);
        mRvAdasList =  findViewById(R.id.rv_adas_list);
    }

    @Override
    public ImageView getIvBack() {
        return mIvBack;
    }

    @Override
    public SwipeRefreshLayout getSwipeContainer() {
        return mSwipeContainer;
    }

    @Override
    public RecyclerView getRvAdasList() {
        return mRvAdasList;
    }

}
