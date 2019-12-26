package com.jhhscm.platform.fragment.coupon;

import android.view.View;

import com.jhhscm.platform.R;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemSelectCouponBinding;
import com.jhhscm.platform.event.SelectCouponEvent;
import com.jhhscm.platform.tool.EventBusUtil;

public class SelectCouponViewHolder extends AbsRecyclerViewHolder<CouponListBean.ResultBean> {

    private ItemSelectCouponBinding mBinding;

    public SelectCouponViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemSelectCouponBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final CouponListBean.ResultBean item) {
        if (item != null) {
            if (item.isSelect()) {
                mBinding.tvSelect.setImageResource(R.mipmap.ic_shoping_s1);
            } else {
                mBinding.tvSelect.setImageResource(R.mipmap.ic_shoping_s);
            }
            if (item.getDiscount() < 1) {
                mBinding.tvCount.setText(item.getDiscount() * 10 + "折");
            } else {
                mBinding.tvCount.setText(item.getDiscount() + "元");
            }

            mBinding.tvName.setText(item.getName());
            String data = "";
            if (item.getStart_time() != null) {
                if (item.getStart_time().length() > 10) {
                    data = item.getStart_time().substring(0, 10) + " 至 ";
                } else {
                    data = item.getStart_time() + " 至 ";
                }
            } else {
                data = "-- 至 ";
            }
            if (item.getEnd_time() != null) {
                if (item.getEnd_time().length() > 10) {
                    data = data + item.getEnd_time().substring(0, 10);
                } else {
                    data = data + item.getEnd_time();
                }
            } else {
                data = data + "--";
            }
            mBinding.tvData.setText(data);
            mBinding.tvCondition.setText(item.getDesc());
            mBinding.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item.isSelect()) {
                        item.setSelect(false);
                        mBinding.tvSelect.setImageResource(R.mipmap.ic_shoping_s);
                    } else {
                        item.setSelect(true);
                        mBinding.tvSelect.setImageResource(R.mipmap.ic_shoping_s1);
                        EventBusUtil.post(new SelectCouponEvent(item, 0));
                    }
                }
            });
        }
    }
}
