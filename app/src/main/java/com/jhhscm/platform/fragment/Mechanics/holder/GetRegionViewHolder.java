package com.jhhscm.platform.fragment.Mechanics.holder;

import android.view.View;


import com.jhhscm.platform.activity.MechanicsH5Activity;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemMechanicsJxBinding;
import com.jhhscm.platform.databinding.ItemMechanicsPrivinceBinding;
import com.jhhscm.platform.event.ConsultationEvent;
import com.jhhscm.platform.event.GetRegionEvent;
import com.jhhscm.platform.fragment.Mechanics.bean.GetRegionBean;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.UrlUtils;


public class GetRegionViewHolder extends AbsRecyclerViewHolder<GetRegionBean.ResultBean> {

    private ItemMechanicsPrivinceBinding mBinding;

    public GetRegionViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemMechanicsPrivinceBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final GetRegionBean.ResultBean item) {
        mBinding.tvName.setText(item.getName());
        mBinding.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBusUtil.post(new GetRegionEvent(item.getId()+"",item.getType()+""));
            }
        });
    }
}

