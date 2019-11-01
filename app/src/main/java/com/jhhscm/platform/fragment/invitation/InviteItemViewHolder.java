package com.jhhscm.platform.fragment.invitation;

import android.util.Log;
import android.view.View;

import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemCreateOrderBinding;
import com.jhhscm.platform.databinding.ItemInviteBinding;
import com.jhhscm.platform.fragment.GoodsToCarts.GetCartGoodsByUserCodeBean;
import com.jhhscm.platform.fragment.home.bean.GetPageArticleListBean;
import com.nostra13.universalimageloader.core.ImageLoader;

public class InviteItemViewHolder extends AbsRecyclerViewHolder<GetPageArticleListBean.DataBean> {

    private ItemInviteBinding mBinding;

    public InviteItemViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemInviteBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final GetPageArticleListBean.DataBean item) {

    }
}