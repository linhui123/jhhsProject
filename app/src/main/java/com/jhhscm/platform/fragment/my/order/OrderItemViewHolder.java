package com.jhhscm.platform.fragment.my.order;

import android.view.View;

import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemCreateOrderBinding;
import com.nostra13.universalimageloader.core.ImageLoader;

public class OrderItemViewHolder extends AbsRecyclerViewHolder<FindOrderListBean.DataBean.GoodsListBean> {

    private ItemCreateOrderBinding mBinding;

    public OrderItemViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemCreateOrderBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final FindOrderListBean.DataBean.GoodsListBean item) {
        mBinding.tvTitle.setText(item.getGoods_name());
        mBinding.tvNum.setText("×" + item.getNumber());
        mBinding.tvPrice.setText("￥" + item.getPrice());
        ImageLoader.getInstance().displayImage(item.getPic_url(), mBinding.im);
//        mBinding.rl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                if (item.getOrder_status().contains("10")) {
////                    OrderDetailActivity.start(itemView.getContext(), item.getOrderCode(), 1);
////                } else if (item.getOrder_status().contains("20")) {
////                    OrderDetailActivity.start(itemView.getContext(), item.getOrderCode(), 2);
////                } else if (item.getOrder_status().contains("30")) {
////                    OrderDetailActivity.start(itemView.getContext(), item.getOrderCode(), 3);
////                } else if (item.getOrder_status().contains("40")) {
////                    OrderDetailActivity.start(itemView.getContext(), item.getOrderCode(), 4);
////                }else {
////
////                }
//
//            }
//        });
    }
}

