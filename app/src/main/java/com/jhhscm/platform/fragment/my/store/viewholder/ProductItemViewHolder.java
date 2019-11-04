package com.jhhscm.platform.fragment.my.store.viewholder;

import android.view.View;

import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemInviteBinding;
import com.jhhscm.platform.databinding.ItemStoreProductBinding;
import com.jhhscm.platform.fragment.home.bean.GetPageArticleListBean;

public class ProductItemViewHolder extends AbsRecyclerViewHolder<GetPageArticleListBean.DataBean> {

    private ItemStoreProductBinding mBinding;

    public ProductItemViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemStoreProductBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final GetPageArticleListBean.DataBean item) {

    }
}
