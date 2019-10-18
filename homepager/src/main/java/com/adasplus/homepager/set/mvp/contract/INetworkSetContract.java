package com.adasplus.homepager.set.mvp.contract;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Author:刘净辉
 * Date : 2019/9/25 18:05
 * Description :
 */
public interface INetworkSetContract {
    interface Model {
    }

    interface View {

        ImageView getIvBack();

        EditText getEtWirelessIpAddress();

        EditText getEtWirelessMaskCode();

        EditText getEtWirelessGateway();

        EditText getEtWirelessMacAddress();

        EditText getEtWiredIpAddress();

        EditText getEtWiredMaskCode();

        EditText getEtWiredGateway();

        EditText getEtWiredMacAddress();

        TextView getTvSave();
    }

    interface Presenter {

        void initData();

        void initListener();
    }
}
