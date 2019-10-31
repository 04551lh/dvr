package com.adasplus.homepager.set.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adasplus.base.base.BaseActivity;
import com.adasplus.homepager.R;
import com.adasplus.homepager.set.mvp.contract.IDormancySetContract;
import com.adasplus.homepager.set.mvp.presenter.DormancySetPresenter;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class DormancySetActivity extends BaseActivity implements IDormancySetContract.View {

    private ImageView mIvDormancyBack;
    private SwipeRefreshLayout mSwipeRefreshLayoutDormancySet;
    private TextView mTvDormancySave;
    private Button mBtnSub;
    private EditText mEtErrorNumber;
    private Button mBtnAdd;
    private ImageView mIvCloseHintMessage;
    private RelativeLayout mRlHintMessage;

    @Override
    protected void init(Bundle savedInstanceState) {
        DormancySetPresenter dormancySetPresenter = new DormancySetPresenter(this);
        dormancySetPresenter.initData();
        dormancySetPresenter.initListener();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dormancy_set;
    }

    @Override
    protected void initWidget() {
        mBtnSub =  findViewById(R.id.btn_sub);
        mEtErrorNumber =  findViewById(R.id.et_error_number);
        mBtnAdd =  findViewById(R.id.btn_add);
        mIvDormancyBack =  findViewById(R.id.iv_dormancy_back);
        mSwipeRefreshLayoutDormancySet =  findViewById(R.id.swipeRefreshLayout_dormancy_set);
        mTvDormancySave =  findViewById(R.id.tv_dormancy_save);
        mRlHintMessage = findViewById(R.id.rl_hint_message);
        mIvCloseHintMessage = findViewById(R.id.iv_close_hint_message);
    }

    @Override
    public ImageView getIvDormancyBack() {
        return mIvDormancyBack;
    }

    @Override
    public SwipeRefreshLayout getSwipeRefreshLayoutDormancySet() {
        return mSwipeRefreshLayoutDormancySet;
    }

    @Override
    public TextView getTvDormancySave() {
        return mTvDormancySave;
    }

    @Override
    public Button getBtnSub() {
        return mBtnSub;
    }

    @Override
    public EditText getEtErrorNumber() {
        return mEtErrorNumber;
    }

    @Override
    public Button getBtnAdd() {
        return mBtnAdd;
    }

    @Override
    public ImageView getIvCloseHintMessage() {
        return mIvCloseHintMessage;
    }

    @Override
    public RelativeLayout getRlHintMessage() {
        return mRlHintMessage;
    }

}
