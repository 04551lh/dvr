package com.adasplus.dvr_controller.mvp.contract;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
/**
 * Author:刘净辉
 * Date : 2019/9/10
 * Description :
 */
public interface IMainContract {
    interface Model {
    }

    interface View {

        LinearLayout getLlHomePager();

        LinearLayout getLlBasicInfoPager();

        LinearLayout getLlFileExportPager();

        ImageView getIvHomePager();

        TextView  getTvHomePager();

        ImageView getIvBasicInfoPager();

        TextView getTvBasicInfoPager();

        ImageView getIvFileExportPager();

        TextView getTvFileExportPager();
    }

    interface Presenter {
        void initData();

        void initListener();
    }
}
