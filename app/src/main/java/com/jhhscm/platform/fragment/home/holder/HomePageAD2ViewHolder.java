package com.jhhscm.platform.fragment.home.holder;

import android.view.View;

import com.google.gson.Gson;
import com.jhhscm.platform.activity.MemberShipActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemHomePageAdBinding;
import com.jhhscm.platform.event.JumpEvent;
import com.jhhscm.platform.fragment.home.AdBean;
import com.jhhscm.platform.fragment.home.HomePageItem;
import com.jhhscm.platform.tool.EventBusUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

public class HomePageAD2ViewHolder extends AbsRecyclerViewHolder<HomePageItem> {

    private ItemHomePageAdBinding mBinding;

    public HomePageAD2ViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemHomePageAdBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final HomePageItem item) {
        if (item.adBean5 != null) {//会员权益
            final List<String> list = new ArrayList<>();
            for (AdBean.ResultBean adBean : item.adBean5.getResult()) {
                list.add(adBean.getUrl());
            }
            if (list.size() > 0) {
                ImageLoader.getInstance().displayImage(list.get(0), mBinding.ivReport);
            }
            mBinding.ivReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MemberShipActivity.start(itemView.getContext());
                }
            });
        }
    }
}

