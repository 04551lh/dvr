package com.adasplus.dvr_controller.mvp.contract;

/**
 * Author:刘净辉
 * Date : 2019/10/18 16:44
 * Description :
 */
public interface IVideoPreviewContract {
    interface Model {
    }

    interface View {

    }

    interface Presenter {

        void initData();

        void findViewById(android.view.View view);

        void setClickEvent(android.view.View view);
    }
}
