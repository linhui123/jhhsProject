package com.jhhscm.platform.fragment.home.holder;

import android.view.View;

import com.google.gson.Gson;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemHomePageBusinessBinding;
import com.jhhscm.platform.event.JumpEvent;
import com.jhhscm.platform.fragment.home.AdBean;
import com.jhhscm.platform.fragment.home.HomePageItem;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.views.dlflipviewpage.bean.DLGridViewBean;
import com.jhhscm.platform.views.dlflipviewpage.utils.DLVPSetting;

import java.util.ArrayList;
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
            dlGridViewBeans = new ArrayList<>();
            for (AdBean.ResultBean resultBean : item.adBean3.getResult()) {
                DLGridViewBean dlGridViewBean = new DLGridViewBean();
                dlGridViewBean.setImg(resultBean.getUrl());
                dlGridViewBean.setText(resultBean.getName());
                dlGridViewBean.setObject(resultBean);
                dlGridViewBeans.add(dlGridViewBean);
            }
            if (dlGridViewBeans.size() < 9) {
                mBinding.indicator.setVisibility(View.GONE);
            } else {
                mBinding.indicator.setVisibility(View.VISIBLE);
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
            mBinding.viewPager.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                @Override
                public void onViewAttachedToWindow(View v) {
                    try {
                        mBinding.viewPager.getAdapter().notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onViewDetachedFromWindow(View v) {

                }
            });
            mBinding.indicator.setViewPager(mBinding.viewPager);
        }
    }

    private void jump(String type, AdBean.ResultBean resultBean) {
        EventBusUtil.post(new JumpEvent(type, resultBean));
    }


    /**
     * 判断event坐标点是否在sv范围内:    
     */
    public boolean isTouchNsv(float x, float y) {
        int[] pos = new int[2];
        mBinding.viewPager.getLocationOnScreen(pos);
        int width = mBinding.viewPager.getMeasuredWidth();
        int height = mBinding.viewPager.getMeasuredHeight();
        return x >= pos[0] && x <= pos[0] + width && y >= pos[1] && y <= pos[1] + height;
    }
}

