package com.jhhscm.platform.fragment.coupon;

import android.view.View;

import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemCouponUnuseBinding;
import com.jhhscm.platform.databinding.ItemCouponUseBinding;
import com.jhhscm.platform.fragment.sale.SaleItem;

public class CouponUseItemViewHolder extends AbsRecyclerViewHolder<SaleItem> {

    private ItemCouponUseBinding mBinding;

    public CouponUseItemViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemCouponUseBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final SaleItem item) {
        if (item.orderBean != null) {


        }
    }
}

