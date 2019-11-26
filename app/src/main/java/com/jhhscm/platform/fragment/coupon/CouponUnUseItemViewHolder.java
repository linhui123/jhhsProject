package com.jhhscm.platform.fragment.coupon;

import android.view.View;

import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemCouponUnuseBinding;
import com.jhhscm.platform.fragment.sale.SaleItem;

public class CouponUnUseItemViewHolder extends AbsRecyclerViewHolder<SaleItem> {

    private ItemCouponUnuseBinding mBinding;

    public CouponUnUseItemViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemCouponUnuseBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final SaleItem item) {
        if (item.couponResult != null) {
            mBinding.tvCount.setText(item.couponResult.getDiscount() + "元");
            mBinding.tvName.setText(item.couponResult.getName());
            if (item.couponResult.getStart_time() != null && item.couponResult.getStart_time().length() > 10) {
                mBinding.tvData.setText(item.couponResult.getStart_time().substring(0, 10) + " 至 ");
            } else {
                mBinding.tvData.setText(item.couponResult.getStart_time() + " 至 ");
            }
            if (item.couponResult.getEnd_time() != null && item.couponResult.getEnd_time().length() > 10) {
                mBinding.tvData.append(item.couponResult.getEnd_time().substring(0, 10));
            } else {
                mBinding.tvData.append(item.couponResult.getEnd_time());
            }
            mBinding.tvCondition.setText(item.couponResult.getDesc());
            mBinding.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    new NewCouponDialog(itemView.getContext(), item.couponResult.getName(), item.couponResult.getDiscount() + "", item.couponResult.getDesc(), new NewCouponDialog.CallbackListener() {
//                        @Override
//                        public void clickYes() {
//
//                        }
//                    }).show();

                }
            });
        }
    }
}

