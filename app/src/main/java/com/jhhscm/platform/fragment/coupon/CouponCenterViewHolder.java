package com.jhhscm.platform.fragment.coupon;

import android.util.Log;
import android.view.View;

import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.ServiceRecordActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemCouponCenterBinding;
import com.jhhscm.platform.databinding.ItemCreateOrderBinding;
import com.jhhscm.platform.databinding.ItemStoreMemberBinding;
import com.jhhscm.platform.fragment.GoodsToCarts.GetCartGoodsByUserCodeBean;
import com.jhhscm.platform.fragment.home.bean.GetPageArticleListBean;
import com.jhhscm.platform.tool.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

public class CouponCenterViewHolder extends AbsRecyclerViewHolder<GetPageArticleListBean.DataBean> {

    private ItemCouponCenterBinding mBinding;

    public CouponCenterViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemCouponCenterBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final GetPageArticleListBean.DataBean item) {
        mBinding.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show(itemView.getContext(), "领取");
            }
        });

        if (getAdapterPosition() == 0) {
            mBinding.tvReceive.setBackgroundResource(R.drawable.button_c397);
            mBinding.tvReceive.setText("已领取");
            mBinding.tvReceive.setTextColor(itemView.getContext().getResources().getColor(R.color.white));
        } else {
            mBinding.tvReceive.setBackgroundResource(R.drawable.edit_bg_397);
            mBinding.tvReceive.setText("领取");
            mBinding.tvReceive.setTextColor(itemView.getContext().getResources().getColor(R.color.a397));
        }
    }
}

