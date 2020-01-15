package com.jhhscm.platform.fragment.search;

import android.app.Activity;
import android.content.Context;
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

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.FragmentSearchBinding;
import com.jhhscm.platform.event.ConsultationEvent;
import com.jhhscm.platform.event.SerachEvent;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.home.action.SaveMsgAction;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.DisplayUtils;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.tool.Utils;
import com.jhhscm.platform.views.dialog.SimpleDialog;
import com.jhhscm.platform.views.dialog.TelPhoneDialog;
import com.jhhscm.platform.views.flowlayout.FlowLayout;
import com.jhhscm.platform.views.flowlayout.TagAdapter;
import com.jhhscm.platform.views.flowlayout.TagFlowLayout;
import com.jhhscm.platform.views.recyclerview.DividerItemStrokeDecoration;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import retrofit2.Response;

public class SearchFragment extends AbsFragment<FragmentSearchBinding> {
    private int mScreenWidth = 0; // 屏幕宽度
    private int type = 0;
    private SearchNewFragment newListFragment;
    private SearchOldFragment oldListFragment;
    private SearchPeiJianFragment peijianListFragment;
    private List<String> historys;
    private TagAdapter tagAdapter;
    private LayoutInflater mInflater;

    private InnerAdapter mAdapter;
    private int mShowCount = 10;
    private int mCurrentPage = 1;
    private final int START_PAGE = mCurrentPage;
    private SearchBean searchBean;
    private String keyword = "";

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
        EventBusUtil.registerEvent(this);
        type = getArguments().getInt("type");
        mInflater = LayoutInflater.from(getActivity());
        LinearLayout.LayoutParams llParams = (LinearLayout.LayoutParams) mDataBinding.llTop.getLayoutParams();
        llParams.topMargin += DisplayUtils.getStatusBarHeight(getContext());
        mDataBinding.llTop.setLayoutParams(llParams);
        newListFragment = SearchNewFragment.instance();
        oldListFragment = SearchOldFragment.instance();
        peijianListFragment = SearchPeiJianFragment.instance();
        initTab();
        setFlow();
        initRv();
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
                    mDataBinding.rv.autoRefresh();
                    mDataBinding.rlLabview.setVisibility(View.GONE);
                    mDataBinding.rv.setVisibility(View.VISIBLE);
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

