package com.jhhscm.platform.fragment.Mechanics.holder;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.jhhscm.platform.activity.PeiJianActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemPeijianRightBinding;
import com.jhhscm.platform.fragment.Mechanics.adapter.PeiJianTypeAdapter;
import com.jhhscm.platform.fragment.Mechanics.bean.GoodsCatatoryListBean;
import com.nostra13.universalimageloader.core.ImageLoader;

public class PeiJianTypeViewHolder extends AbsRecyclerViewHolder<GoodsCatatoryListBean.DataBean> {

    private ItemPeijianRightBinding mBinding;

    public PeiJianTypeViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemPeijianRightBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final GoodsCatatoryListBean.DataBean item) {
        if (item != null) {
            ImageLoader.getInstance().displayImage(item.getPic_url(), mBinding.im);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(itemView.getContext(), 3, GridLayoutManager.VERTICAL, false);
            mBinding.rv.setLayoutManager(gridLayoutManager);
            PeiJianTypeAdapter adapter = new PeiJianTypeAdapter(item.getSecend_brand_list(), itemView.getContext());
            mBinding.rv.setAdapter(adapter);
            mBinding.rv.setNestedScrollingEnabled(false);
            adapter.setMyListener(new PeiJianTypeAdapter.ItemListener() {
                @Override
                public void onItemClick(GoodsCatatoryListBean.DataBean.SecendBrandListBean secendBrandListBean) {
                    PeiJianActivity.start(itemView.getContext(), item.getId(), secendBrandListBean.getId(), secendBrandListBean.getName());
                }
            });
        }
    }
}
