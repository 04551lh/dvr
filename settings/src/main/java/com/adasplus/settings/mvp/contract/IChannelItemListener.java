package com.adasplus.settings.mvp.contract;

import com.adasplus.settings.mvp.model.CANChannelsModel;

/**
 * Author:刘净辉
 * Date : 2019/9/26 14:18
 * Description :
 */
public interface IChannelItemListener {

    void onItemListener(int position, CANChannelsModel.ArrayBean canChannelsModel);
}
