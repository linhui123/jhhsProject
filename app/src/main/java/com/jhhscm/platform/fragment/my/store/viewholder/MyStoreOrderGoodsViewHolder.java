package com.jhhscm.platform.fragment.my.store.viewholder;

import android.view.View;

import com.jhhscm.platform.activity.OrderDetailActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemCreateOrderBinding;
import com.jhhscm.platform.databinding.ItemStoreOrderGoodsBinding;
import com.jhhscm.platform.fragment.sale.FindOrderBean;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MyStoreOrderGoodsViewHolder extends AbsRecyclerViewHolder<FindOrderBean.GoodsListBean> {

    private ItemStoreOrderGoodsBinding mBinding;

    public MyStoreOrderGoodsViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemStoreOrderGoodsBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final FindOrderBean.GoodsListBean item) {

    }
}
