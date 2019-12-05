package com.jhhscm.platform.fragment.my.store.viewholder;

import android.view.View;

import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemStoreOrderGoodsBinding;
import com.jhhscm.platform.fragment.my.store.action.FindBusOrderListBean;

public class MyStoreOrderGoodsViewHolder extends AbsRecyclerViewHolder<FindBusOrderListBean.DataBean.GoodsListBean> {

    private ItemStoreOrderGoodsBinding mBinding;

    public MyStoreOrderGoodsViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemStoreOrderGoodsBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final FindBusOrderListBean.DataBean.GoodsListBean item) {
        if (item != null) {
            mBinding.name.setText(item.getGoods_name());
            mBinding.price.setText("￥" + item.getPrice());
        }
    }
}
