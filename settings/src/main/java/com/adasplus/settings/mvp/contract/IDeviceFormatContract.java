package com.adasplus.settings.mvp.contract;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Author:刘净辉
 * Date : 2019/10/12 14:33
 * Description :
 */
public interface IDeviceFormatContract {
    interface Model {
    }

    interface View {

        ImageView getIvBack();

        RecyclerView getRvDeviceFormatList();

        TextView getTvDeviceFormatData();
    }

    interface Presenter {

        void initData();

        void initListener();
    }
}
