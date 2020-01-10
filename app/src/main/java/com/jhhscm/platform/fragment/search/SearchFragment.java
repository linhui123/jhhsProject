package com.jhhscm.platform.fragment.search;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.TextUtils;
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
import com.jhhscm.platform.databinding.FragmentSearchBinding;
import com.jhhscm.platform.event.SerachEvent;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.network.ScreenUtil;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.DisplayUtils;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.Utils;
import com.jhhscm.platform.views.CustomLableView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends AbsFragment<FragmentSearchBinding> {
    private int mScreenWidth = 0; // 屏幕宽度
    private int type = 0;
    private SearchNewFragment newListFragment;
    private SearchOldFragment oldListFragment;
    private SearchPeiJianFragment peijianListFragment;
    private List<String> historys;

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
        historys = ConfigUtils.getSearchHistory(getContext());
        if (ConfigUtils.getSearchHistory(getContext()) != null && ConfigUtils.getSearchHistory(getContext()).size() > 0) {
            mDataBinding.custom.setSonContent(historys);
        }

        mDataBinding.homeEidt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (mDataBinding.homeEidt.getText().toString().trim().length() > 0) {
                        if (historys != null) {
                            if (historys.contains(mDataBinding.homeEidt.getText().toString().trim())) {
                                historys.remove(mDataBinding.homeEidt.getText().toString().trim());
                            }
                        } else {
                            historys = new ArrayList<>();
                        }
                        historys.add(0, mDataBinding.homeEidt.getText().toString().trim());
                        ConfigUtils.setSearchHistory(getContext(), historys);
                    }

                    EventBusUtil.post(new SerachEvent(mDataBinding.homeEidt.getText().toString().trim()));
                    hideSoftInputFromWindow(getActivity());
                    mDataBinding.rlLabview.setVisibility(View.GONE);
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

        mDataBinding.custom.setOnItemClickListener(new CustomLableView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, String sonContent) {
                if (mDataBinding.homeEidt.getText().toString().trim().length() > 0) {
                    if (historys != null) {
                        if (historys.contains(mDataBinding.homeEidt.getText().toString().trim())) {
                            historys.remove(mDataBinding.homeEidt.getText().toString().trim());
                        }
                    } else {
                        historys = new ArrayList<>();
                    }
                    historys.add(0, mDataBinding.homeEidt.getText().toString().trim());
                    ConfigUtils.setSearchHistory(getContext(), historys);
                }
                mDataBinding.custom.setSonContent(historys);
                mDataBinding.homeEidt.setText(sonContent);
                mDataBinding.homeEidt.setFocusable(false);
                mDataBinding.homeEidt.setFocusableInTouchMode(false);
                EventBusUtil.post(new SerachEvent(mDataBinding.homeEidt.getText().toString()));
                mDataBinding.rlLabview.setVisibility(View.GONE);
                hideSoftInputFromWindow(getActivity());
            }
        });
        mDataBinding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfigUtils.removeSearchHistory(getContext());
                historys.clear();
                mDataBinding.custom.clear();
            }
        });
        mDataBinding.homeEidt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mDataBinding.rlLabview.setVisibility(View.VISIBLE);
                } else {
                    mDataBinding.rlLabview.setVisibility(View.GONE);
                }
            }
        });

        mDataBinding.homeEidt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataBinding.rlLabview.setVisibility(View.VISIBLE);
                historys = ConfigUtils.getSearchHistory(getContext());
                if (ConfigUtils.getSearchHistory(getContext()) != null && ConfigUtils.getSearchHistory(getContext()).size() > 0) {
                    mDataBinding.custom.setSonContent(historys);
                }
                showSoftInputFromWindow(getActivity(), mDataBinding.homeEidt);
            }
        });
        mDataBinding.idFlowlayout.setAdapter(new TagAdapter<String>(historys) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView mTextView = new TextView(getContext());
                LinearLayout.LayoutParams layoutparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                mTextView.setLayoutParams(layoutparams);
                mTextView.setText(s);
                mTextView.setEllipsize(TextUtils.TruncateAt.END);
                mTextView.setSingleLine();
                mTextView.setTextSize(14);
                mTextView.setTextColor(getResources().getColor(R.color.acc9));
                mTextView.setBackgroundResource(R.drawable.bg_cf5);
                mTextView.setPadding(ScreenUtil.dip2px(getContext(), 10), ScreenUtil.dip2px(getContext(), 5),
                        ScreenUtil.dip2px(getContext(), 10), ScreenUtil.dip2px(getContext(), 5));
                return mTextView;
            }
        });
    }

    /**
     * 搜索结果 头部tab
     */
    private void initTab() {
        mScreenWidth = Utils.getWindowsWidth(getActivity());
        if (mDataBinding.enhanceTabLayout.getTabLayout().getTabCount() == 0) {
            mDataBinding.enhanceTabLayout.addTab("  配件  ", 3, mScreenWidth);
            mDataBinding.enhanceTabLayout.addTab("二手机", 3, mScreenWidth);
            mDataBinding.enhanceTabLayout.addTab("  新机  ", 3, mScreenWidth);
        }

        final List<Fragment> fragments = new ArrayList<>();
        fragments.add(peijianListFragment);
        fragments.add(oldListFragment);
        fragments.add(newListFragment);
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
