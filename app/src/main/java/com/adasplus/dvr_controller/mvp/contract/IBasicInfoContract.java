package com.adasplus.dvr_controller.mvp.contract;

import android.view.View;

/**
 * Author:刘净辉
 * Date : 2019/10/18 16:14
 * Description :
 */
public interface IBasicInfoContract {
    interface Model {
    }

    interface View {
    }

    interface Presenter {
        void findViewById(android.view.View view);

        void setClickEvent(android.view.View view);
    }
}
