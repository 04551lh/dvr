package com.adasplus.fileexport.mvp.presenter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adasplus.base.network.BaseWrapper;
import com.adasplus.base.network.model.FileExportModel;
import com.adasplus.base.popup.CommonPopupWindow;
import com.adasplus.base.utils.ExceptionUtils;
import com.adasplus.fileexport.R;
import com.adasplus.fileexport.activity.FileExportActivity;
import com.adasplus.fileexport.mvp.contract.IFileExportContract;

import rx.Subscriber;

/**
 * Author:刘净辉
 * Date : 2019/9/21 18:23
 * Description :
 */
public class FileExportPresenter implements IFileExportContract.Presenter {


    public FileExportPresenter(IFileExportContract.View view) {
        mFileExportView = view;
        mFileExportActivity = (FileExportActivity) view;
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void initData() {

    }



    @Override
    public void initListener() {
        ImageView ivBack = mFileExportView.getIvBack();
        TextView tvExportFile = mFileExportView.getTvExportFile();


    }



}
