package com.jhhscm.platform.fragment.home.holder;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemHomePageBannerBinding;
import com.jhhscm.platform.fragment.home.AdBean;
import com.jhhscm.platform.fragment.home.HomePageItem;
import com.umeng.analytics.MobclickAgent;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

public class HomePageBannerViewHolder extends AbsRecyclerViewHolder<HomePageItem> {

    private ItemHomePageBannerBinding mBinding;

    public HomePageBannerViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemHomePageBannerBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final HomePageItem item) {
        if (item.adBean1 != null) {
            ViewTreeObserver vto = mBinding.bgaBanner.getViewTreeObserver();
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    mBinding.bgaBanner.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    ViewGroup.LayoutParams lp1 = mBinding.bgaBanner.getLayoutParams();
                    lp1.width = mBinding.bgaBanner.getWidth();
                    lp1.height = (int) (mBinding.bgaBanner.getWidth() / 2.45);
                    mBinding.bgaBanner.setLayoutParams(lp1);
                }
            });

            mBinding.bgaBanner.setAdapter(new BGABanner.Adapter() {
                @Override
                public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
                    ImageLoader.getInstance().displayImage((String) model, (ImageView) view);
                }
            });
            final List<String> list = new ArrayList<>();
            for (AdBean.ResultBean adBean : item.adBean1.getResult()) {
                list.add(adBean.getUrl());
            }
//        list.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic1xjab4j30ci08cjrv.jpg");
//        list.add("http://img.netbian.com/file/2018/0827/f97554874cd162e372d1a947b90a060b.jpg");
//        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1562306846041&di=2b594e635a7b21ab85e400c27aad48fa&imgtype=0&src=http%3A%2F%2Fimg010.hc360.cn%2Fg6%2FM09%2F25%2FB6%2FwKhQr1OVYwGEFaS_AAAAALvbF5Q839.jpg");

            mBinding.bgaBanner.setData(list, null);

            mBinding.bgaBanner.setDelegate(new BGABanner.Delegate() {
                @Override
                public void onBannerItemClick(BGABanner banner, View itemViews, @Nullable Object model, int position) {
                    MobclickAgent.onEvent(itemView.getContext(), "HomePageBanner");
//                if (!StringUtils.isNullEmpty(jumpData)) {
//                    JumpDataUtils.jumpData(itemView.getContext(), jumpData, "");
////                        EventBusUtil.post(new SignInEvent(SignInEvent.CLOSE));
//                }
                }
            });
        }
    }
}

