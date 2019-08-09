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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.gson.Gson;
import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.MainActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.event.JumpEvent;
import com.jhhscm.platform.fragment.home.AdBean;
import com.jhhscm.platform.fragment.home.HomePageItem;

import com.jhhscm.platform.databinding.ItemHomePageBusinessBinding;
import com.jhhscm.platform.tool.EventBusUtil;
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
        if (item.adBean3 != null && item.adBean3.getResult() != null) {
            if (item.adBean3.getResult().size() < 9) {
                mBinding.indicator.setVisibility(View.GONE);
            }
            dlGridViewBeans = new ArrayList<>();
            for (AdBean.ResultBean resultBean : item.adBean3.getResult()) {
                DLGridViewBean dlGridViewBean = new DLGridViewBean();
                dlGridViewBean.setImg(resultBean.getUrl());
                dlGridViewBean.setText(resultBean.getName());
                dlGridViewBean.setObject(resultBean);
                dlGridViewBeans.add(dlGridViewBean);
            }
            setting = new DLVPSetting(itemView.getContext(), 2, 4, new DLVPSetting.OnClickItemListener() {
                @Override
                public void OnClickItem(int position, DLGridViewBean bean) {
                    AdBean.ResultBean resultBean = (AdBean.ResultBean) bean.getObject();
                    Gson gson = new Gson();
                    AdBean.DataBean findOrderBean = gson.fromJson(resultBean.getContent(), AdBean.DataBean.class);
                    jump(findOrderBean.getTYPE(), resultBean);
                }

                @Override
                public void OnClickItem(int position, Map<String, Object> map) {

                }
            });
            mBinding.viewPager.setAdapter(setting.getAdapter(dlGridViewBeans));
            mBinding.indicator.setViewPager(mBinding.viewPager);
        }
    }

    private void jump(String type, AdBean.ResultBean resultBean) {
        EventBusUtil.post(new JumpEvent(type, resultBean));
    }
}

