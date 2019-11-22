package com.jhhscm.platform.fragment.my.order;

import android.view.View;

import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemOrderBusBinding;
import com.jhhscm.platform.databinding.ItemOrderDeviceBinding;

public class OrderItemDeviceViewHolder extends AbsRecyclerViewHolder<FindOrderListBean.DataBean.GoodsOwnerListBean> {

    private ItemOrderDeviceBinding mBinding;

    public OrderItemDeviceViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemOrderDeviceBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final FindOrderListBean.DataBean.GoodsOwnerListBean item) {
        mBinding.tvBrand.setText(item.getBrands());
        mBinding.model.setText(item.getFixs());
        mBinding.no.setText(item.getV_1());
        mBinding.gpsNo.setText(item.getV_2());
        mBinding.error.setText(item.getV3s());
    }
}
