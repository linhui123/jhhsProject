package com.jhhscm.platform.fragment.home;

import com.jhhscm.platform.bean.UserData;
import com.jhhscm.platform.fragment.home.bean.FindBrandHomePageBean;
import com.jhhscm.platform.fragment.home.bean.FindCategoryHomePageBean;
import com.jhhscm.platform.fragment.home.bean.FindLabourReleaseHomePageBean;

public class HomePageItem {
    public static final int TYPE_HOME_PAGE_BANNER = 1; // 轮播图
    public static final int TYPE_HOME_PAGE_BUSINESS = 2;//业务模块
    public static final int TYPE_HOME_PAGE_AD = 3;//广告
    public static final int TYPE_HOME_PAGE_SEND_FRIENDS = 4;//联系信息
    public static final int TYPE_HOME_PAGE_BRAND = 5;//品牌
    public static final int TYPE_HOME_PAGE_RECOMMEND = 6;//推荐
    public static final int TYPE_HOME_PAGE_MSG = 7;//劳务资讯
    public static final int TYPE_HOME_PAGE_PROJECT = 8;//业务项目
    public static final int TYPE_HOME_PAGE_AC = 9;//广告
    public int itemType;

    public UserData userData;
    public static AdBean adBean1; //头部轮播图
    public static AdBean adBean2; //广告1
    public static AdBean adBean3; //广告2 活动
    public static FindBrandHomePageBean findBrandHomePageBean;
    public static FindCategoryHomePageBean findCategoryHomePageBean;
    public static FindLabourReleaseHomePageBean findLabourReleaseHomePageBean;


    public HomePageItem(int itemType) {
        this.itemType = itemType;
    }
}
