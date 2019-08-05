package com.jhhscm.platform.fragment.my.order;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.LoginActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.FragmentMyPeiJianListBinding;
import com.jhhscm.platform.event.GetRegionEvent;
import com.jhhscm.platform.fragment.GoodsToCarts.CreateOrderViewHolder;
import com.jhhscm.platform.fragment.GoodsToCarts.GetCartGoodsByUserCodeBean;
import com.jhhscm.platform.fragment.Mechanics.PeiJianFragment;

import com.jhhscm.platform.fragment.Mechanics.bean.FindCategoryBean;
import com.jhhscm.platform.fragment.base.AbsFragment;

import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.UserSession;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.DisplayUtils;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.tool.Utils;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;


public class MyPeiJianListFragment extends AbsFragment<FragmentMyPeiJianListBinding> {
    private MyPeiJianListAdapter mAdapter;
    private UserSession userSession;
    private int mShowCount = 10;
    private int mCurrentPage = 1;
    private final int START_PAGE = mCurrentPage;

    private static final int TAB_COUNT = 5;
    public static final int ORDER_STAUS_1 = 1;
    public static final int ORDER_STAUS_2 = 1;
    public static final int ORDER_STAUS_3 = 2;
    public static final int ORDER_STAUS_4 = 3;
    public static final int ORDER_STAUS_5 = 4;

    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    private int mScreenWidth = 0; // 屏幕宽度

    public static MyPeiJianListFragment instance() {
        MyPeiJianListFragment view = new MyPeiJianListFragment();
        return view;
    }

    @Override
    protected FragmentMyPeiJianListBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentMyPeiJianListBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        EventBusUtil.registerEvent(this);
        RelativeLayout.LayoutParams llParams = (RelativeLayout.LayoutParams) mDataBinding.rlColumn.getLayoutParams();
        llParams.topMargin += DisplayUtils.getStatusBarHeight(getContext());
        mDataBinding.rlColumn.setLayoutParams(llParams);

        if (ConfigUtils.getCurrentUser(getContext()) != null
                && ConfigUtils.getCurrentUser(getContext()).getMobile() != null) {
            userSession = ConfigUtils.getCurrentUser(getContext());
        } else {
            startNewActivity(LoginActivity.class);
        }

        mDataBinding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new MyPeiJianListAdapter(getContext());
        mDataBinding.recyclerview.setAdapter(mAdapter);
        mDataBinding.recyclerview.autoRefresh();
        mDataBinding.recyclerview.setOnPullListener(new WrappedRecyclerView.OnPullListener() {
            @Override
            public void onRefresh(RecyclerView view) {
                findOrderList(true, "");
                //  mAdapter.setDetail(new FindOrderListBean());
            }

            @Override
            public void onLoadMore(RecyclerView view) {
                findOrderList(true, "");
                //  mAdapter.setDetail(new FindOrderListBean());
            }
        });


        initTabColumn();
    }


    /**
     * 初始化Column栏目项
     */
    private void initTabColumn() {
        final List<String> tabNameS = new ArrayList<>();
        tabNameS.add("  全部   ");
        tabNameS.add("待付款");
        tabNameS.add("代发货");
        tabNameS.add("待收货");
        tabNameS.add("已完成");

        int count = TAB_COUNT;
        fragments.clear();//清空
        if (count == 0) {
            mDataBinding.rlCaseBaseNull.setVisibility(View.VISIBLE);
        } else {
            mDataBinding.rlCaseBaseNull.setVisibility(View.GONE);
        }
        for (int i = 0; i < count; i++) {
            fragments.add(PeiJianFragment.instance());
        }

        mScreenWidth = Utils.getWindowsWidth(getActivity());
        mDataBinding.enhanceTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    findOrderList(true, "");
                }else  if (tab.getPosition() == 1) {
                    findOrderList(true, "1");
                }else  if (tab.getPosition() == 2) {
                    findOrderList(true, "2");
                }else  if (tab.getPosition() == 3) {
                    findOrderList(true, "3");
                }else  if (tab.getPosition() == 4) {
                    findOrderList(true, "4");
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

    public void onEvent(GetRegionEvent messageEvent) {

    }

    /**
     * 获取订单列表
     */
    private void findOrderList(final boolean refresh, final String type) {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("user_code", userSession.getUserCode());
        map.put("order_type", type);
        map.put("page", mCurrentPage + "");
        map.put("limit", mShowCount + "");
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getActivity(), map, "findOrderList: " + "type");
        NetBean netBean = new NetBean();
        netBean.setToken(userSession.getToken());
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(FindOrderListAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<FindOrderListBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<FindOrderListBean>> response, BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                if (response.body().getCode().equals("200")) {
                                    findOrderListBean = response.body().getData();
                                    doSuccessResponse(refresh, findOrderListBean);
//                                    mAdapter.setDetail(response.body().getData());
                                } else {
                                    ToastUtils.show(getContext(), "error " + type + ":" + response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }

    FindOrderListBean findOrderListBean;

    private void doSuccessResponse(boolean refresh, FindOrderListBean categoryBean) {
        this.findOrderListBean = categoryBean;
        if (refresh) {
            mAdapter.setDetail(categoryBean);
        } else {
            mAdapter.setExpend(categoryBean);
        }
        mDataBinding.recyclerview.getAdapter().notifyDataSetChanged();
        mDataBinding.recyclerview.loadComplete(mAdapter.getItemCount() == 0, ((float) findOrderListBean.getPage().getTotal() / (float) findOrderListBean.getPage().getPageSize()) > mCurrentPage);
    }
}
