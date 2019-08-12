package com.jhhscm.platform.fragment.home.holder;

import android.view.View;

import com.google.gson.Gson;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemHomePageAcBinding;
import com.jhhscm.platform.databinding.ItemHomePageAdBinding;
import com.jhhscm.platform.event.JumpEvent;
import com.jhhscm.platform.fragment.home.AdBean;
import com.jhhscm.platform.fragment.home.HomePageItem;
import com.jhhscm.platform.tool.EventBusUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class HomePageACViewHolder extends AbsRecyclerViewHolder<HomePageItem> {

    private ItemHomePageAcBinding mBinding;

    public HomePageACViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemHomePageAcBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final HomePageItem item) {
        mBinding.ivReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBusUtil.post(new JumpEvent("GOLD", new AdBean.ResultBean()));
            }
        });
    }
}


