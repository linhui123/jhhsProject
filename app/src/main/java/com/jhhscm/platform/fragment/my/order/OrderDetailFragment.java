package com.jhhscm.platform.fragment.my.order;


import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.Resources;
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
import com.jhhscm.platform.activity.CashierActivity;
import com.jhhscm.platform.activity.LoginActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.FragmentMyPeiJianListBinding;
import com.jhhscm.platform.databinding.FragmentOrderDetailBinding;
import com.jhhscm.platform.event.AddressRefreshEvent;
import com.jhhscm.platform.event.GetRegionEvent;
import com.jhhscm.platform.fragment.GoodsToCarts.CreateOrderResultBean;
import com.jhhscm.platform.fragment.Mechanics.PeiJianFragment;
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
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.tool.Utils;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

public class OrderDetailFragment extends AbsFragment<FragmentOrderDetailBinding> {
    private UserSession userSession;
    private FindOrderBean findOrderBean;
    private String order_code = "";
    private int type;
    private InnerAdapter mAdapter;

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
        checkDeviceHasNavigationBar(getActivity());
        if (ConfigUtils.getCurrentUser(getContext()) != null
                && ConfigUtils.getCurrentUser(getContext()).getMobile() != null) {
            userSession = ConfigUtils.getCurrentUser(getContext());
        } else {
            startNewActivity(LoginActivity.class);
        }

        type = getArguments().getInt("type");
        Log.e("type", "type :"+type);
        order_code = getArguments().getString("orderGood");

        mDataBinding.rv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new InnerAdapter(getContext());
        mDataBinding.rv.setAdapter(mAdapter);

        mDataBinding.tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delOrder();
            }
        });

        mDataBinding.tvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                delOrder();
            }
        });

        mDataBinding.tvTijiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CashierActivity.start(getContext(), new CreateOrderResultBean(new CreateOrderResultBean.DataBean(order_code)));
            }
        });
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
        map.put("orderGood", order_code);
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
                                    findOrderBean = response.body().getData();
                                    if (findOrderBean != null) {
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

    /**
     * 取消订单
     */
    private void delOrder() {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("order_code", order_code);
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
                                    getActivity().finish();
                                    EventBusUtil.post(new AddressRefreshEvent(1));
                                } else {
                                    ToastUtils.show(getContext(), response.body().getMessage());
                                }
                            }
                        }
                    }
                }));
    }

    private void intView(final FindOrderBean findOrderBean) {
        if (type == 1) {
            mDataBinding.orderType.setText(findOrderBean.getOrder().getOrder_text());
            //剩余支付时间
            mDataBinding.tvInfo.setText("剩1小时17分钟将自动关闭");
            mDataBinding.tvCancle.setVisibility(View.VISIBLE);
            mDataBinding.tvTijiao.setVisibility(View.VISIBLE);
            mDataBinding.rlWuliu.setVisibility(View.GONE);
            mDataBinding.rlWuliuNo.setVisibility(View.GONE);
            mDataBinding.tvIm.setBackgroundResource(R.mipmap.ic_order_1);
        } else if (type == 2) {
            mDataBinding.orderType.setText(findOrderBean.getOrder().getOrder_text());
            mDataBinding.tvInfo.setText("买家已付款，等待商家发货");
            mDataBinding.rlWuliu.setVisibility(View.GONE);
            mDataBinding.rlWuliuNo.setVisibility(View.GONE);
            mDataBinding.tvIm.setBackgroundResource(R.mipmap.ic_order_2);
        } else if (type == 3) {
            mDataBinding.orderType.setText(findOrderBean.getOrder().getOrder_text());
            //剩余确认收货时间
            mDataBinding.tvInfo.setText("剩7天20小时30分钟将自动确认收货");
//            mDataBinding.tvWuliu.setText(findOrderBean.getOrder().getShip_channel());
//            mDataBinding.tvWuliuNo.setText(findOrderBean.getOrder().getShip_sn());
            mDataBinding.tvIm.setBackgroundResource(R.mipmap.ic_order_3);
        } else if (type == 4) {
            mDataBinding.orderType.setText(findOrderBean.getOrder().getOrder_text());
            mDataBinding.tvInfo.setVisibility(View.GONE);
            mDataBinding.rlWuliu.setVisibility(View.GONE);
            mDataBinding.rlWuliuNo.setVisibility(View.GONE);
            mDataBinding.tvIm.setBackgroundResource(R.mipmap.ic_order_4);
        } else if (type == 5) {
            mDataBinding.orderType.setText(findOrderBean.getOrder().getOrder_text());
            mDataBinding.tvInfo.setVisibility(View.GONE);
            mDataBinding.tvDel.setVisibility(View.VISIBLE);
            mDataBinding.rlWuliu.setVisibility(View.GONE);
            mDataBinding.rlWuliuNo.setVisibility(View.GONE);
        }

        mDataBinding.tvName.setText(findOrderBean.getOrder().getConsignee());
        mDataBinding.tvTel.setText(findOrderBean.getOrder().getMobile());
        mDataBinding.tvAddress.setText(findOrderBean.getOrder().getAddress());
        mDataBinding.tvPrice.setText("￥" + findOrderBean.getOrder().getGoods_price());
        mDataBinding.tvYunfei.setText("+￥" + findOrderBean.getOrder().getFreight_price());
        mDataBinding.tvYouhui.setText("-￥" + findOrderBean.getOrder().getCoupon_price());
        mDataBinding.tvTotal.setText("￥" + findOrderBean.getOrder().getOrder_price());

        mDataBinding.tvOrderNo.setText(order_code);
        mDataBinding.tvOrderTime.setText(findOrderBean.getOrder().getEnd_time());

        mAdapter.setData(findOrderBean.getGoodsList());

        mDataBinding.tvOrderNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (order_code != null) {
                    copy(order_code, getContext());
                }
            }
        });

        mDataBinding.tvWuliuNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (findOrderBean.getOrder().getShip_sn() != null) {
//                    copy(findOrderBean.getOrder().getShip_sn(), getContext());
//                }
            }
        });
    }

    private class InnerAdapter extends AbsRecyclerViewAdapter<FindOrderBean.GoodsListBean> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<FindOrderBean.GoodsListBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new OrderListViewHolder(mInflater.inflate(R.layout.item_create_order, parent, false));
        }
    }

    /**
     * 判断是否存在NavigationBar
     *
     * @param context：上下文环境
     * @return：返回是否存在(true/false)
     */
    public boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                //不存在虚拟按键
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                //存在虚拟按键
                hasNavigationBar = true;
                RelativeLayout.LayoutParams layout = (RelativeLayout.LayoutParams) mDataBinding.rlBottom.getLayoutParams();
                //setMargins：顺序是左、上、右、下
                layout.setMargins(15, 0, 15, getNavigationBarHeight(getActivity()) + 10);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasNavigationBar;
    }

    /**
     * 测量底部导航栏的高度
     *
     * @param mActivity:上下文环境
     * @return：返回测量出的底部导航栏高度
     */
    private int getNavigationBarHeight(Activity mActivity) {
        Resources resources = mActivity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    /**
     * 实现文本复制功能
     * add by wangqianzhou
     *
     * @param content
     */
    public static void copy(String content, Context context) {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
        ToastUtil.show(context, "复制成功!");
    }

    /**
     * 实现粘贴功能
     * add by wangqianzhou
     *
     * @param context
     * @return
     */
    public static String paste(Context context) {
// 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        return cmb.getText().toString().trim();
    }

}
