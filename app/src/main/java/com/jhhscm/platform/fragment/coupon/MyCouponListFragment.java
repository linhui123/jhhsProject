package com.jhhscm.platform.fragment.coupon;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.GoodsToCartsActivity;
import com.jhhscm.platform.activity.LoginActivity;
import com.jhhscm.platform.databinding.FragmentMyCouponListBinding;
import com.jhhscm.platform.databinding.FragmentOrderStatusBinding;
import com.jhhscm.platform.event.AddressResultEvent;
import com.jhhscm.platform.event.OrderCancleEvent;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.my.order.DelOrderAction;
import com.jhhscm.platform.fragment.my.order.FindOrderListAction;
import com.jhhscm.platform.fragment.my.order.FindOrderListBean;
import com.jhhscm.platform.fragment.my.order.MyPeiJianListAdapter;
import com.jhhscm.platform.fragment.my.order.OrderStatusFragment;
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
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;

import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class MyCouponListFragment extends AbsFragment<FragmentMyCouponListBinding> {
    private UserSession userSession;
    private MyCouponListAdapter mAdapter;
    private int mShowCount = 10;
    private int mCurrentPage = 1;
    private final int START_PAGE = mCurrentPage;

    private String type = "";

    public static MyCouponListFragment instance(String type) {
        MyCouponListFragment view = new MyCouponListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        view.setArguments(bundle);
        return view;
    }

    @Override
    protected FragmentMyCouponListBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentMyCouponListBinding.inflate(inflater, container, attachToRoot);
    }

    @Override
    protected void setupViews() {
        EventBusUtil.registerEvent(this);
        type = getArguments().getString("type");
        initView();
    }

    private void initView() {
        if (ConfigUtils.getCurrentUser(getContext()) != null
                && ConfigUtils.getCurrentUser(getContext()).getMobile() != null) {
            userSession = ConfigUtils.getCurrentUser(getContext());
        } else {
            startNewActivity(LoginActivity.class);
        }

        mDataBinding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new MyCouponListAdapter(getContext());
        mDataBinding.recyclerview.setAdapter(mAdapter);
        mDataBinding.recyclerview.autoRefresh();
        mDataBinding.recyclerview.setOnPullListener(new WrappedRecyclerView.OnPullListener() {
            @Override
            public void onRefresh(RecyclerView view) {
                findOrderList(true, type);
            }

            @Override
            public void onLoadMore(RecyclerView view) {
                findOrderList(false, type);
            }
        });
    }

    public void onEvent(AddressResultEvent messageEvent) {
        mDataBinding.recyclerview.autoRefresh();
    }

    public void onEvent(OrderCancleEvent event) {
        if (event.order_code != null && type.equals(event.type)) {
            delOrder(event.order_code);
            EventBusUtil.post(new AddressResultEvent());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }

    /**
     * 获取订单列表
     * 获取列表后，遍历商品信息
     */
    private void findOrderList(final boolean refresh, final String type) {
        mCurrentPage = refresh ? START_PAGE : ++mCurrentPage;
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
                                } else if (response.body().getCode().equals("1003")) {
                                    ToastUtils.show(getContext(), "登录信息过期，请重新登录");
                                    startNewActivity(LoginActivity.class);
                                } else {
                                    ToastUtils.show(getContext(), "error " + type + ":" + response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }

    FindOrderListBean findOrderListBean;

    private void doSuccessResponse(final boolean refresh, final FindOrderListBean categoryBean) {
        this.findOrderListBean = categoryBean;
        if (categoryBean != null && categoryBean.getData() != null
                && categoryBean.getData().size() > 0) {
            for (int i = 0; i < categoryBean.getData().size(); i++) {
                findOrder(categoryBean.getData().get(i).getOrder_code());
                if (i == categoryBean.getData().size() - 1) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (refresh) {
                                mAdapter.setDetail(categoryBean,type);
                            } else {
                                mAdapter.setExpend(categoryBean,type);
                            }
                        }
                    }, 500);
                }
            }
        } else {
            if (refresh) {
                mAdapter.setDetail(categoryBean,type);
            } else {
                mAdapter.setExpend(categoryBean,type);
            }
        }
        mDataBinding.recyclerview.getAdapter().notifyDataSetChanged();
        mDataBinding.recyclerview.loadComplete(mAdapter.getItemCount() == 0, ((float) findOrderListBean.getPage().getTotal() / (float) findOrderListBean.getPage().getPageSize()) > mCurrentPage);

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
                                                for (FindOrderBean.GoodsListBean goodsListBean : findOrderBean.getGoodsList()) {
                                                    goodsListBean.setOrder_text(dataBean.getOrder_text());
                                                    goodsListBean.setOrder_status(dataBean.getOrder_status());
                                                }
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

