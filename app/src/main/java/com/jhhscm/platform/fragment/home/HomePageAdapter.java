package com.jhhscm.platform.fragment.home;

import android.content.Context;
import android.view.ViewGroup;

import com.jhhscm.platform.R;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.bean.UserData;
import com.jhhscm.platform.fragment.home.bean.FindBrandHomePageBean;
import com.jhhscm.platform.fragment.home.bean.FindCategoryHomePageBean;
import com.jhhscm.platform.fragment.home.bean.FindLabourReleaseHomePageBean;
import com.jhhscm.platform.fragment.home.holder.HomePageACViewHolder;
import com.jhhscm.platform.fragment.home.holder.HomePageADViewHolder;
import com.jhhscm.platform.fragment.home.holder.HomePageBannerViewHolder;
import com.jhhscm.platform.fragment.home.holder.HomePageBrandViewHolder;
import com.jhhscm.platform.fragment.home.holder.HomePageBusinessViewHolder;
import com.jhhscm.platform.fragment.home.holder.HomePageMsgViewHolder;
import com.jhhscm.platform.fragment.home.holder.HomePageRecommendViewHolder;
import com.jhhscm.platform.fragment.home.holder.HomePageSendFriendsViewHolder;

public class HomePageAdapter extends AbsRecyclerViewAdapter<HomePageItem> {

    public HomePageAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        return get(position).itemType;
    }

    /**
     * 轮播
     */
    private void addHomePageBanner(AdBean adBean) {
        HomePageItem item = new HomePageItem(HomePageItem.TYPE_HOME_PAGE_BANNER);
        item.adBean1 = adBean;
        mData.add(item);
    }

    /**
     * 业务模块
     */
    private void addHomePageBusiness(AdBean adBean) {
        HomePageItem item = new HomePageItem(HomePageItem.TYPE_HOME_PAGE_BUSINESS);
        item.adBean3 = adBean;
        mData.add(item);
    }

    /**
     * 广告
     */
    private void addHomePageAD(AdBean adBean) {
        HomePageItem item = new HomePageItem(HomePageItem.TYPE_HOME_PAGE_AD);
        item.adBean2 = adBean;
        mData.add(item);
    }

    private void addHomePageAc() {
        HomePageItem item = new HomePageItem(HomePageItem.TYPE_HOME_PAGE_AC);
        mData.add(item);
    }

    /**
     * 电话信息
     */
    private void addHomePageSendFriends() {
        HomePageItem item = new HomePageItem(HomePageItem.TYPE_HOME_PAGE_SEND_FRIENDS);
        mData.add(item);
    }

    /**
     * 热门品牌
     */
    private void addHomePageBank(FindBrandHomePageBean findBrandHomePageBean) {
        HomePageItem item = new HomePageItem(HomePageItem.TYPE_HOME_PAGE_BRAND);
        item.findBrandHomePageBean = findBrandHomePageBean;
        mData.add(item);
    }

    /**
     * 推荐配件
     */
    private void addHomePageRecommend(FindCategoryHomePageBean findCategoryHomePageBean) {
        HomePageItem item = new HomePageItem(HomePageItem.TYPE_HOME_PAGE_RECOMMEND);
        item.findCategoryHomePageBean = findCategoryHomePageBean;
        mData.add(item);
    }

    /**
     * 劳务资讯
     */
    private void addHomePageMsg(FindLabourReleaseHomePageBean findLabourReleaseHomePageBean) {
        HomePageItem item = new HomePageItem(HomePageItem.TYPE_HOME_PAGE_MSG);
        item.findLabourReleaseHomePageBean = findLabourReleaseHomePageBean;
        mData.add(item);
    }

    public void setDetail(HomePageItem homePageItem) {
        mData.clear();
        addHomePageBanner(homePageItem.adBean1);
        addHomePageBusiness(homePageItem.adBean3);
        addHomePageSendFriends();
        addHomePageAD(homePageItem.adBean2);
        addHomePageBank(homePageItem.findBrandHomePageBean);
        addHomePageRecommend(homePageItem.findCategoryHomePageBean);
        addHomePageAc();
        addHomePageMsg(homePageItem.findLabourReleaseHomePageBean);
        notifyDataSetChanged();
    }

    @Override
    public AbsRecyclerViewHolder<HomePageItem> onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case HomePageItem.TYPE_HOME_PAGE_BANNER:
                return new HomePageBannerViewHolder(mInflater.inflate(R.layout.item_home_page_banner, parent, false));
            case HomePageItem.TYPE_HOME_PAGE_BUSINESS:
                return new HomePageBusinessViewHolder(mInflater.inflate(R.layout.item_home_page_business, parent, false));
            case HomePageItem.TYPE_HOME_PAGE_AD:
                return new HomePageADViewHolder(mInflater.inflate(R.layout.item_home_page_ad, parent, false));
            case HomePageItem.TYPE_HOME_PAGE_AC:
                return new HomePageACViewHolder(mInflater.inflate(R.layout.item_home_page_ac, parent, false));
            case HomePageItem.TYPE_HOME_PAGE_SEND_FRIENDS:
                return new HomePageSendFriendsViewHolder(mInflater.inflate(R.layout.item_home_page_phone, parent, false));
            case HomePageItem.TYPE_HOME_PAGE_BRAND:
                return new HomePageBrandViewHolder(mInflater.inflate(R.layout.item_home_page_brand, parent, false));
            case HomePageItem.TYPE_HOME_PAGE_RECOMMEND:
                return new HomePageRecommendViewHolder(mInflater.inflate(R.layout.item_home_page_recommend, parent, false));
            case HomePageItem.TYPE_HOME_PAGE_MSG:
                return new HomePageMsgViewHolder(mInflater.inflate(R.layout.item_home_page_msg, parent, false));
        }
        return null;
    }
}

