package com.jhhscm.platform.fragment.Mechanics.holder;

import android.view.View;

import com.jhhscm.platform.activity.H5PeiJianActivity;
import com.jhhscm.platform.activity.MechanicsByBrandActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemMechanicsBrandBinding;
import com.jhhscm.platform.databinding.ItemMechanicsPeijianBinding;
import com.jhhscm.platform.event.BrandResultEvent;
import com.jhhscm.platform.event.FinishEvent;
import com.jhhscm.platform.fragment.Mechanics.bean.FindBrandBean;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.UrlUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

public class BrandViewHolder extends AbsRecyclerViewHolder<FindBrandBean.ResultBean> {
    private int type = 1;// 1 选择品牌； 2选择机型
    private ItemMechanicsBrandBinding mBinding;

    public BrandViewHolder(View itemView,int t) {
        super(itemView);
        type = t;
        mBinding = ItemMechanicsBrandBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final FindBrandBean.ResultBean item) {
        ImageLoader.getInstance().displayImage(item.getPic_url(), mBinding.im);
        mBinding.tvTitle.setText(item.getName());
        mBinding.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type==1){
                    EventBusUtil.post(new BrandResultEvent(item.getId(), item.getName()));
                    EventBusUtil.post(new FinishEvent());
                }else {
                    MechanicsByBrandActivity.start(itemView.getContext(), item.getId(),0);
                }
            }
        });
    }
}