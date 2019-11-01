package com.adasplus.homepager.set.mvp.contract;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public interface IVideoShowContract {
    interface Model {
    }

    interface View {

        ImageView getImVideoShowBack();

        EditText getEtVideoShowPlatform();

        ImageView getImFullScreen();

        TextView getTvVideoShowSave();
    }

    interface Presenter {

        void initData();

        void initListener();

        void getNetWork();
    }
}
