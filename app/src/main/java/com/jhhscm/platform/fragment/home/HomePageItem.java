package com.jhhscm.platform.fragment.home;

import com.jhhscm.platform.fragment.home.bean.FindBrandHomePageBean;
import com.jhhscm.platform.fragment.home.bean.FindCategoryHomePageBean;
import com.jhhscm.platform.fragment.home.bean.FindLabourReleaseHomePageBean;
import com.jhhscm.platform.fragment.home.bean.GetPageArticleListBean;

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
    public static final int TYPE_HOME_PAGE_NEWS = 10;//咨询信息
    public static final int TYPE_HOME_PAGE_AD_2 = 11;//会员权益广告
    public int itemType;

    public static AdBean adBean1; //头部轮播图
    public static AdBean adBean2; //广告1
    public static AdBean adBean3; //按钮
    public static AdBean adBean4; //活动
    public static AdBean adBean5; //会员权广告 8
    public static FindBrandHomePageBean findBrandHomePageBean;
    public static FindCategoryHomePageBean findCategoryHomePageBean;
    public static FindLabourReleaseHomePageBean findLabourReleaseHomePageBean;
    public static GetPageArticleListBean getPageArticleListBean;

    public HomePageItem() {

    }

    public HomePageItem(int itemType) {
        this.itemType = itemType;
    }

    public static AdBean getAdBean1() {
        return adBean1;
    }

    public static void setAdBean1(AdBean adBean1) {
        HomePageItem.adBean1 = adBean1;
    }

    public static AdBean getAdBean2() {
        return adBean2;
    }

    public static void setAdBean2(AdBean adBean2) {
        HomePageItem.adBean2 = adBean2;
    }

    public static AdBean getAdBean3() {
        return adBean3;
    }

    public static void setAdBean3(AdBean adBean3) {
        HomePageItem.adBean3 = adBean3;
    }

    public static AdBean getAdBean4() {
        return adBean4;
    }

    public static void setAdBean4(AdBean adBean4) {
        HomePageItem.adBean4 = adBean4;
    }

    public static AdBean getAdBean5() {
        return adBean5;
    }

    public static void setAdBean5(AdBean adBean5) {
        HomePageItem.adBean5 = adBean5;
    }

    public static FindBrandHomePageBean getFindBrandHomePageBean() {
        return findBrandHomePageBean;
    }

    public static void setFindBrandHomePageBean(FindBrandHomePageBean findBrandHomePageBean) {
        HomePageItem.findBrandHomePageBean = findBrandHomePageBean;
    }

    public static FindCategoryHomePageBean getFindCategoryHomePageBean() {
        return findCategoryHomePageBean;
    }

    public static void setFindCategoryHomePageBean(FindCategoryHomePageBean findCategoryHomePageBean) {
        HomePageItem.findCategoryHomePageBean = findCategoryHomePageBean;
    }

    public static FindLabourReleaseHomePageBean getFindLabourReleaseHomePageBean() {
        return findLabourReleaseHomePageBean;
    }

    public static void setFindLabourReleaseHomePageBean(FindLabourReleaseHomePageBean findLabourReleaseHomePageBean) {
        HomePageItem.findLabourReleaseHomePageBean = findLabourReleaseHomePageBean;
    }

    public static GetPageArticleListBean getGetPageArticleListBean() {
        return getPageArticleListBean;
    }

    public static void setGetPageArticleListBean(GetPageArticleListBean getPageArticleListBean) {
        HomePageItem.getPageArticleListBean = getPageArticleListBean;
    }
}
