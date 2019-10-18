package com.adasplus.homepager.set.mvp.contract;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Author:刘净辉
 * Date : 2019/9/26 10:23
 * Description :
 */
public interface ICANSetContract {
    interface Model {
    }

    interface View {

        ImageView getIvBack();

        TextView getTvSelectChannelNumber();

        EditText getEtRateValue();

        TextView getTvSave();
    }

    interface Presenter {

        void initData();

        void initListener();
    }
}
