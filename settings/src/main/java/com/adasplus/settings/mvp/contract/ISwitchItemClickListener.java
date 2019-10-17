package com.adasplus.settings.mvp.contract;

import com.adasplus.settings.mvp.model.ConvertWarningsModel;

/**
 * Author:刘净辉
 * Date : 2019/9/30 14:29
 * Description :
 */
public interface ISwitchItemClickListener {
    void onSwitchListener(int position,ConvertWarningsModel convertWarningsModel,boolean status);
}
