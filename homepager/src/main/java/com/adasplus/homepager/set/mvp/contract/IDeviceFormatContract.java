package com.adasplus.homepager.set.mvp.contract;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * Author:刘净辉
 * Date : 2019/10/12 14:33
 * Description :
 */
public interface IDeviceFormatContract {
    interface Model {
    }

    interface View {

        ImageView getIvDeviceFormatBack();

        SwipeRefreshLayout getSwipeRefreshLayoutDeviceFormatSet();

        RecyclerView getRvDeviceFormatList();

        TextView getTvDeviceFormatData();
    }

    interface Presenter {

        void initData();

        void initListener();
    }
}
