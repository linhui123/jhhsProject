package com.jhhscm.platform.fragment.my.order;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
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
import com.jhhscm.platform.activity.GoodsToCartsActivity;
import com.jhhscm.platform.activity.LoginActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.FragmentMyPeiJianListBinding;
import com.jhhscm.platform.event.AddressRefreshEvent;
import com.jhhscm.platform.event.AddressResultEvent;
import com.jhhscm.platform.event.GetRegionEvent;
import com.jhhscm.platform.event.OrderCancleEvent;
import com.jhhscm.platform.fragment.GoodsToCarts.CreateOrderViewHolder;
import com.jhhscm.platform.fragment.GoodsToCarts.GetCartGoodsByUserCodeBean;
import com.jhhscm.platform.fragment.Mechanics.PeiJianFragment;

import com.jhhscm.platform.fragment.Mechanics.bean.FindCategoryBean;
import com.jhhscm.platform.fragment.base.AbsFragment;

import com.jhhscm.platform.fragment.sale.FindOrderAction;
import com.jhhscm.platform.fragment.sale.FindOrderBean;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.ResultBean;
import com.jhhscm.platform.http.bean.UserSession;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.DisplayUtils;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.tool.Utils;
import com.jhhscm.platform.views.recyclerview.DividerItemStrokeDecoration;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;

import java.security.spec.MGF1ParameterSpec;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class MyPeiJianListFragment extends AbsFragment<FragmentMyPeiJianListBinding> {
    private MyPeiJianListAdapter mAdapter;
    private UserSession userSession;
    private int mShowCount = 5;
    private int mCurrentPage = 1;
    private final int START_PAGE = mCurrentPage;

    private static final int TAB_COUNT = 5;
    private String type = "";
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
                findOrderList(true, type);
            }

            @Override
            public void onLoadMore(RecyclerView view) {
                findOrderList(true, type);
            }
        });

        mDataBinding.tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsToCartsActivity.start(getContext());
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
        for (int i = 0; i < count; i++) {
            fragments.add(PeiJianFragment.instance());
        }

        mScreenWidth = Utils.getWindowsWidth(getActivity());
        mDataBinding.enhanceTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                showDialog();
                if (tab.getPosition() == 0) {
                    type = "";
                    mDataBinding.recyclerview.autoRefresh();
                } else if (tab.getPosition() == 1) {
                    type = "1";
                    mDataBinding.recyclerview.autoRefresh();
                } else if (tab.getPosition() == 2) {
                    type = "2";
                    mDataBinding.recyclerview.autoRefresh();
                } else if (tab.getPosition() == 3) {
                    type = "3";
                    mDataBinding.recyclerview.autoRefresh();
                } else if (tab.getPosition() == 4) {
                    type = "4";
                    mDataBinding.recyclerview.autoRefresh();
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
        mDataBinding.recyclerview.autoRefresh();
    }

    public void onEvent(OrderCancleEvent event) {
        if (event.order_code != null) {
            delOrder(event.order_code);
        }
    }

    /**
     * 获取订单列表
     * 获取列表后，遍历商品信息
     */
    private void findOrderList(final boolean refresh, final String type) {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("user_code", userSession.getUserCode());
        map.put("order_type", type);
        map.put("page", mCurrentPage + "");
        map.put("limit", mShowCount + "");
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getActivity(), map, "findOrderList: " + type);
        NetBean netBean = new NetBean();
        netBean.setToken(userSession.getToken());
        netBean.setSign(sign);
        netBean.setContent(content);
//        onNewRequestCall(FindOrderListAction.newInstance(getContext(), netBean)
//                .request(new AHttpService.IResCallback<BaseEntity<FindOrderListBean>>() {
//            @Override
//            public void onCallback(int resultCode, Response<BaseEntity<FindOrderListBean>> response, BaseErrorInfo baseErrorInfo) {
//                if (getView() != null) {
//                    closeDialog();
//                    if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
//                        return;
//                    }
//                    if (response != null) {
//                        if (response.body().getCode().equals("200")) {
//                            findOrderListBean = response.body().getData();
//                            doSuccessResponse(refresh, findOrderListBean);
//                        } else if (response.body().getCode().equals("1003")) {
//                            ToastUtils.show(getContext(), "登录信息过期，请重新登录");
//                            startNewActivity(LoginActivity.class);
//                        } else {
//                            ToastUtils.show(getContext(), "error " + type + ":" + response.body().getMessage());
//                        }
//                    }
//                }
//            }
//        }));
    }

    FindOrderListBean findOrderListBean;

    private void doSuccessResponse(final boolean refresh, final FindOrderListBean categoryBean) {
        this.findOrderListBean = categoryBean;
        if (findOrderListBean != null && findOrderListBean.getData() != null
                && findOrderListBean.getData().size() > 0) {
            for (int i = 0; i < findOrderListBean.getData().size(); i++) {
                findOrder(findOrderListBean.getData().get(i).getOrder_code());
                if (i == findOrderListBean.getData().size() - 1) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (refresh) {
                                mAdapter.setDetail(categoryBean);
                            } else {
                                mAdapter.setExpend(categoryBean);
                            }
                        }
                    }, 500);
                }
            }
        } else {
            if (refresh) {
                mAdapter.setDetail(categoryBean);
            } else {
                mAdapter.setExpend(categoryBean);
            }
        }
        mDataBinding.recyclerview.getAdapter().notifyDataSetChanged();
        mDataBinding.recyclerview.loadComplete(mAdapter.getItemCount() == 0, ((float) findOrderListBean.getPage().getTotal() / (float) findOrderListBean.getPage().getPageSize()) > mCurrentPage);

//        if (findOrderListBean.getData().size() > 0) {
//            mDataBinding.rlCaseBaseNull.setVisibility(View.VISIBLE);
//            mDataBinding.recyclerview.hideLoad();
//        } else {
//            mDataBinding.rlCaseBaseNull.setVisibility(View.GONE);
//        }
    }

    /**
     * 订单查询
     */
    private void findOrder(final String orderGood) {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("orderGood", orderGood);
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getContext(), map, "findOrder");
        NetBean netBean = new NetBean();
        netBean.setToken(userSession.getToken());
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(FindOrderAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<FindOrderBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<FindOrderBean>> response,
                                           BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                if (response.body().getCode().equals("200")) {
                                    FindOrderBean findOrderBean = response.body().getData();
                                    if (findOrderBean != null && findOrderBean.getGoodsList() != null) {
                                        for (FindOrderListBean.DataBean dataBean : findOrderListBean.getData()) {
                                            if (dataBean.getOrder_code().equals(orderGood)) {
                                                dataBean.setGoodsListBeans(findOrderBean.getGoodsList());
                                            }
                                        }
                                    }
                                } else {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }

    /**
     * 取消订单
     */
    private void delOrder(final String orderGood) {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("order_code", orderGood);
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getContext(), map, "delOrder");
        NetBean netBean = new NetBean();
        netBean.setToken(userSession.getToken());
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(DelOrderAction.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<ResultBean>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<ResultBean>> response,
                                           BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                if (response.body().getCode().equals("200")) {
                                    mDataBinding.recyclerview.autoRefresh();
                                } else {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }
}

