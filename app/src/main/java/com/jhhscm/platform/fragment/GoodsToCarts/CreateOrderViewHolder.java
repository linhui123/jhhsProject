package com.jhhscm.platform.fragment.GoodsToCarts;

import android.util.Log;
import android.view.View;

import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemCreateOrderBinding;
import com.jhhscm.platform.databinding.ItemMechanicsPrivinceBinding;
import com.jhhscm.platform.event.GetRegionEvent;
import com.jhhscm.platform.fragment.Mechanics.bean.GetRegionBean;
import com.jhhscm.platform.tool.EventBusUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

public class CreateOrderViewHolder extends AbsRecyclerViewHolder<GetCartGoodsByUserCodeBean.ResultBean> {

    private ItemCreateOrderBinding mBinding;

    public CreateOrderViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemCreateOrderBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final GetCartGoodsByUserCodeBean.ResultBean item) {
        mBinding.tvTitle.setText(item.getGoodsName());
        Log.e("CreateOrderViewHolder","item.getNumber() "+item.getNumber());
        mBinding.tvNum.setText("×" + item.getNumber());
        mBinding.tvPrice.setText(item.getPrice());
        ImageLoader.getInstance().displayImage(item.getPicUrl(), mBinding.im);
    }
}