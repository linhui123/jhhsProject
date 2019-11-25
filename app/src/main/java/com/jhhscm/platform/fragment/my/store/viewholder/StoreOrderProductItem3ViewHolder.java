package com.jhhscm.platform.fragment.my.store.viewholder;

import android.view.View;

import com.jhhscm.platform.R;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemStoreOrderProductBinding;
import com.jhhscm.platform.databinding.ItemStoreOrderSubmit3Binding;
import com.jhhscm.platform.event.RefreshEvent;
import com.jhhscm.platform.fragment.my.store.action.BusinessFindcategorybyBuscodeBean;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtil;

public class StoreOrderProductItem3ViewHolder extends AbsRecyclerViewHolder<BusinessFindcategorybyBuscodeBean.DataBean> {

    private ItemStoreOrderSubmit3Binding mBinding;

    public StoreOrderProductItem3ViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemStoreOrderSubmit3Binding.bind(itemView);
    }

    @Override
    protected void onBindView(final BusinessFindcategorybyBuscodeBean.DataBean item) {
        mBinding.name.setText(item.getName());
        mBinding.brand.setText(item.getBrandName());
        mBinding.tvNum.setText("×" + item.getNum());
        if (item.getCategoryName() != null) {
            mBinding.type.setText("类型：" + item.getCategoryName());
        }
        mBinding.price.setText("品牌：" + item.getCounter_price() + "");
    }
}