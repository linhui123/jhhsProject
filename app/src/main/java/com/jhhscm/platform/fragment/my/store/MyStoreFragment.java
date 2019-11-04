package com.jhhscm.platform.fragment.my.store;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.LoginActivity;
import com.jhhscm.platform.activity.StoreOrderSubmit1Activity;
import com.jhhscm.platform.activity.StoreOrderSubmit2Activity;
import com.jhhscm.platform.databinding.FragmentMyBinding;
import com.jhhscm.platform.databinding.FragmentMyStoreBinding;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.coupon.MyCouponListFragment;
import com.jhhscm.platform.fragment.my.MyFragment;
import com.jhhscm.platform.http.bean.UserSession;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.Utils;

import java.util.ArrayList;
import java.util.List;

public class MyStoreFragment extends AbsFragment<FragmentMyStoreBinding> {
    private UserSession userSession;
    private int mScreenWidth = 0; // 屏幕宽度

    private MyProductFragment unCounponFragment;
    private MyMemberFragment useCounponFragment;
    private MyStoreOrderFragment oldCounponFragment;

    public static MyStoreFragment instance() {
        MyStoreFragment view = new MyStoreFragment();
        return view;
    }

    @Override
    protected FragmentMyStoreBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentMyStoreBinding.inflate(inflater, container, attachToRoot);
    }


    @Override
    protected void setupViews() {
        if (ConfigUtils.getCurrentUser(getContext()) != null
                && ConfigUtils.getCurrentUser(getContext()).getMobile() != null) {
            userSession = ConfigUtils.getCurrentUser(getContext());
        } else {
            startNewActivity(LoginActivity.class);
        }

        unCounponFragment = MyProductFragment.instance();
        useCounponFragment = MyMemberFragment.instance();
        oldCounponFragment = MyStoreOrderFragment.instance();

        initTab();

        mDataBinding.invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mDataBinding.settlement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreOrderSubmit1Activity.start(getContext());
            }
        });
    }

    /**
     * 搜索结果 头部tab
     */
    private void initTab() {
        mScreenWidth = Utils.getWindowsWidth(getActivity());
        if (mDataBinding.enhanceTabLayout.getTabLayout().getTabCount() == 0) {
            mDataBinding.enhanceTabLayout.addTab("产品", 3, mScreenWidth);
            mDataBinding.enhanceTabLayout.addTab("会员", 3, mScreenWidth);
            mDataBinding.enhanceTabLayout.addTab("订单", 3, mScreenWidth);
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
