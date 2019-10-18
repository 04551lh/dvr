package com.adasplus.homepager.activate.mvp.contract;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Author:刘净辉
 * Date : 2019/10/11 16:26
 * Description :
 */
public interface IAddNewPlatformsContract {
    interface Model {
    }

    interface View {
        ImageView getIvBack();

        EditText getEtPlatformIpAddress();

        EditText getEtPlatformPort();

        TextView getTvSave();
    }

    interface Presenter {

        void initData();

        void initListener();
    }
}
