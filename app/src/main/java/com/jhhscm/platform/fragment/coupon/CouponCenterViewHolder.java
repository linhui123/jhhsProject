package com.jhhscm.platform.fragment.coupon;

import android.view.View;

import com.jhhscm.platform.R;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemCouponCenterBinding;
import com.jhhscm.platform.event.GetCouponEvent;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.StringUtils;

public class CouponCenterViewHolder extends AbsRecyclerViewHolder<CouponGetListBean.DataBean> {

    private ItemCouponCenterBinding mBinding;

    public CouponCenterViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemCouponCenterBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final CouponGetListBean.DataBean item) {
        if (item != null) {
            if (item.getDiscount() < 1) {
                mBinding.tvCount.setText(item.getDiscount() * 10 + "折");
                mBinding.tvCount1.setText(item.getDiscount() * 10 + "折");
            } else {
                mBinding.tvCount.setText(StringUtils.replace(item.getDiscount())+ "元");
                mBinding.tvCount1.setText(StringUtils.replace(item.getDiscount()) + "元");
            }

            mBinding.tvName.setText(item.getName());
            mBinding.tvName1.setText(item.getName());
            String data = "";
            if (item.getStartTime() != null) {
                if (item.getStartTime().length() > 10) {
                    data = item.getStartTime().substring(0, 10) + " 至 ";
                } else {
                    data = item.getStartTime() + " 至 ";
                }
            } else {
                data = "-- 至 ";
            }
            if (item.getEndTime() != null) {
                if (item.getEndTime().length() > 10) {
                    data = data + item.getEndTime().substring(0, 10);
                } else {
                    data = data + item.getEndTime();
                }
            } else {
                data = data + "--";
            }
            mBinding.tvData.setText(data);
            mBinding.tvData1.setText(data);
            mBinding.tvCondition.setText(item.getDesc());
            mBinding.tvCondition1.setText(item.getDesc());

            if (item.getIsGetAll().equals("1")) {
                mBinding.ll1.setVisibility(View.VISIBLE);
                mBinding.ll.setVisibility(View.GONE);
            } else {
                mBinding.ll.setVisibility(View.VISIBLE);
                mBinding.ll1.setVisibility(View.GONE);
                if (item.getIsGet().equals("1")) {
                    mBinding.tvReceive.setBackgroundResource(R.drawable.button_c397);
                    mBinding.tvReceive.setText("已领取");
                    mBinding.tvReceive.setTextColor(itemView.getContext().getResources().getColor(R.color.white));
                } else {
                    mBinding.tvReceive.setBackgroundResource(R.drawable.edit_bg_397);
                    mBinding.tvReceive.setText("领取");
                    mBinding.tvReceive.setTextColor(itemView.getContext().getResources().getColor(R.color.a397));
                }
            }
            mBinding.tvReceive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item.getIsGet().equals("0")) {
                        EventBusUtil.post(new GetCouponEvent(item.getCode(), item.getStartTime(), item.getEndTime(), 1));
                    }
                }
            });
        }
    }
}

