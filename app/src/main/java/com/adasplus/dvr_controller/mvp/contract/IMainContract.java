package com.adasplus.dvr_controller.mvp.contract;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adasplus.menudrawer.OverlayDrawer;

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

        OverlayDrawer getOdTopSlideView();
    }

    interface Presenter {
        void initData();

        void initListener();
    }
}
