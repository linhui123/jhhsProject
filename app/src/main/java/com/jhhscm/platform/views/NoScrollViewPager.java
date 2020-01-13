package com.jhhscm.platform.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.jhhscm.platform.views.tabstrip.APSTSViewPager;

public class NoScrollViewPager extends APSTSViewPager {
    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollViewPager(Context context) {
        super(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item,false);
    }

}
