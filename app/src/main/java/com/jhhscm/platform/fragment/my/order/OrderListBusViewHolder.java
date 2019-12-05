package com.jhhscm.platform.fragment.my.order;

import android.view.View;

import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemOrderBusBinding;

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
    }
}
