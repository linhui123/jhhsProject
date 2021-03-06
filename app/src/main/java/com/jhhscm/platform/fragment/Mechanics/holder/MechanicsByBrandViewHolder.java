package com.jhhscm.platform.fragment.Mechanics.holder;

import android.view.View;

import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.h5.MechanicsH5Activity;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemCompairsonSelectBinding;
import com.jhhscm.platform.event.BrandResultEvent;
import com.jhhscm.platform.event.CompMechanicsEvent;
import com.jhhscm.platform.event.FinishEvent;
import com.jhhscm.platform.fragment.Mechanics.bean.GetGoodsByBrandBean;
import com.jhhscm.platform.tool.CalculationUtils;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.UrlUtils;

public class MechanicsByBrandViewHolder extends AbsRecyclerViewHolder<GetGoodsByBrandBean.ResultBean.DataBean> {

    private ItemCompairsonSelectBinding mBinding;
    private int type = 0;//0 返回；1进入详情；

    public MechanicsByBrandViewHolder(View itemView, int t) {
        super(itemView);
        this.type = t;
        mBinding = ItemCompairsonSelectBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final GetGoodsByBrandBean.ResultBean.DataBean item) {
        if (type == 1) {
            mBinding.tvSelect.setVisibility(View.INVISIBLE);
        }
        mBinding.tvTitle.setText(item.getName());
        mBinding.tvPrice.setText(CalculationUtils.wan1(item.getCounterPrice()));
        mBinding.tvDetail.setVisibility(View.GONE);
        mBinding.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 0) {
                    mBinding.tvSelect.setBackgroundResource(R.mipmap.ic_shoping_s1);
                    EventBusUtil.post(new CompMechanicsEvent(item));
                    EventBusUtil.post(new BrandResultEvent(item.getBrandId() + "", item.getId() + "", item.getName()));
                    EventBusUtil.post(new FinishEvent());
                } else {
                    String url = UrlUtils.XJXQ + "&good_code=" + item.getCode();
                    MechanicsH5Activity.start(itemView.getContext(), url, "新机详情", item.getCode(), item.getName(), item.getPicUrl(),1);
                }
            }
        });
    }

}
