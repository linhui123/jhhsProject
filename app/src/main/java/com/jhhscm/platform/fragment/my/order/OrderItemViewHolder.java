package com.jhhscm.platform.fragment.my.order;

import android.view.View;

import com.jhhscm.platform.activity.h5.H5PeiJianActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemCreateOrderBinding;
import com.jhhscm.platform.tool.UrlUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

public class OrderItemViewHolder extends AbsRecyclerViewHolder<FindOrderListBean.DataBean.GoodsListBean> {

    private ItemCreateOrderBinding mBinding;

    public OrderItemViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemCreateOrderBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final FindOrderListBean.DataBean.GoodsListBean item) {
        mBinding.tvTitle.setText(item.getGoods_name());
        mBinding.tvNum.setText("×" + item.getNumber());
        mBinding.tvPrice.setText("￥" + item.getPrice());
        ImageLoader.getInstance().displayImage(item.getPic_url(), mBinding.im);
        mBinding.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String url = UrlUtils.PJXQ + "&good_code=" + item.get();
//                H5PeiJianActivity.start(itemView.getContext(), url, "配件详情", "", "",
//                        item.getGoodsName(), item.getGoodsCode(),
//                        item.getPicUrl(), item.getPicUrl(), item.getNumber() + "", 3);

            }
        });
    }
}

