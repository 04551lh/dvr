package com.adasplus.homepager.set.mvp.contract;


import com.adasplus.homepager.set.mvp.model.CANChannelsModel;

/**
 * Author:刘净辉
 * Date : 2019/9/26 14:18
 * Description :
 */
public interface IChannelItemListener {

    void onItemListener(int position, CANChannelsModel.ArrayBean canChannelsModel);
}
