package com.adasplus.dvr_controller.fragment;


import android.os.Bundle;
import android.view.View;

import com.adasplus.base.base.BaseFragment;
import com.adasplus.dvr_controller.R;
import com.adasplus.dvr_controller.mvp.contract.IFileExportContract;
import com.adasplus.dvr_controller.mvp.presenter.FileExportPresenter;

public class FileExportFragment extends BaseFragment implements IFileExportContract.View {

    private FileExportPresenter mFileExportPresenter;

    @Override
    protected void onFirstUserVisible() {
        if (mFileExportPresenter != null){
            mFileExportPresenter.initData();
        }
    }

    @Override
    protected void init(View view, Bundle savedInstanceState) {
        mFileExportPresenter = new FileExportPresenter(getAppCompatActivity(), this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_file_export;
    }

    @Override
    public void findViewById(View view) {
        super.findViewById(view);
        if (mFileExportPresenter != null){
            mFileExportPresenter.findViewById(view);
        }
    }

    @Override
    public void setClickEvent(View view) {
        super.setClickEvent(view);
        if (mFileExportPresenter != null){
            mFileExportPresenter.setClickEvent(view);
        }
    }
}
