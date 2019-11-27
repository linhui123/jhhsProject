package com.jhhscm.platform.fragment.search;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhhscm.platform.R;
import com.jhhscm.platform.databinding.FragmentAuthenticationBinding;
import com.jhhscm.platform.databinding.FragmentSearchBinding;
import com.jhhscm.platform.event.SerachEvent;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.my.AuthenticationFragment;
import com.jhhscm.platform.fragment.my.collect.NewCollectListFragment;
import com.jhhscm.platform.fragment.my.collect.OldCollectListFragment;
import com.jhhscm.platform.fragment.my.collect.PeiJianCollectListFragment;
import com.jhhscm.platform.http.bean.UserSession;
import com.jhhscm.platform.tool.DisplayUtils;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.Utils;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends AbsFragment<FragmentSearchBinding> {

    private int mScreenWidth = 0; // 屏幕宽度
    private int type = 0;
    private SearchNewFragment newListFragment;
    private SearchOldFragment oldListFragment;
    private SearchPeiJianFragment peijianListFragment;

    public static SearchFragment instance() {
        SearchFragment view = new SearchFragment();
        return view;
    }

    @Override
    protected FragmentSearchBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentSearchBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        type = getArguments().getInt("type");

        LinearLayout.LayoutParams llParams = (LinearLayout.LayoutParams) mDataBinding.llTop.getLayoutParams();
        llParams.topMargin += DisplayUtils.getStatusBarHeight(getContext());
        mDataBinding.llTop.setLayoutParams(llParams);

        newListFragment = SearchNewFragment.instance();
        oldListFragment = SearchOldFragment.instance();
        peijianListFragment = SearchPeiJianFragment.instance();
        initTab();

        mDataBinding.homeEidt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    EventBusUtil.post(new SerachEvent(mDataBinding.homeEidt.getText().toString()));
                    hideSoftInputFromWindow(getActivity());
                    return true;
                }
                return false;
            }
        });

        mDataBinding.cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    /**
     * 搜索结果 头部tab
     */
    private void initTab() {
        mScreenWidth = Utils.getWindowsWidth(getActivity());
        if (mDataBinding.enhanceTabLayout.getTabLayout().getTabCount() == 0) {
            mDataBinding.enhanceTabLayout.addTab("  新机  ", 3, mScreenWidth);
            mDataBinding.enhanceTabLayout.addTab("二手机", 3, mScreenWidth);
            mDataBinding.enhanceTabLayout.addTab("  配件  ", 3, mScreenWidth);
        }

        final List<Fragment> fragments = new ArrayList<>();
        fragments.add(newListFragment);
        fragments.add(oldListFragment);
        fragments.add(peijianListFragment);

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

        mDataBinding.enhanceTabLayout.getTabLayout().getTabAt(type).select();
    }

    /**
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * EditText 关闭软键盘
     */
    public static void hideSoftInputFromWindow(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
    }

//    public void onResume() {
//        super.onResume();
//        MobclickAgent.onPageStart("search");
//    }
//
//    public void onPause() {
//        super.onPause();
//        MobclickAgent.onPageEnd("search");
//    }
}
