package com.jhhscm.platform.fragment.home.holder;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.MainActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.fragment.home.AdBean;
import com.jhhscm.platform.fragment.home.HomePageItem;

import com.jhhscm.platform.databinding.ItemHomePageBusinessBinding;
import com.jhhscm.platform.views.dlflipviewpage.bean.DLGridViewBean;
import com.jhhscm.platform.views.dlflipviewpage.utils.DLVPSetting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomePageBusinessViewHolder extends AbsRecyclerViewHolder<HomePageItem> {

    private ItemHomePageBusinessBinding mBinding;
    private List<DLGridViewBean> dlGridViewBeans;
    private DLVPSetting setting;

    public HomePageBusinessViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemHomePageBusinessBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final HomePageItem item) {
        if (item.adBean3.getResult().size() < 9) {
            mBinding.indicator.setVisibility(View.GONE);
        }
        dlGridViewBeans = new ArrayList<>();
        for (AdBean.ResultBean resultBean : item.adBean3.getResult()) {
            DLGridViewBean dlGridViewBean = new DLGridViewBean();
            dlGridViewBean.setImg(resultBean.getUrl());
            dlGridViewBean.setText(resultBean.getName());
            dlGridViewBeans.add(dlGridViewBean);
        }
        setting = new DLVPSetting(itemView.getContext(), 2, 4, new DLVPSetting.OnClickItemListener() {
            @Override
            public void OnClickItem(int position, DLGridViewBean bean) {

            }

            @Override
            public void OnClickItem(int position, Map<String, Object> map) {
//                Toast.makeText(itemView.getContext(), (String) map.get("txt"), Toast.LENGTH_SHORT).show();
            }
        });
        mBinding.viewPager.setAdapter(setting.getAdapter(dlGridViewBeans));
        mBinding.indicator.setViewPager(mBinding.viewPager);

    }
}

