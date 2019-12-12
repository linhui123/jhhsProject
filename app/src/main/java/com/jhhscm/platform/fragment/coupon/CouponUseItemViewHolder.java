package com.jhhscm.platform.fragment.coupon;

import android.view.View;

import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
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
        if (item.couponResult != null) {
            mBinding.tvCount.setText(item.couponResult.getDiscount() + "元");
            mBinding.tvName.setText(item.couponResult.getName());
            String data = "";
            if (item.couponResult.getStart_time() != null) {
                if (item.couponResult.getStart_time().length() > 10) {
                    data = item.couponResult.getStart_time().substring(0, 10) + " 至 ";
                } else {
                    data = item.couponResult.getStart_time() + " 至 ";
                }
            } else {
                data = "-- 至 ";
            }
            if (item.couponResult.getEnd_time() != null) {
                if (item.couponResult.getEnd_time().length() > 10) {
                    data = data + item.couponResult.getEnd_time().substring(0, 10);
                } else {
                    data = data + item.couponResult.getEnd_time();
                }
            } else {
                data = data + "--";
            }
            mBinding.tvData.setText(data);
            mBinding.tvCondition.setText(item.couponResult.getDesc());

            mBinding.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}

