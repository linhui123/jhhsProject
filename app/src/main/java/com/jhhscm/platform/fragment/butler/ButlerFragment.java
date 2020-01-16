package com.jhhscm.platform.fragment.butler;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jhhscm.platform.activity.LoginActivity;
import com.jhhscm.platform.databinding.FragmentButlerBinding;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.my.book.BookingFragment;
import com.jhhscm.platform.fragment.my.labour.MyLabourFragment;
import com.jhhscm.platform.fragment.repayment.RepaymentFragment;
import com.jhhscm.platform.fragment.vehicle.VehicleMonitoringFragment;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.DisplayUtils;
import com.jhhscm.platform.tool.Utils;

import java.util.ArrayList;
import java.util.List;

public class ButlerFragment extends AbsFragment<FragmentButlerBinding> {
    private int mScreenWidth = 0; // 屏幕宽度
    private int type = 0;
    private VehicleMonitoringFragment vehicleMonitoringFragment;
    private RepaymentFragment repaymentFragment;
    private MyLabourFragment labourFragment;
    private BookingFragment bookingFragment;

    public static ButlerFragment instance() {
        ButlerFragment view = new ButlerFragment();
        return view;
    }

    @Override
    protected FragmentButlerBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentButlerBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        LinearLayout.LayoutParams llParams = (LinearLayout.LayoutParams) mDataBinding.top.getLayoutParams();
        llParams.topMargin += DisplayUtils.getStatusBarHeight(getContext());
        mDataBinding.top.setLayoutParams(llParams);
        if (ConfigUtils.getCurrentUser(getContext()) != null
                && ConfigUtils.getCurrentUser(getContext()).getMobile() != null
                && ConfigUtils.getCurrentUser(getContext()).getUserCode() != null) {
        } else {
            startNewActivity(LoginActivity.class);
            getActivity().finish();
        }

        mDataBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        vehicleMonitoringFragment = VehicleMonitoringFragment.instance();
        repaymentFragment = RepaymentFragment.instance();
        labourFragment = MyLabourFragment.instance();
        bookingFragment = BookingFragment.instance();
        initTab();
    }

    /**
     * 搜索结果 头部tab
     */
    private void initTab() {

        mScreenWidth = Utils.getWindowsWidth(getActivity());
        if (mDataBinding.enhanceTabLayout.getTabLayout().getTabCount() == 0) {
            mDataBinding.enhanceTabLayout.addTab("云惠管家", 18);
            mDataBinding.enhanceTabLayout.addTab("我的还款", 18);
            mDataBinding.enhanceTabLayout.addTab("我的劳务", 18);
            mDataBinding.enhanceTabLayout.addTab("记账工具", 18);
        }

        final List<Fragment> fragments = new ArrayList<>();
        fragments.add(vehicleMonitoringFragment);
        fragments.add(repaymentFragment);
        fragments.add(labourFragment);
        fragments.add(bookingFragment);
        mDataBinding.vpM.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mDataBinding.enhanceTabLayout.getTabLayout()));
        mDataBinding.vpM.setOffscreenPageLimit(4);
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

        mDataBinding.enhanceTabLayout.getTabLayout().getTabAt(type).select();

    }
}
