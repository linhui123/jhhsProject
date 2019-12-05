package com.jhhscm.platform.fragment.GoodsToCarts;

import android.view.View;

import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemCreateOrderBinding;

public class CreateOrderViewHolder extends AbsRecyclerViewHolder<GetCartGoodsByUserCodeBean.ResultBean> {

    private ItemCreateOrderBinding mBinding;

    public CreateOrderViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemCreateOrderBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final GetCartGoodsByUserCodeBean.ResultBean item) {
//        mBinding.tvTitle.setText(item.getGoodsName());
//        Log.e("CreateOrderViewHolder","item.getNumber() "+item.getNumber());
//        mBinding.tvNum.setText("×" + item.getNumber());
//        mBinding.tvPrice.setText(item.getPrice());
//        ImageLoader.getInstance().displayImage(item.getPicUrl(), mBinding.im);
    }
}