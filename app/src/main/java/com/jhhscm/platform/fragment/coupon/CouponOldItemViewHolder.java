package com.jhhscm.platform.fragment.coupon;

import android.view.View;

import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemCouponOldBinding;
import com.jhhscm.platform.fragment.sale.SaleItem;

public class CouponOldItemViewHolder extends AbsRecyclerViewHolder<SaleItem> {

    private ItemCouponOldBinding mBinding;

    public CouponOldItemViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemCouponOldBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final SaleItem item) {
        if (item.orderBean != null) {


        }
    }
}