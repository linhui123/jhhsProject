package com.jhhscm.platform.fragment.my.store.viewholder;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.jhhscm.platform.R;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemStoreOrderBinding;
import com.jhhscm.platform.fragment.home.bean.GetPageArticleListBean;
import com.jhhscm.platform.fragment.my.store.action.FindBusOrderListBean;
import com.jhhscm.platform.fragment.my.store.viewholder.MyStoreOrderGoodsViewHolder;
import com.jhhscm.platform.fragment.sale.FindOrderBean;
import com.jhhscm.platform.tool.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class StoreOrderItemViewHolder extends AbsRecyclerViewHolder<FindBusOrderListBean.ResultBean.DataBean> {

    private ItemStoreOrderBinding mBinding;

    public StoreOrderItemViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemStoreOrderBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final FindBusOrderListBean.ResultBean.DataBean item) {
        FindOrderBean.GoodsListBean goodsListBean = new FindOrderBean.GoodsListBean();
        List<FindOrderBean.GoodsListBean> listBeans = new ArrayList<>();
        listBeans.add(goodsListBean);
        listBeans.add(goodsListBean);
        listBeans.add(goodsListBean);

        mBinding.recyclerview.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        InnerAdapter mAdapter = new InnerAdapter(itemView.getContext());
        mBinding.recyclerview.setAdapter(mAdapter);
        mAdapter.setData(listBeans);

        mBinding.tvFunc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show(itemView.getContext(), "取消订单");
            }
        });
        mBinding.tvFunc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show(itemView.getContext(), "修改订单");
            }
        });
    }

    private class InnerAdapter extends AbsRecyclerViewAdapter<FindOrderBean.GoodsListBean> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<FindOrderBean.GoodsListBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyStoreOrderGoodsViewHolder(mInflater.inflate(R.layout.item_store_order_goods, parent, false));
        }
    }
}
