package com.jhhscm.platform.fragment.my.order;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.OrderDetailActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemHomePageBannerBinding;
import com.jhhscm.platform.databinding.ItemOrderStatus1Binding;
import com.jhhscm.platform.fragment.GoodsToCarts.CreateOrderFragment;
import com.jhhscm.platform.fragment.GoodsToCarts.CreateOrderViewHolder;
import com.jhhscm.platform.fragment.GoodsToCarts.GetCartGoodsByUserCodeBean;
import com.jhhscm.platform.fragment.home.AdBean;
import com.jhhscm.platform.fragment.home.HomePageItem;
import com.jhhscm.platform.fragment.sale.SaleItem;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

public class OrderStaus1ViewHolder extends AbsRecyclerViewHolder<SaleItem> {

    private ItemOrderStatus1Binding mBinding;
    private InnerAdapter mAdapter;

    public OrderStaus1ViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemOrderStatus1Binding.bind(itemView);
    }

    @Override
    protected void onBindView(final SaleItem item) {
        mBinding.rv.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        mAdapter = new InnerAdapter(itemView.getContext());
        mBinding.rv.setAdapter(mAdapter);
        mAdapter.setData(new GetCartGoodsByUserCodeBean().getResult());

        mBinding.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("OrderStaus1", "getOrder_text " + item.orderBean.getOrder_text());
                if ("未付款".equals(item.orderBean.getOrder_text())) {
                    OrderDetailActivity.start(itemView.getContext(), item.orderBean.getOrder_code(), 1);
                } else if ("未发货".equals(item.orderBean.getOrder_text())) {
                    OrderDetailActivity.start(itemView.getContext(), item.orderBean.getOrder_code(), 2);
                } else if ("未收货".equals(item.orderBean.getOrder_text())) {
                    OrderDetailActivity.start(itemView.getContext(), item.orderBean.getOrder_code(), 3);
                } else if ("已完成".equals(item.orderBean.getOrder_text())) {
                    OrderDetailActivity.start(itemView.getContext(), item.orderBean.getOrder_code(), 4);
                }
            }
        });

    }

    private class InnerAdapter extends AbsRecyclerViewAdapter<GetCartGoodsByUserCodeBean.ResultBean> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<GetCartGoodsByUserCodeBean.ResultBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CreateOrderViewHolder(mInflater.inflate(R.layout.item_create_order, parent, false));
        }
    }
}


