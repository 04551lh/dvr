package com.adasplus.settings.mvp.contract;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Author:刘净辉
 * Date : 2019/9/26 16:02
 * Description :
 */
public interface IDormancySetContract {
    interface Model {
    }

    interface View {

        ImageView getIvBack();

        TextView getTvSave();

        Button getBtnSub();

        EditText getEtErrorNumber();

        Button getBtnAdd();
    }

    interface Presenter {

        void initData();

        void initListener();
    }
}
