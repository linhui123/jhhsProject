package com.jhhscm.platform.fragment.aftersale;

import android.util.Log;
import android.view.View;

import com.jhhscm.platform.activity.ServiceRecordActivity;
import com.jhhscm.platform.activity.StoreDetailActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemAftersaleStoreBinding;
import com.jhhscm.platform.databinding.ItemCreateOrderBinding;
import com.jhhscm.platform.databinding.ItemStoreMemberBinding;
import com.jhhscm.platform.fragment.GoodsToCarts.GetCartGoodsByUserCodeBean;
import com.jhhscm.platform.fragment.home.bean.GetPageArticleListBean;
import com.jhhscm.platform.tool.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

public class AfterSaleViewHolder extends AbsRecyclerViewHolder<GetPageArticleListBean.DataBean> {

    private ItemAftersaleStoreBinding mBinding;

    public AfterSaleViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemAftersaleStoreBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final GetPageArticleListBean.DataBean item) {
        mBinding.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreDetailActivity.start(itemView.getContext());
            }
        });
    }
}

