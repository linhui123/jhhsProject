package com.jhhscm.platform.fragment.Mechanics;

import com.jhhscm.platform.bean.UserData;
import com.jhhscm.platform.fragment.Mechanics.bean.GetComboBoxBean;
import com.jhhscm.platform.fragment.Mechanics.bean.GetGoodsPageListBean;
import com.jhhscm.platform.fragment.home.AdBean;
import com.jhhscm.platform.fragment.home.bean.FindBrandHomePageBean;
import com.jhhscm.platform.fragment.home.bean.FindCategoryHomePageBean;
import com.jhhscm.platform.fragment.home.bean.FindLabourReleaseHomePageBean;

public class MechanicsItem {
    public static final int TYPE_MECHANICS_NEW = 1; // 新机列表
    public static final int TYPE_MECHANICS_OLD = 2;//二手机列表

    public static final int TYPE_MECHANICS_JX = 3;//机型
    public static final int TYPE_MECHANICS_CS = 4;//产商
    public static final int TYPE_MECHANICS_PP = 5;//品牌
    public static final int TYPE_MECHANICS_PX = 6;//排序

    public static final int TYPE_MECHANICS_DL = 7;//动力
    public static final int TYPE_MECHANICS_CD = 8;//铲斗
    public static final int TYPE_MECHANICS_DW = 9;//吨位

    public static final int TYPE_MECHANICS_SELECTED = 10;//选择条件

    public static int itemType;

    public UserData userData;

    public static GetGoodsPageListBean getGoodsPageListBean;
    public static GetGoodsPageListBean.DataBean resultBean;
    public static GetComboBoxBean getComboBoxBeanJX;
    public static GetComboBoxBean.ResultBean resultBeanJX;
    public static GetComboBoxBean getComboBoxBeanCS;
    public static GetComboBoxBean getComboBoxBeanPP;
    public static GetComboBoxBean getComboBoxBeanPX;
    public static GetComboBoxBean getComboBoxBeanDL;
    public static GetComboBoxBean getComboBoxBeanCD;
    public static GetComboBoxBean getComboBoxBeanDW;

    public MechanicsItem(int itemType) {
        this.itemType = itemType;
    }
}
