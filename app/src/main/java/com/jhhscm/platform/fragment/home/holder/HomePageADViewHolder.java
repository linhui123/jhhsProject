package com.jhhscm.platform.fragment.home.holder;

import android.view.View;

import com.google.gson.Gson;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.event.JumpEvent;
import com.jhhscm.platform.fragment.home.AdBean;
import com.jhhscm.platform.fragment.home.HomePageItem;
import com.jhhscm.platform.tool.ConfigUtils;
import com.jhhscm.platform.tool.EventBusUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;
import com.jhhscm.platform.databinding.ItemHomePageAdBinding;

import java.util.ArrayList;
import java.util.List;

public class HomePageADViewHolder extends AbsRecyclerViewHolder<HomePageItem> {

    private ItemHomePageAdBinding mBinding;

    public HomePageADViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemHomePageAdBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final HomePageItem item) {
        if (item.adBean1 != null) {
            final List<String> list = new ArrayList<>();
            for (AdBean.ResultBean adBean : item.adBean1.getResult()) {
                list.add(adBean.getUrl());
            }
//        list.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic1xjab4j30ci08cjrv.jpg");
            if (list.size() > 0) {
                ImageLoader.getInstance().displayImage(list.get(0), mBinding.ivReport);
            }

            mBinding.ivReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (list.size() > 0) {
                        AdBean adBean = item.adBean1;
                        AdBean.ResultBean resultBean = adBean.getResult().get(0);
                        Gson gson = new Gson();
                        AdBean.DataBean findOrderBean = gson.fromJson(resultBean.getContent(), AdBean.DataBean.class);
                        EventBusUtil.post(new JumpEvent(findOrderBean.getTYPE(), resultBean));
                    }
                }
            });
        }
    }
}

