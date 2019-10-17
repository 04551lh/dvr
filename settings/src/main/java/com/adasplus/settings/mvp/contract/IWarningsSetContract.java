package com.adasplus.settings.mvp.contract;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Author:刘净辉
 * Date : 2019/9/26 17:14
 * Description :
 */
public interface IWarningsSetContract {
    interface Model {
    }

    interface View {
        ImageView getIvBack();

        TextView getTvAdas();

        TextView getTvDms();

    }

    interface Presenter {

        void initData();

        void initListener();
    }
}
