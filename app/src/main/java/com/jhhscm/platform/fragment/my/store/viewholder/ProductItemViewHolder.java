package com.jhhscm.platform.fragment.my.store.viewholder;

import android.view.View;

import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemStoreProductBinding;
import com.jhhscm.platform.fragment.my.store.action.BusinessFindcategorybyBuscodeBean;

public class ProductItemViewHolder extends AbsRecyclerViewHolder<BusinessFindcategorybyBuscodeBean.DataBean> {

    private ItemStoreProductBinding mBinding;

    public ProductItemViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemStoreProductBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final BusinessFindcategorybyBuscodeBean.DataBean item) {
        if (item != null) {
            mBinding.name.setText(item.getName());
            mBinding.brand.setText("品牌：" + item.getBrandName());
            if (item.getCategoryName()!=null){
                mBinding.type.setText("类型：" + item.getCategoryName());
            }

            mBinding.price.setText(item.getCounter_price() + "");
        }
    }
}
