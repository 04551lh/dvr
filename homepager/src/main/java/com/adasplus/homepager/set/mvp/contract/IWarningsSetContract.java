package com.adasplus.homepager.set.mvp.contract;

import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Author:刘净辉
 * Date : 2019/9/26 17:14
 * Description :
 */
public interface IWarningsSetContract {
    interface Model {
    }

    interface View {
        ImageView getIvWarningsBack();

        LinearLayout getLlAdas();

        LinearLayout getLlDms();

    }

    interface Presenter {

        void initData();

        void initListener();
    }
}
