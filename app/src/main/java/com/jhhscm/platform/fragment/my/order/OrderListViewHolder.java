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
                OrderDetailActivity.start(itemView.getContext(), item.getOrderCode(), 1);
            }
        });
    }
}
