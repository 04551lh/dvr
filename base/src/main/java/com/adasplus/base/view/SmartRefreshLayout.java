package com.adasplus.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

/**
 * Author:刘净辉
 * Date : 2019/10/24 15:18
 * Description :
 */
public class SmartRefreshLayout extends ViewGroup {


    public SmartRefreshLayout(Context context) {
        this(context,null);
    }

    public SmartRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SmartRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        ViewConfiguration configuration = ViewConfiguration.get(context);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
