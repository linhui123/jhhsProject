package com.jhhscm.platform.views.tabstrip;

/**
 * Created by Administrator on 2018/1/26.
 */

public interface OnTabStripItemListener {
    void onTabRepeatClick(int index);

    void onTabSelectClick(int index, int old);
}
