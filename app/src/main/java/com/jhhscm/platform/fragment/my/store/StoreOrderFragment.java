package com.jhhscm.platform.fragment.my.store;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.jhhscm.platform.R;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.FragmentMyStoreOrderBinding;
import com.jhhscm.platform.databinding.FragmentStoreOrderBinding;
import com.jhhscm.platform.event.OrderCancleEvent;
import com.jhhscm.platform.fragment.base.AbsFragment;
import com.jhhscm.platform.fragment.home.action.GetArticleListAction;
import com.jhhscm.platform.fragment.home.bean.GetPageArticleListBean;
import com.jhhscm.platform.fragment.my.order.DelOrderAction;
import com.jhhscm.platform.fragment.my.store.action.FindBusGoodsOwnerListByUserCodeBean;
import com.jhhscm.platform.fragment.my.store.action.FindBusGoodsOwnerOrderListByUserCodeAction;
import com.jhhscm.platform.fragment.my.store.action.FindBusGoodsOwnerOrderListByUserCodeBean;
import com.jhhscm.platform.fragment.my.store.action.FindBusOrderListAction;
import com.jhhscm.platform.fragment.my.store.action.FindBusOrderListBean;
import com.jhhscm.platform.fragment.my.store.viewholder.StoreOrderItemViewHolder;
import com.jhhscm.platform.http.AHttpService;
import com.jhhscm.platform.http.HttpHelper;
import com.jhhscm.platform.http.bean.BaseEntity;
import com.jhhscm.platform.http.bean.BaseErrorInfo;
import com.jhhscm.platform.http.bean.NetBean;
import com.jhhscm.platform.http.bean.ResultBean;
import com.jhhscm.platform.http.sign.Sign;
import com.jhhscm.platform.http.sign.SignObject;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.Des;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtil;
import com.jhhscm.platform.tool.ToastUtils;
import com.jhhscm.platform.views.dialog.ConfirmOrderDialog;
import com.jhhscm.platform.views.recyclerview.WrappedRecyclerView;

import java.util.Map;
import java.util.TreeMap;

import retrofit2.Response;

/**
 * 服务记录-订单列表
 */
public class StoreOrderFragment extends AbsFragment<FragmentStoreOrderBinding> {

    private InnerAdapter mAdapter;
    private int mShowCount = 10;
    private int mCurrentPage = 1;
    private final int START_PAGE = mCurrentPage;
    private String type = "";
    private String bus_code = "";
    private FindBusGoodsOwnerListByUserCodeBean.ResultBean.DataBean goods_code;

    public static StoreOrderFragment instance() {
        StoreOrderFragment view = new StoreOrderFragment();
        return view;
    }

    @Override
    protected FragmentStoreOrderBinding bindRootView(LayoutInflater inflater, ViewGroup container, boolean attachToRoot) {
        return FragmentStoreOrderBinding.inflate(inflater, container, attachToRoot);
    }


