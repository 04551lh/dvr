package com.adasplus.basicinfo.mvp.contract;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Author:刘净辉
 * Date : 2019/10/11 17:46
 * Description :
 */
public interface IBasicInfoContract {
    interface Model {
    }

    interface View {

        ImageView getIvBack();

        TextView getTvCarInfo();

        TextView getTvDriverInfo();

        TextView getTvSystemInfo();
    }

    interface Presenter {

        void initData();

        void initListener();
    }
}
