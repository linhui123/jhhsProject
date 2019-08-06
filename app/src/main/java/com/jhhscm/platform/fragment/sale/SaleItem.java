package com.jhhscm.platform.fragment.sale;

import com.jhhscm.platform.fragment.my.order.FindOrderListBean;

public class SaleItem {
    public static final int TYPE_MECHANICS_TOP = 1; // 卖机头部
    public static final int TYPE_MECHANICS_OLD = 2;//二手机历史列表

    public static final int TYPE_SALE_1 = 3; // 待付款
    public static final int TYPE_SALE_2 = 4;//代发货
    public static final int TYPE_SALE_3 = 5; // 待收货
    public static final int TYPE_SALE_4 = 6;//已完成
    public static final int TYPE_SALE_5 = 7; // 全部


    public int itemType;

    public static OldGoodOrderHistoryBean oldGoodOrderHistoryBean;
    public static OldGoodOrderHistoryBean.DataBean dataBean;

    public static FindOrderListBean findOrderListBean;
    public static FindOrderListBean.DataBean orderBean;
    public SaleItem(int itemType) {
        this.itemType = itemType;
    }
}
