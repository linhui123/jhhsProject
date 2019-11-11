package com.jhhscm.platform.fragment.my.order;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.GoodsToCartsActivity;
import com.jhhscm.platform.activity.LoginActivity;
import com.jhhscm.platform.databinding.FragmentMyOrderListBinding;
import com.jhhscm.platform.databinding.FragmentSearchBinding;
import com.jhhscm.platform.event.SerachEvent;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.search.SearchFragment;
import com.jhhscm.platform.fragment.search.SearchNewFragment;
import com.jhhscm.platform.fragment.search.SearchOldFragment;
import com.jhhscm.platform.fragment.search.SearchPeiJianFragment;
import com.jhhscm.platform.http.bean.UserSession;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.DisplayUtils;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.Utils;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;

import java.util.ArrayList;
import java.util.List;


public class MyOrderListFragment extends AbsFragment<FragmentMyOrderListBinding> {
    private UserSession userSession;
    private int mScreenWidth = 0; // 屏幕宽度
    private int type;
    private OrderStatusFragment allListFragment;
    private OrderStatusFragment fukuangListFragment;
    private OrderStatusFragment dahuoListFragment;
    private OrderStatusFragment shouhuoListFragment;
    private OrderStatusFragment wanchengListFragment;

    public static MyOrderListFragment instance() {
        MyOrderListFragment view = new MyOrderListFragment();
        return view;
    }

    @Override
    protected FragmentMyOrderListBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentMyOrderListBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        if (ConfigUtils.getCurrentUser(getContext()) != null
                && ConfigUtils.getCurrentUser(getContext()).getMobile() != null) {
            userSession = ConfigUtils.getCurrentUser(getContext());
        } else {
            startNewActivity(LoginActivity.class);
        }

        allListFragment = OrderStatusFragment.instance("");
        fukuangListFragment = OrderStatusFragment.instance("1");
        dahuoListFragment = OrderStatusFragment.instance("2");
        shouhuoListFragment = OrderStatusFragment.instance("3");
        wanchengListFragment = OrderStatusFragment.instance("4");
        initTab();
    }

    /**
     * 搜索结果 头部tab
     */
    private void initTab() {
        mScreenWidth = Utils.getWindowsWidth(getActivity());
        if (mDataBinding.enhanceTabLayout.getTabLayout().getTabCount() == 0) {
            mDataBinding.enhanceTabLayout.addTab("  全部  ", 5, mScreenWidth);
            mDataBinding.enhanceTabLayout.addTab("待付款", 5, mScreenWidth);
            mDataBinding.enhanceTabLayout.addTab("待发货", 5, mScreenWidth);
            mDataBinding.enhanceTabLayout.addTab("待收货", 5, mScreenWidth);
            mDataBinding.enhanceTabLayout.addTab("已完成", 5, mScreenWidth);
        }

        final List<Fragment> fragments = new ArrayList<>();
        fragments.add(allListFragment);
        fragments.add(fukuangListFragment);
        fragments.add(dahuoListFragment);
        fragments.add(shouhuoListFragment);
        fragments.add(wanchengListFragment);

        mDataBinding.vpM.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mDataBinding.enhanceTabLayout.getTabLayout()));
        mDataBinding.vpM.setOffscreenPageLimit(5);
        mDataBinding.enhanceTabLayout.setupWithViewPager(mDataBinding.vpM);
        mDataBinding.vpM.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mDataBinding.enhanceTabLayout.getTabLayout().getTabAt(position).getText();
            }
        });

        type = getArguments().getInt("type", 0);
//        mDataBinding.vpM.setCurrentItem(type);
        mDataBinding.enhanceTabLayout.getTabLayout().getTabAt(type).select();
    }

}
