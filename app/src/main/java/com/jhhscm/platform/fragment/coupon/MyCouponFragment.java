package com.jhhscm.platform.fragment.coupon;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.LoginActivity;
import com.jhhscm.platform.databinding.FragmentCouponCenterBinding;
import com.jhhscm.platform.databinding.FragmentMyCouponBinding;
import com.jhhscm.platform.databinding.FragmentMyOrderListBinding;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.my.order.MyOrderListFragment;
import com.jhhscm.platform.fragment.my.order.OrderStatusFragment;
import com.jhhscm.platform.http.bean.UserSession;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.Utils;

import java.util.ArrayList;
import java.util.List;

public class MyCouponFragment extends AbsFragment<FragmentMyCouponBinding> {
    private UserSession userSession;
    private int mScreenWidth = 0; // 屏幕宽度

    private MyCouponListFragment unCounponFragment;
    private MyCouponListFragment useCounponFragment;
    private MyCouponListFragment oldCounponFragment;

    public static MyCouponFragment instance() {
        MyCouponFragment view = new MyCouponFragment();
        return view;
    }

    @Override
    protected FragmentMyCouponBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentMyCouponBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        if (ConfigUtils.getCurrentUser(getContext()) != null
                && ConfigUtils.getCurrentUser(getContext()).getMobile() != null) {
            userSession = ConfigUtils.getCurrentUser(getContext());
        } else {
            startNewActivity(LoginActivity.class);
        }

        unCounponFragment = MyCouponListFragment.instance("0");
        useCounponFragment = MyCouponListFragment.instance("1");
        oldCounponFragment = MyCouponListFragment.instance("2");

        initTab();
    }

    /**
     * 搜索结果 头部tab
     */
    private void initTab() {
        mScreenWidth = Utils.getWindowsWidth(getActivity());
        if (mDataBinding.enhanceTabLayout.getTabLayout().getTabCount() == 0) {
            mDataBinding.enhanceTabLayout.addTab("未使用", 3, mScreenWidth);
            mDataBinding.enhanceTabLayout.addTab("已使用", 3, mScreenWidth);
            mDataBinding.enhanceTabLayout.addTab("已过期", 3, mScreenWidth);
        }

        final List<Fragment> fragments = new ArrayList<>();
        fragments.add(unCounponFragment);
        fragments.add(useCounponFragment);
        fragments.add(oldCounponFragment);


        mDataBinding.vpM.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mDataBinding.enhanceTabLayout.getTabLayout()));
        mDataBinding.vpM.setOffscreenPageLimit(3);
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
    }

}

