package com.jhhscm.platform.fragment.GoodsToCarts;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.OrderDetailActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemCreateOrderBinding;
import com.jhhscm.platform.databinding.ItemCreateOrderListBinding;
import com.jhhscm.platform.databinding.ItemOrderStatus1Binding;
import com.jhhscm.platform.event.OrderCancleEvent;
import com.jhhscm.platform.event.PayEvent;
import com.jhhscm.platform.fragment.my.order.OrderListViewHolder;
import com.jhhscm.platform.fragment.my.order.OrderStaus1ViewHolder;
import com.jhhscm.platform.fragment.sale.FindOrderBean;
import com.jhhscm.platform.fragment.sale.SaleItem;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.views.dialog.ConfirmOrderDialog;
import com.jhhscm.platform.views.dialog.LogisticsDialog;
import com.jhhscm.platform.views.recyclerview.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class CreateOrderItemViewHolder extends AbsRecyclerViewHolder<GetCartGoodsByUserCodeBean.ResultBean> {

    private ItemCreateOrderListBinding mBinding;
    private String type = "";

    public CreateOrderItemViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemCreateOrderListBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final GetCartGoodsByUserCodeBean.ResultBean item) {
        if (item != null) {
            mBinding.storeCoupon.setText("暂无优惠券");
            mBinding.yunfei.setText("￥ 0.00");
            mBinding.total.setText("￥ "+item.getPrice());
            mBinding.rv.addItemDecoration(new DividerItemDecoration(itemView.getContext()));
            mBinding.rv.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            InnerAdapter mAdapter = new InnerAdapter(itemView.getContext());
            mBinding.rv.setAdapter(mAdapter);
            List<FindOrderBean.GoodsListBean> listBeans=new ArrayList<>();
            FindOrderBean.GoodsListBean goodsListBean = new FindOrderBean.GoodsListBean();
            goodsListBean.setGoodsName(item.getGoodsName());
            goodsListBean.setNumber(item.getNumber());
            goodsListBean.setPrice(item.getPrice());
            goodsListBean.setPicUrl(item.getPicUrl());
            listBeans.add(goodsListBean);
            listBeans.add(goodsListBean);
            mAdapter.setData(listBeans);
        }
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
}
