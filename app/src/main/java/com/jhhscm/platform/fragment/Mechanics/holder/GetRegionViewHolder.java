package com.jhhscm.platform.fragment.Mechanics.holder;

import android.view.View;

import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemMechanicsPrivinceBinding;
import com.jhhscm.platform.event.GetRegionEvent;
import com.jhhscm.platform.fragment.Mechanics.bean.GetRegionBean;
import com.jhhscm.platform.tool.EventBusUtil;


public class GetRegionViewHolder extends AbsRecyclerViewHolder<GetRegionBean.ResultBean> {

    private ItemMechanicsPrivinceBinding mBinding;
    private int type = 0;//0二手机列表；1劳务招聘列表；2  劳务求职列表；3 售后列表

    public GetRegionViewHolder(View itemView, int type) {
        super(itemView);
        this.type = type;
        mBinding = ItemMechanicsPrivinceBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final GetRegionBean.ResultBean item) {
        mBinding.tvName.setText(item.getName());
        mBinding.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBusUtil.post(new GetRegionEvent(item.getId() + "", item.getName(), item.getType() + "", type));
            }
        });
    }
}

