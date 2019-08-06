package com.jhhscm.platform.fragment.my.order;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.LoginActivity;
import com.jhhscm.platform.databinding.FragmentMyPeiJianListBinding;
import com.jhhscm.platform.databinding.FragmentOrderDetailBinding;
import com.jhhscm.platform.event.GetRegionEvent;
import com.jhhscm.platform.fragment.Mechanics.PeiJianFragment;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.sale.FindOrderAction;
import com.jhhscm.platform.fragment.sale.FindOrderBean;
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

public class OrderDetailFragment extends AbsFragment<FragmentOrderDetailBinding> {
    private UserSession userSession;
    private FindOrderBean findOrderBean;
    private String orderGood = "";
    private int type;

    public static OrderDetailFragment instance() {
        OrderDetailFragment view = new OrderDetailFragment();
        return view;
    }

    @Override
    protected FragmentOrderDetailBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentOrderDetailBinding.inflate(inflater, container, attachToRoot);
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

        type = getArguments().getInt("type");
        orderGood = getArguments().getString("orderGood");
        LinearLayout.LayoutParams llParams = (LinearLayout.LayoutParams) mDataBinding.rlTop.getLayoutParams();
        llParams.topMargin += DisplayUtils.getStatusBarHeight(getContext());
        mDataBinding.rlTop.setLayoutParams(llParams);
        findOrder();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }

    public void onEvent(GetRegionEvent messageEvent) {

    }

    /**
     * 订单查询
     */
    private void findOrder() {
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
//                                    String jsonData = Des.decyptByDes(response.body().getContent());
//                                    Gson gson = new Gson();
//                                    findOrderBean = gson.fromJson(jsonData, FindOrderBean.class);
                                    findOrderBean=response.body().getData();
                                    if (findOrderBean != null && findOrderBean.getOrder() != null) {
                                        intView(findOrderBean);
                                    }
                                } else {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }

    private void intView(FindOrderBean findOrderBean) {
        if (type == 1) {
            mDataBinding.orderType.setText("待付款");
            //剩余支付时间
            mDataBinding.tvInfo.setText("剩1小时17分钟将自动关闭");
            mDataBinding.tvCancle.setVisibility(View.VISIBLE);
            mDataBinding.tvTijiao.setVisibility(View.VISIBLE);
        } else if (type == 2) {
            mDataBinding.orderType.setText("待发货");
            mDataBinding.tvInfo.setText("买家已付款，等待商家发货");
        } else if (type == 3) {
            mDataBinding.orderType.setText("待收货");
            //剩余确认收货时间
            mDataBinding.tvInfo.setText("剩7天20小时30分钟将自动确认收货");
        } else if (type == 4) {
            mDataBinding.orderType.setText("已完成");
            mDataBinding.tvInfo.setVisibility(View.GONE);
        } else if (type == 5) {
            mDataBinding.orderType.setText("已取消");
            mDataBinding.tvInfo.setVisibility(View.GONE);
            mDataBinding.tvDel.setVisibility(View.VISIBLE);
        }
    }
}
