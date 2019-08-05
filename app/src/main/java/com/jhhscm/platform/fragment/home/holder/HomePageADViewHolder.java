package com.jhhscm.platform.fragment.home.holder;

import android.view.View;

import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.fragment.home.AdBean;
import com.jhhscm.platform.fragment.home.HomePageItem;
import com.jhhscm.platform.tool.ConfigUtils;
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
        final List<String> list = new ArrayList<>();
        for (AdBean.ResultBean adBean : item.adBean1.getResult()) {
            list.add(adBean.getUrl());
        }
//        list.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic1xjab4j30ci08cjrv.jpg");
        ImageLoader.getInstance().displayImage(list.get(0), mBinding.ivReport);

        mBinding.ivReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(itemView.getContext(), "HomepageAD");
//                if (ConfigUtils.getCurrentUser(itemView.getContext()) == null) {
//                    FillReport1Activity.start(itemView.getContext());
//                } else if (ConfigUtils.getCurrentUser(itemView.getContext()).getComplete().equals("1")) {
//                    String token = ConfigUtils.getCurrentUser(itemView.getContext()).getToken();
//                    String url = UrlUtils.ZNBG;
//                    url = url + "&token=" + token;
//                    H5Activity.start(itemView.getContext(), url, "头发智能报告");
//                } else if (ConfigUtils.getCurrentUser(itemView.getContext()).getComplete().equals("0")) {
//                    FillReport1Activity.start(itemView.getContext());
//                }
            }
        });

    }
}

