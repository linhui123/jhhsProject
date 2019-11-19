package com.jhhscm.platform.fragment.my.store.viewholder;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.jhhscm.platform.R;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemStoreOrderBinding;
import com.jhhscm.platform.fragment.my.store.action.FindBusGoodsOwnerOrderListByUserCodeBean;
import com.jhhscm.platform.fragment.my.store.action.FindBusOrderListBean;
import com.jhhscm.platform.fragment.sale.FindOrderBean;
import com.jhhscm.platform.tool.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class MyStoreOrderItemViewHolder extends AbsRecyclerViewHolder<FindBusOrderListBean.DataBean> {

    private ItemStoreOrderBinding mBinding;

    public MyStoreOrderItemViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemStoreOrderBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final FindBusOrderListBean.DataBean item) {
        if (item != null) {
            mBinding.orderNo.setText("订单编号：" + item.getOrder_code());
            mBinding.orderTime.setText("下单时间：" + item.getAdd_time());
            mBinding.orderType.setText(item.getOrder_status() + "");
            mBinding.fee.setText("￥" + item.getOther_price());
            mBinding.total.setText("￥" + item.getOrder_price());
            mBinding.phone.setText(item.getUser_name());
            mBinding.name.setText(item.getUser_name());

            mBinding.recyclerview.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            InnerAdapter mAdapter = new InnerAdapter(itemView.getContext());
            mBinding.recyclerview.setAdapter(mAdapter);
            mAdapter.setData(item.getGoodsList());

            mBinding.tvFunc1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.show(itemView.getContext(), "取消订单");
                }
            });
            mBinding.tvFunc2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.show(itemView.getContext(), "修改订单");
                }
            });
        }
    }

    private class InnerAdapter extends AbsRecyclerViewAdapter<FindBusOrderListBean.DataBean.GoodsListBean> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<FindBusOrderListBean.DataBean.GoodsListBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyStoreOrderGoodsViewHolder(mInflater.inflate(R.layout.item_store_order_goods, parent, false));
        }
    }
}
