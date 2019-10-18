package com.adasplus.homepager.set.mvp.contract;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Author:刘净辉
 * Date : 2019/9/26 19:31
 * Description :
 */
public interface IDMSWarningContract {
    interface Model {
    }

    interface View {

        ImageView getIvBack();

        RecyclerView getRvDmsList();
    }

    interface Presenter {

        void initData();

        void initListener();
    }
}
