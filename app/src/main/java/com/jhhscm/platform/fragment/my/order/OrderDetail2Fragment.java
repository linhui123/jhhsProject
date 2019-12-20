package com.jhhscm.platform.fragment.my.order;


import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.CashierActivity;
import com.jhhscm.platform.activity.LoginActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.bean.PbImage;
import com.jhhscm.platform.databinding.FragmentOrderDetailBinding;
import com.jhhscm.platform.event.AddressRefreshEvent;
import com.jhhscm.platform.event.RefreshEvent;
import com.jhhscm.platform.fragment.GoodsToCarts.CreateOrderResultBean;
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
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.dialog.ConfirmOrderDialog;
import com.jhhscm.platform.views.recyclerview.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

/**
 * 维修订单
 */
public class OrderDetail2Fragment extends AbsFragment<FragmentOrderDetailBinding> {
    private UserSession userSession;
    private FindOrderListBean.DataBean findOrderBean;
    private int type;
    private String order_code;
    private String sign;//从店铺订单进来
    private InnerAdapter mAdapter;
    private InnerDeviceAdapter deviceAdapter;

    public static OrderDetail2Fragment instance() {
        OrderDetail2Fragment view = new OrderDetail2Fragment();
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
            getActivity().finish();
        }
        type = getArguments().getInt("type");
        sign = getArguments().getString("sign");
        order_code = getArguments().getString("orderGood");
        findOrderBean = (FindOrderListBean.DataBean) getArguments().getSerializable("dataBean");
        Log.e("type", "type :" + type);

        mDataBinding.rv.addItemDecoration(new DividerItemDecoration(getContext()));
        mDataBinding.rv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new InnerAdapter(getContext());
        mDataBinding.rv.setAdapter(mAdapter);

        mDataBinding.rvDevice.addItemDecoration(new DividerItemDecoration(getContext()));
        mDataBinding.rvDevice.setLayoutManager(new LinearLayoutManager(getContext()));
        deviceAdapter = new InnerDeviceAdapter(getContext());
        mDataBinding.rvDevice.setAdapter(deviceAdapter);

        if (findOrderBean != null) {
            intView(findOrderBean);
        } else if (sign != null && order_code != null) {
            findOrder3();
        } else {
            ToastUtil.show(getContext(), "数据错误");
            getActivity().finish();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
    }

    public void onEvent(RefreshEvent messageEvent) {

    }

