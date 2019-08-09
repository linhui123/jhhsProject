package com.jhhscm.platform.fragment.Mechanics.holder;

import android.view.View;

import com.jhhscm.platform.R;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemCompairsonSelectBinding;
import com.jhhscm.platform.databinding.ItemMechanicsBrandBinding;
import com.jhhscm.platform.event.BrandResultEvent;
import com.jhhscm.platform.event.CompMechanicsEvent;
import com.jhhscm.platform.event.FinishEvent;
import com.jhhscm.platform.fragment.Mechanics.bean.FindBrandBean;
import com.jhhscm.platform.fragment.Mechanics.bean.GetGoodsByBrandBean;
import com.jhhscm.platform.tool.EventBusUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MechanicsByBrandViewHolder extends AbsRecyclerViewHolder<GetGoodsByBrandBean.ResultBean.DataBean> {

    private ItemCompairsonSelectBinding mBinding;

    public MechanicsByBrandViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemCompairsonSelectBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final GetGoodsByBrandBean.ResultBean.DataBean item) {
        mBinding.tvTitle.setText(item.getName());
        mBinding.tvPrice.setText(item.getRetailPrice() + "");
        mBinding.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.tvSelect.setBackgroundResource(R.mipmap.ic_shoping_s1);
                EventBusUtil.post(new CompMechanicsEvent(item));
                EventBusUtil.post(new BrandResultEvent(item.getBrandId() + "", item.getId() + "", item.getName()));
//                EventBusUtil.post(new BrandResultEvent(item.getBrandId() + "", item.getId() + ""));
                EventBusUtil.post(new FinishEvent());
            }
        });
    }
}
