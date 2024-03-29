package com.adasplus.base.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.adasplus.base.R;
import com.adasplus.base.dialog.BasicDialog;
import com.adasplus.base.dialog.CommonDialog;
import com.alibaba.android.arouter.launcher.ARouter;


/**
 * Author:刘净辉
 * Date : 2019/9/10
 */
public abstract class BaseActivity extends AppCompatActivity {


    private View mLoadView;
    private TextView mTvSetLoadContent;
    private BasicDialog mNetRequestDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ARouter.getInstance().inject(this);
        initWidget();
        initDialog();
        init(savedInstanceState);
    }

    protected abstract void init(Bundle savedInstanceState);

    protected abstract int getLayoutId();

    protected abstract void initWidget();

    private void initDialog() {
        mLoadView = View.inflate(this, R.layout.dialog_load_progress, null);
        mTvSetLoadContent = mLoadView.findViewById(R.id.tv_text_content);
    }

    public void setText(String content) {
        if (mTvSetLoadContent != null) {
            mTvSetLoadContent.setText(content);
        }
    }

    public void setText(int resId) {
        if (mTvSetLoadContent != null) {
            mTvSetLoadContent.setText(resId);
        }
    }

    public void showNetRequestDialog() {
        int wh = (int) getResources().getDimension(R.dimen.dp_65);
        if(mNetRequestDialog == null){
            mNetRequestDialog = CommonDialog.init()
                    .setView(mLoadView)
                    .setWidth(wh)
                    .setHeight(wh)
                    .setOutCancel(false)
                    .show(getSupportFragmentManager());
        }
        else{
            mNetRequestDialog.show(getSupportFragmentManager());
        }

    }

    public void dismissNetRequestDialog() {
        if (mNetRequestDialog != null && mNetRequestDialog.isAdded()) {
            mNetRequestDialog.dismiss();
        }
    }

    public void showToast(int msg) {
        Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    public void showToast(String string) {
        Toast.makeText(BaseActivity.this, string, Toast.LENGTH_SHORT).show();
    }
}