    /**
     * 订单查询
     */
    private void findOrder3() {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("order_code", order_code);
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getContext(), map, "findOrderDetail");
        NetBean netBean = new NetBean();
        netBean.setToken(userSession.getToken());
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(FindOrderDetail3Action.newInstance(getContext(), netBean)
                .request(new AHttpService.IResCallback<BaseEntity<FindOrderListBean.OrderDetail>>() {
                    @Override
                    public void onCallback(int resultCode, Response<BaseEntity<FindOrderListBean.OrderDetail>> response,
                                           BaseErrorInfo baseErrorInfo) {
                        if (getView() != null) {
                            closeDialog();
                            if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                return;
                            }
                            if (response != null) {
                                new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                if (response.body().getCode().equals("200")) {
                                    findOrderBean = response.body().getData().getData();
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
     * 确认订单
     */
    private void updateOrderStatus(final String orderGood) {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("order_code", orderGood);
        String content = JSON.toJSONString(map);
        content = Des.encryptByDes(content);
        String sign = Sign.getSignKey(getContext(), map, "delOrder");
        NetBean netBean = new NetBean();
        netBean.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
        netBean.setSign(sign);
        netBean.setContent(content);
        onNewRequestCall(UpdateOrderStatusAction.newInstance(getContext(), netBean)
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
                                    if (response.body().getData().getData().equals("0")) {
                                        ToastUtil.show(getContext(), "操作成功");
                                        mDataBinding.orderType.setText("用户已确认");
                                        mDataBinding.tvConfirm.setVisibility(View.GONE);
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
        map.put("order_code", findOrderBean.getOrder_code());
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

    private void intView(final FindOrderListBean.DataBean findOrderBean) {
        mDataBinding.rlLocation.setVisibility(View.GONE);
        mDataBinding.rvDevice.setVisibility(View.VISIBLE);
        mAdapter.setData(findOrderBean.getGoodsList());
        deviceAdapter.setData(findOrderBean.getGoodsOwnerList());

        if (findOrderBean.getTicket() != null && findOrderBean.getTicket().length() > 10) {
            mDataBinding.isSchemeImage.setVisibility(View.VISIBLE);
            List<PbImage> items = new ArrayList<>();
            String ticket = findOrderBean.getTicket().replace("[", "").replace("]", "");
            String[] strs = ticket.split(",");
            if (strs.length > 0) {
                for (int i = 0; i < strs.length; i++) {
                    PbImage pbImage = new PbImage();
                    pbImage.setmUrl(strs[i].replace("\"", "").trim());
                    pbImage.setmToken(strs[i].replace("\"", "").trim());
                    items.add(pbImage);
                }
                mDataBinding.isSchemeImage.setPbImageList(items);
            }
        } else {
            mDataBinding.tvPiaoju.setVisibility(View.GONE);
            mDataBinding.isSchemeImage.setVisibility(View.GONE);
        }

        if (type == 1) {
            mDataBinding.orderType.setText(findOrderBean.getOrder_text());
            mDataBinding.tvInfo.setVisibility(View.GONE);
            mDataBinding.tvCancle.setVisibility(View.GONE);
            mDataBinding.tvConfirm.setVisibility(View.VISIBLE);
            mDataBinding.tvTijiao.setVisibility(View.VISIBLE);
            mDataBinding.rlWuliu.setVisibility(View.GONE);
            mDataBinding.rlWuliuNo.setVisibility(View.GONE);
            mDataBinding.tvIm.setBackgroundResource(R.mipmap.ic_order_1);
            if (findOrderBean.getOrder_status().equals("501")) {
                mDataBinding.tvConfirm.setVisibility(View.GONE);
            }
        } else if (type == 4) {
            mDataBinding.orderType.setText(findOrderBean.getOrder_text());
            mDataBinding.tvInfo.setVisibility(View.GONE);
            mDataBinding.rlWuliu.setVisibility(View.GONE);
            mDataBinding.rlWuliuNo.setVisibility(View.GONE);
            mDataBinding.tvIm.setBackgroundResource(R.mipmap.ic_order_4);
        }

        mDataBinding.tvPeijian.setText("￥" + findOrderBean.getGoods_price());
        mDataBinding.tvHour.setText("￥" + findOrderBean.getOther_price());
        if (findOrderBean.getCoupon_price() != null) {
            mDataBinding.tvYouhui.setText("-￥" + findOrderBean.getCoupon_price());
        } else {
            mDataBinding.rlCoupon.setVisibility(View.GONE);
        }

        mDataBinding.rlYunfei.setVisibility(View.GONE);
        mDataBinding.rlWuliu.setVisibility(View.GONE);
        mDataBinding.rlWuliuNo.setVisibility(View.GONE);
        mDataBinding.rlProduce.setVisibility(View.GONE);
        mDataBinding.tvTotal.setText("￥" + findOrderBean.getOrder_price());

        mDataBinding.tvOrderNo.setText(findOrderBean.getOrder_code());
        if (findOrderBean.getAdd_time() != null) {
            mDataBinding.tvOrderTime.setText(findOrderBean.getAdd_time());
        }

        mAdapter.setData(findOrderBean.getGoodsList());

        mDataBinding.tvOrderNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (findOrderBean.getOrder_code() != null) {
                    copy(findOrderBean.getOrder_code(), getContext());
                }
            }
        });

        mDataBinding.tvWuliuNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (findOrderBean.getShip_sn() != null) {
                    copy(findOrderBean.getShip_sn(), getContext());
                }
            }
        });

        mDataBinding.tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ConfirmOrderDialog(getContext(), new ConfirmOrderDialog.CallbackListener() {
                    @Override
                    public void clickY() {
                        updateOrderStatus(findOrderBean.getOrder_code());
                    }
                }).show();
            }
        });

        mDataBinding.tvTijiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CashierActivity.start(getContext(), new CreateOrderResultBean(new CreateOrderResultBean.DataBean(findOrderBean.getOrder_code())));
            }
        });
    }

    private class InnerAdapter extends AbsRecyclerViewAdapter<FindOrderListBean.DataBean.GoodsListBean> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<FindOrderListBean.DataBean.GoodsListBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new OrderItemViewHolder(mInflater.inflate(R.layout.item_create_order, parent, false));
        }
    }

    private class InnerDeviceAdapter extends AbsRecyclerViewAdapter<FindOrderListBean.DataBean.GoodsOwnerListBean> {
        public InnerDeviceAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<FindOrderListBean.DataBean.GoodsOwnerListBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new OrderItemDeviceViewHolder(mInflater.inflate(R.layout.item_order_device, parent, false));
        }
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
