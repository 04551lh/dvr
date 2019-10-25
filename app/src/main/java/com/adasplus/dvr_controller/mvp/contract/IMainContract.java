package com.adasplus.dvr_controller.mvp.contract;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.header.TwoLevelHeader;

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

        ImageView getIvFourGSignalStatus();

        TextView getTvFourGSignalLevel();

        ImageView getIvLocationStatus();

        TextView getTvLocationStatus();

        ImageView getIvFarLightStatus();

        TextView getTvFarLightStatus();

        ImageView getIvNearLightStatus();

        TextView getTvNearLightStatus();

        ImageView getIvLeftTurnStatus();

        TextView getTvLeftTurnStatus();

        ImageView getIvRightTurnStatus();

        TextView getTvRightTurnStatus();

        ImageView getIvBrakeStatus();

        TextView getTvBrakeStatus();

        ImageView getIvTargetsPlatformStatus();

        TextView getTvTargetsPlatformStatus();

        ImageView getIvCloseMenu();

        SmartRefreshLayout getSrlRefreshLayout();

        TwoLevelHeader getTlhHeaderView();

        LinearLayout getLlCloseMenu();
    }

    interface Presenter {

        void initWidget();

        void initData();

        void initListener();
    }
}
