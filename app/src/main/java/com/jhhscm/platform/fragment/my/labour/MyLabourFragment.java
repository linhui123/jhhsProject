package com.jhhscm.platform.fragment.my.labour;


import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.LoginActivity;
import com.jhhscm.platform.activity.PushQiuZhiActivity;
import com.jhhscm.platform.activity.PushZhaoPinActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.FragmentMyLabourBinding;
import com.jhhscm.platform.event.AddressRefreshEvent;
import com.jhhscm.platform.event.AddressResultEvent;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.my.labour.action.FindLabourListAction;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.UserSession;
import com.jhhscm.platform.http.sign.SignObject;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.tool.Utils;
import com.jhhscm.platform.views.recyclerview.DividerItemStrokeDecoration;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class MyLabourFragment extends AbsFragment<FragmentMyLabourBinding> {
    private InnerAdapter mAdapter;
    private static final int TAB_COUNT = 2;
    private UserSession userSession;
    private int mShowCount = 10;
    private int mCurrentPage = 1;
    private final int START_PAGE = mCurrentPage;
    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    private int mScreenWidth = 0; // 屏幕宽度

    private int type = 1;

    public static MyLabourFragment instance() {
        MyLabourFragment view = new MyLabourFragment();
        return view;
    }

    @Override
    protected FragmentMyLabourBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentMyLabourBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        EventBusUtil.registerEvent(this);
        if (ConfigUtils.getCurrentUser(getContext()) != null
                && ConfigUtils.getCurrentUser(getContext()).getMobile() != null
                && ConfigUtils.getCurrentUser(getContext()).getUserCode() != null) {
            userSession = ConfigUtils.getCurrentUser(getContext());
        } else {
            startNewActivity(LoginActivity.class);
        }
        mDataBinding.recyclerview.addItemDecoration(new DividerItemStrokeDecoration(getContext()));
        mDataBinding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new InnerAdapter(getContext());
        mDataBinding.recyclerview.setAdapter(mAdapter);
        mDataBinding.recyclerview.autoRefresh();
        mDataBinding.recyclerview.setOnPullListener(new WrappedRecyclerView.OnPullListener() {
            @Override
            public void onRefresh(RecyclerView view) {
                findLabourList(true, type + "");
            }

            @Override
            public void onLoadMore(RecyclerView view) {
                findLabourList(true, type + "");
            }
        });

        mDataBinding.tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 0) {
                    PushZhaoPinActivity.start(getContext(), "", "", 0);
                } else {
                    PushQiuZhiActivity.start(getContext(), "", "", 0);
                }
            }
        });
        initTabColumn();
    }

    /**
     * 初始化Column栏目项
     */
    private void initTabColumn() {
        final List<String> tabNameS = new ArrayList<>();
        tabNameS.add("我的求职");
        tabNameS.add("我的招聘");

        int count = TAB_COUNT;
        fragments.clear();//清空
        mScreenWidth = Utils.getWindowsWidth(getActivity());
        mDataBinding.enhanceTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    type = 1;
                    mDataBinding.tel.setImageResource(R.mipmap.ic_push_qiuzhi);
                    findLabourList(true, "1");
                } else if (tab.getPosition() == 1) {
                    type = 0;
                    mDataBinding.tel.setImageResource(R.mipmap.ic_push_zhaopin);
                    findLabourList(true, "0");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        for (int i = 0; i < count; i++) {
            mDataBinding.enhanceTabLayout.addTab(tabNameS.get(i), count, mScreenWidth);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }

    public void onEvent(AddressResultEvent messageEvent) {
        Log.e("AddressResultEvent"," AddressResultEvent ");
        findLabourList(true, type + "");
    }

    public void onEvent(AddressRefreshEvent messageEvent) {
        Log.e("AddressRefreshEvent"," AddressRefreshEvent ");
        findLabourList(true, type + "");
    }


    /**
     * 查询个人劳务列表
     */
    private void findLabourList(final boolean refresh, final String type) {
        mCurrentPage = refresh ? START_PAGE : ++mCurrentPage;
        Map<String, Object> map = new TreeMap<String, Object>();
        map.put("user_code", userSession.getUserCode());
        map.put("type", type);
        map.put("page", mCurrentPage);
        map.put("limit", mShowCount);
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = SignObject.getSignKey(getActivity(), map, "findLabourList: " + "type");
        NetBean netBean = new NetBean();
        netBean.setToken(userSession.getToken());
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(FindLabourListAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<FindLabourListBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<FindLabourListBean>> response, BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                if (response.body().getCode().equals("200")) {
                                    findLabourListBean = response.body().getData();
                                    for (FindLabourListBean.DataBean dataBean : findLabourListBean.getData()) {
                                        dataBean.setType(type);
                                    }
                                    doSuccessResponse(refresh, findLabourListBean);
//                                    mAdapter.setDetail(response.body().getData());
                                } else {
                                    ToastUtils.show(getContext(), "error " + type + ":" + response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }

    FindLabourListBean findLabourListBean;

    private void doSuccessResponse(boolean refresh, FindLabourListBean categoryBean) {
        this.findLabourListBean = categoryBean;
        if (refresh) {
            mAdapter.setData(categoryBean.getData());
        } else {
            mAdapter.append(categoryBean.getData());
        }
        mDataBinding.recyclerview.getAdapter().notifyDataSetChanged();
        if (mAdapter.getItemCount() == 0) {
            mDataBinding.recyclerview.loadComplete(true, false);
        } else {
            mDataBinding.recyclerview.loadComplete(mAdapter.getItemCount() == 0, ((float) findLabourListBean.getPage().getTotal() / (float) findLabourListBean.getPage().getPageSize()) > mCurrentPage);
        }
    }

    private class InnerAdapter extends AbsRecyclerViewAdapter<FindLabourListBean.DataBean> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<FindLabourListBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyLabourViewHolder(mInflater.inflate(R.layout.item_my_labour, parent, false));
        }
    }
}
