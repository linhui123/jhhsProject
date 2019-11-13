package com.jhhscm.platform.fragment.my.order;

import android.view.View;

import com.jhhscm.platform.activity.OrderDetailActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemCreateOrderBinding;
import com.jhhscm.platform.fragment.GoodsToCarts.GetCartGoodsByUserCodeBean;
import com.jhhscm.platform.fragment.sale.FindOrderBean;
import com.nostra13.universalimageloader.core.ImageLoader;

public class OrderListViewHolder extends AbsRecyclerViewHolder<FindOrderBean.GoodsListBean> {

    private ItemCreateOrderBinding mBinding;

    public OrderListViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemCreateOrderBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final FindOrderBean.GoodsListBean item) {
        mBinding.tvTitle.setText(item.getGoodsName());
        mBinding.tvNum.setText("×" + item.getNumber());
        mBinding.tvPrice.setText("￥" + item.getPrice());
        ImageLoader.getInstance().displayImage(item.getPicUrl(), mBinding.im);
        mBinding.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (item.getOrder_status().contains("10")) {
                    OrderDetailActivity.start(itemView.getContext(), item.getOrderCode(), 1);
                } else if (item.getOrder_status().contains("20")) {
                    OrderDetailActivity.start(itemView.getContext(), item.getOrderCode(), 2);
                } else if (item.getOrder_status().contains("30")) {
                    OrderDetailActivity.start(itemView.getContext(), item.getOrderCode(), 3);
                } else if (item.getOrder_status().contains("40")) {
                    OrderDetailActivity.start(itemView.getContext(), item.getOrderCode(), 4);
                }else {

                }

//                if ("未付款".equals(item.getOrder_text())) {
//                    OrderDetailActivity.start(itemView.getContext(), item.getOrderCode(), 1);
//                } else if ("未发货".equals(item.getOrder_text())
//                        || "已付款".equals(item.getOrder_text())) {
//                    OrderDetailActivity.start(itemView.getContext(), item.getOrderCode(), 2);
//                } else if ("未收货".equals(item.getOrder_text())) {
//                    OrderDetailActivity.start(itemView.getContext(), item.getOrderCode(), 3);
//                } else if ("已完成".equals(item.getOrder_text())) {
//                    OrderDetailActivity.start(itemView.getContext(), item.getOrderCode(), 4);
//                }
            }
        });
    }
}