    @Override
    protected void setupViews() {
        EventBusUtil.registerEvent(this);
        bus_code = getArguments().getString("bus_code");
        goods_code = (FindBusGoodsOwnerListByUserCodeBean.ResultBean.DataBean) getArguments().getSerializable("goods_code");
        if (goods_code != null) {
            mDataBinding.tvBrand.setText(goods_code.getBrand_name());
            mDataBinding.no.setText(goods_code.getGoodsnum());
            mDataBinding.gpsNo.setText(goods_code.getGpsnum());
            mDataBinding.model.setText(goods_code.getFix_p_17());

            mDataBinding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
            mAdapter = new InnerAdapter(getContext());
            mDataBinding.recyclerview.setAdapter(mAdapter);
            mDataBinding.recyclerview.autoRefresh();
            mDataBinding.recyclerview.setOnPullListener(new WrappedRecyclerView.OnPullListener() {
                @Override
                public void onRefresh(RecyclerView view) {
                    findBusGoodsOwnerOrderListByUserCode(true);
                }

                @Override
                public void onLoadMore(RecyclerView view) {
                    findBusGoodsOwnerOrderListByUserCode(false);
                }
            });
        }


        mDataBinding.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "";
                mDataBinding.button1.setTextColor(getResources().getColor(R.color.white));
                mDataBinding.button1.setBackgroundResource(R.drawable.button_c397);
                mDataBinding.button2.setTextColor(getResources().getColor(R.color.acc9));
                mDataBinding.button2.setBackgroundResource(R.drawable.bg_line_de);
                mDataBinding.button3.setTextColor(getResources().getColor(R.color.acc9));
                mDataBinding.button3.setBackgroundResource(R.drawable.bg_line_de);
                mDataBinding.recyclerview.autoRefresh();
            }
        });

        mDataBinding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "";
                mDataBinding.button1.setTextColor(getResources().getColor(R.color.acc9));
                mDataBinding.button1.setBackgroundResource(R.drawable.bg_line_de);
                mDataBinding.button2.setTextColor(getResources().getColor(R.color.white));
                mDataBinding.button2.setBackgroundResource(R.drawable.button_c397);
                mDataBinding.button3.setTextColor(getResources().getColor(R.color.acc9));
                mDataBinding.button3.setBackgroundResource(R.drawable.bg_line_de);
                mDataBinding.recyclerview.autoRefresh();
            }
        });

        mDataBinding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "";
                mDataBinding.button1.setTextColor(getResources().getColor(R.color.acc9));
                mDataBinding.button1.setBackgroundResource(R.drawable.bg_line_de);
                mDataBinding.button2.setTextColor(getResources().getColor(R.color.acc9));
                mDataBinding.button2.setBackgroundResource(R.drawable.bg_line_de);
                mDataBinding.button3.setTextColor(getResources().getColor(R.color.white));
                mDataBinding.button3.setBackgroundResource(R.drawable.button_c397);
                mDataBinding.recyclerview.autoRefresh();
            }
        });
    }

    private void findBusGoodsOwnerOrderListByUserCode(final boolean refresh) {
        if (getContext() != null) {
            mCurrentPage = refresh ? START_PAGE : ++mCurrentPage;
            Map<String, Object> map = new TreeMap<String, Object>();
            map.put("page", mCurrentPage);
            map.put("limit", mShowCount);
            map.put("bus_code", ConfigUtils.getCurrentUser(getContext()).getUserCode());
            map.put("user_code", bus_code);
            map.put("goods_code", goods_code.getGoods_code());
            map.put("order_status", type);
            String content = JSON.toJSONString(map);
            content = Des.encryptByDes(content);
            String sign = SignObject.getSignKey(getActivity(), map, "findBusGoodsOwnerOrderListByUserCode");
            NetBean netBean = new NetBean();
            netBean.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
            netBean.setSign(sign);
            netBean.setContent(content);
            onNewRequestCall(FindBusGoodsOwnerOrderListByUserCodeAction.newInstance(getContext(), netBean)
                    .request(new AHttpService.IResCallback<BaseEntity<FindBusGoodsOwnerOrderListByUserCodeBean>>() {
                        @Override
                        public void onCallback(int resultCode, Response<BaseEntity<FindBusGoodsOwnerOrderListByUserCodeBean>> response,
                                               BaseErrorInfo baseErrorInfo) {
                            if (getView() != null) {
                                closeDialog();
                                if (new HttpHelper().showError(getContext(), resultCode, baseErrorInfo, getString(R.string.error_net))) {
                                    return;
                                }
                                if (response != null) {
                                    new HttpHelper().showError(getContext(), response.body().getCode(), response.body().getMessage());
                                    if (response.body().getCode().equals("200")) {
                                        initView(refresh, response.body().getData());
                                    } else {
                                        ToastUtils.show(getContext(), response.body().getMessage());
                                    }
                                }
                            }
                        }
                    }));
        }
    }

    FindBusGoodsOwnerOrderListByUserCodeBean getPushListBean;

    private void initView(boolean refresh, FindBusGoodsOwnerOrderListByUserCodeBean pushListBean) {
        this.getPushListBean = pushListBean;
        if (refresh) {
            mAdapter.setData(pushListBean.getData());
        } else {
            mAdapter.append(pushListBean.getData());
        }
//        mDataBinding.recyclerview.loadComplete(true, false);
        mDataBinding.recyclerview.loadComplete(mAdapter.getItemCount() == 0,
                ((float) getPushListBean.getPage().getTotal() / (float) getPushListBean.getPage().getPageSize()) > mCurrentPage);
    }


    private class InnerAdapter extends AbsRecyclerViewAdapter<FindBusGoodsOwnerOrderListByUserCodeBean.DataBean> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<FindBusGoodsOwnerOrderListByUserCodeBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new StoreOrderItemViewHolder(mInflater.inflate(R.layout.item_store_order, parent, false));
        }
    }

    public void onEvent(final OrderCancleEvent event) {
        if (event.order_code != null) {
            new ConfirmOrderDialog(getContext(), "请确认是否取消订单", new ConfirmOrderDialog.CallbackListener() {
                @Override
                public void clickY() {
                    delOrder(event.order_code);
                }
            }).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregisterEvent(this);
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
        netBean.setToken(ConfigUtils.getCurrentUser(getContext()).getToken());
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
                                    if (response.body().getData().getData().equals("0")) {
                                        ToastUtil.show(getContext(), "取消成功");
                                    }
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
