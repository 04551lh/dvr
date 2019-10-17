package com.adasplus.settings.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.adasplus.base.base.BaseActivity;
import com.adasplus.settings.R;
import com.adasplus.settings.mvp.contract.IDormancySetContract;
import com.adasplus.settings.mvp.presenter.DormancySetPresenter;

public class DormancySetActivity extends BaseActivity implements IDormancySetContract.View {

    private ImageView mIvBack;
    private TextView mTvSave;
    private Button mBtnSub;
    private EditText mEtErrorNumber;
    private Button mBtnAdd;

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
        mBtnSub = (Button) findViewById(R.id.btn_sub);
        mEtErrorNumber = (EditText) findViewById(R.id.et_error_number);
        mBtnAdd = (Button) findViewById(R.id.btn_add);
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mTvSave = (TextView) findViewById(R.id.tv_save);
    }

    @Override
    public ImageView getIvBack() {
        return mIvBack;
    }

    @Override
    public TextView getTvSave() {
        return mTvSave;
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
}
