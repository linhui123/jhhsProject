package com.jhhscm.platform.fragment.my.store;

import android.view.View;

import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemStoreOrderGoodsBinding;
import com.jhhscm.platform.fragment.my.store.action.FindBusGoodsOwnerOrderListByUserCodeBean;

public class MyServiceGoodsViewHolder extends AbsRecyclerViewHolder<FindBusGoodsOwnerOrderListByUserCodeBean.DataBean.GoodsListBean> {

    private ItemStoreOrderGoodsBinding mBinding;

    public MyServiceGoodsViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemStoreOrderGoodsBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final FindBusGoodsOwnerOrderListByUserCodeBean.DataBean.GoodsListBean item) {
        if (item != null) {
            mBinding.name.setText(item.getGoods_name());
            mBinding.price.setText("￥" + item.getPrice());
        }
    }
}

