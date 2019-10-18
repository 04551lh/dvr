package com.adasplus.homepager.set.mvp.contract;


import com.adasplus.homepager.set.mvp.model.ConvertWarningsModel;

/**
 * Author:刘净辉
 * Date : 2019/9/30 14:29
 * Description :
 */
public interface ISwitchItemClickListener {
    void onSwitchListener(int position, ConvertWarningsModel convertWarningsModel, boolean status);
}
