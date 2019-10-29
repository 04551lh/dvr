package com.adasplus.homepager.set.mvp.contract;

import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * Author:刘净辉
 * Date : 2019/9/26 18:12
 * Description :
 */
public interface IADASWarningContract {
    interface Model {
    }

    interface View {

        ImageView getIvBack();

        SwipeRefreshLayout getSwipeContainer();

        RecyclerView getRvAdasList();

    }

    interface Presenter {

        void initData();

        void initListener();
    }
}
