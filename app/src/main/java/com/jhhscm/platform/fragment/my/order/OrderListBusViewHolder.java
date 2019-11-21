package com.jhhscm.platform.fragment.my.order;

import android.view.View;

import com.jhhscm.platform.activity.OrderDetailActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemCreateOrderBinding;
import com.jhhscm.platform.databinding.ItemOrderBusBinding;
import com.jhhscm.platform.fragment.sale.FindOrderBean;
import com.nostra13.universalimageloader.core.ImageLoader;

public class OrderListBusViewHolder extends AbsRecyclerViewHolder<FindOrderListBean.DataBean.GoodsOwnerListBean> {

    private ItemOrderBusBinding mBinding;

    public OrderListBusViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemOrderBusBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final FindOrderListBean.DataBean.GoodsOwnerListBean item) {
        mBinding.tvBrand.setText("设备品牌：" + item.getBrands());
        mBinding.tvModel.setText("设备型号：" + item.getFixs());
        mBinding.tvError.setText("故障类型：" + item.getV3s());
//        ImageLoader.getInstance().displayImage(item.getPic_url(), mBinding.im);

//        mBinding.rl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (item.getOrder_status().contains("10")) {
//                    OrderDetailActivity.start(itemView.getContext(), item.getOrderCode(), 1);
//                } else if (item.getOrder_status().contains("20")) {
//                    OrderDetailActivity.start(itemView.getContext(), item.getOrderCode(), 2);
//                } else if (item.getOrder_status().contains("30")) {
//                    OrderDetailActivity.start(itemView.getContext(), item.getOrderCode(), 3);
//                } else if (item.getOrder_status().contains("40")) {
//                    OrderDetailActivity.start(itemView.getContext(), item.getOrderCode(), 4);
//                }else {
//
//                }
//            }
//        });
    }
}