        mDataBinding.homeEidt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mDataBinding.rlLabview.setVisibility(View.VISIBLE);
                    mDataBinding.rv.setVisibility(View.GONE);
                } else {
                    mDataBinding.rlLabview.setVisibility(View.GONE);
                    mDataBinding.rv.setVisibility(View.VISIBLE);
                }
            }
        });

        mDataBinding.homeEidt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataBinding.rlLabview.setVisibility(View.VISIBLE);
                mDataBinding.rv.setVisibility(View.GONE);
                historys = ConfigUtils.getSearchHistory(getContext());
                if (ConfigUtils.getSearchHistory(getContext()) != null && ConfigUtils.getSearchHistory(getContext()).size() > 0) {
                    tagAdapter.setDatas(historys);
                }
                showSoftInputFromWindow(getActivity(), mDataBinding.homeEidt);
            }
        });
    }

    /**
     * 搜索结果列表
     */
    private void initRv() {
        mDataBinding.rv.addItemDecoration(new DividerItemStrokeDecoration(getContext()));
        mDataBinding.rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new InnerAdapter(getContext());
        mDataBinding.rv.setAdapter(mAdapter);
        mDataBinding.rv.setOnPullListener(new WrappedRecyclerView.OnPullListener() {
            @Override
            public void onRefresh(RecyclerView view) {
                findCategoryAll(true);
            }

            @Override
            public void onLoadMore(RecyclerView view) {
                findCategoryAll(false);
            }
        });
    }

    private void setFlow() {
        tagAdapter = new TagAdapter<String>() {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.search_tv,
                        mDataBinding.idFlowlayout, false);
                tv.setText(s);
                return tv;
            }
        };
        historys = ConfigUtils.getSearchHistory(getContext());
        if (ConfigUtils.getSearchHistory(getContext()) != null && ConfigUtils.getSearchHistory(getContext()).size() > 0) {
            tagAdapter.setDatas(historys);
        }
        mDataBinding.idFlowlayout.setAdapter(tagAdapter);
        mDataBinding.idFlowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                mDataBinding.homeEidt.setText(historys.get(position));
                if (mDataBinding.homeEidt.getText().toString().trim().length() > 0) {
                    if (historys != null) {
                        if (historys.size() > 0) {
                            if (historys.contains(mDataBinding.homeEidt.getText().toString().trim())) {
                                historys.remove(mDataBinding.homeEidt.getText().toString().trim());
                            }
                            historys.add(0, mDataBinding.homeEidt.getText().toString());
                        } else {
                            historys.add(mDataBinding.homeEidt.getText().toString().trim());
                        }
                    } else {
                        historys = new ArrayList<>();
                    }
                    ConfigUtils.setSearchHistory(getContext(), historys);
                }

                mDataBinding.homeEidt.setFocusable(false);
                mDataBinding.homeEidt.setFocusableInTouchMode(false);
                EventBusUtil.post(new SerachEvent(mDataBinding.homeEidt.getText().toString()));
                mDataBinding.rlLabview.setVisibility(View.GONE);
                mDataBinding.rv.autoRefresh();
                mDataBinding.rv.setVisibility(View.VISIBLE);
                hideSoftInputFromWindow(getActivity());
                return true;
            }
        });

        mDataBinding.idFlowlayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {

            }
        });

        mDataBinding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfigUtils.removeSearchHistory(getContext());
                historys.clear();
                tagAdapter.clear();
            }
        });
    }

    /**
     * 获取新机列表
     */
    private void findCategoryAll(final boolean refresh) {
        if (getContext() != null) {
            mCurrentPage = refresh ? START_PAGE : ++mCurrentPage;
            Map<String, String> map = new TreeMap<String, String>();
            map.put("keyword", mDataBinding.homeEidt.getText().toString().trim());
            map.put("page", mCurrentPage + "");
            map.put("limit", mShowCount + "");
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = Sign.getSignKey(getActivity(), map, "findCategoryAll");
            NetBean netBean = new NetBean();
            netBean.setToken("");
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(SearchFindCategoryAllAction.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<SearchBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<SearchBean>> response,
                                               BaseErrorInfo baseErrorInfo) {
                            if (getView() != null) {
                                closeDialog();
                                if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                    return;
                                }
                                if (response != null) {
                                    new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                    if (response.body().getCode().equals("200")) {
                                        doSuccessResponse(refresh, response.body().getData());
                                    } else {
                                        ToastUtils.show(getContext(), response.body().getMessage());
                                    }
                                }
                            }
                        }
                    }));
        }
    }

    private void doSuccessResponse(boolean refresh, SearchBean bean) {
        this.searchBean = bean;
        if (refresh) {
            mAdapter.setData(searchBean.getData());
        } else {
            mAdapter.append(searchBean.getData());
        }
        mDataBinding.rv.getAdapter().notifyDataSetChanged();
        mDataBinding.rv.loadComplete(mAdapter.getItemCount() == 0, ((float) searchBean.getPage().getTotal() / (float) searchBean.getPage().getPageSize()) > mCurrentPage);
    }

    private class InnerAdapter extends AbsRecyclerViewAdapter<SearchBean.DataBean> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public int getItemViewType(int position) {
            if (get(position).getGoods_type().equals("2")) {
                return 3;//配件
            } else {
                if (get(position).getIs_second().equals("1")) {
                    return 2;//二手机
                } else {
                    return 1;//新机
                }
            }
        }

        @Override
        public AbsRecyclerViewHolder<SearchBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == 1) {//新机
                return new SearchNewMechanicsViewHolder(mInflater.inflate(R.layout.item_mechanics_new, parent, false));
            } else if (viewType == 2) {//二手机
                return new SearchOldMechanicsViewHolder(mInflater.inflate(R.layout.item_mechanics_old, parent, false));
            } else {//配件
                return new SearchPeiJianViewHolder(mInflater.inflate(R.layout.item_mechanics_peijian, parent, false));
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }

    public void onEvent(final ConsultationEvent event) {
        if (event != null && (event.type == 2 || event.type == 3)) {
            new TelPhoneDialog(getContext(), new TelPhoneDialog.CallbackListener() {

                @Override
                public void clickYes(String phone) {
                    saveMsg(phone, event.type + "");
                }
            }).show();
        }
    }

    /**
     * 信息咨询
     */
    private void saveMsg(final String phone, String type) {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("mobile", phone);
        map.put("type", type);
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getContext(), map, "saveMsg");
        NetBean netBean = new NetBean();
        netBean.setToken("");
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(SaveMsgAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity> response, BaseErrorInfo baseErrorInfo) {
                        closeDialog();
                        if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                            return;
                        }
                        if (response != null) {
                            if (response.body().getCode().equals("200")) {
                                new SimpleDialog(getContext(), phone, new SimpleDialog.CallbackListener() {
                                    @Override
                                    public void clickYes() {

                                    }
                                }).show();
                            } else {
                                ToastUtils.show(getContext(), response.body().getMessage());
                            }
                        }
                    }
                }));
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
}
