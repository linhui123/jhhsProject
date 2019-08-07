package com.jhhscm.platform.views.selector;


import com.jhhscm.platform.fragment.base.AbsFragment;


/**
 * 类说明：
 * 作者：huangqiuxin on 2016/6/8 14:11
 * 邮箱：648859026@qq.com
 */
public interface IAView {
    AbsFragment getFragment();//当前Fragment信息
    void onHeadActionClick();//点击右上角按钮
}
