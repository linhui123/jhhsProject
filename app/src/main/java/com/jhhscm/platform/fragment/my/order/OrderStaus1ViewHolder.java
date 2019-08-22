package com.jhhscm.platform.fragment.my.order;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.CashierActivity;
import com.jhhscm.platform.activity.OrderDetailActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemOrderStatus1Binding;
import com.jhhscm.platform.event.OrderCancleEvent;
import com.jhhscm.platform.fragment.GoodsToCarts.CreateOrderResultBean;
import com.jhhscm.platform.fragment.sale.FindOrderBean;
import com.jhhscm.platform.fragment.sale.SaleItem;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.views.dialog.LogisticsDialog;
import com.jhhscm.platform.views.recyclerview.DividerItemDecoration;

public class OrderStaus1ViewHolder extends AbsRecyclerViewHolder<SaleItem> {

    private ItemOrderStatus1Binding mBinding;
    private String type = "";

    public OrderStaus1ViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemOrderStatus1Binding.bind(itemView);
    }

    @Override
    protected void onBindView(final SaleItem item) {
        if (item.orderBean != null) {

            if (item.orderBean.getGoodsListBeans() != null && item.orderBean.getGoodsListBeans().size() > 0) {
                mBinding.data.setText(item.orderBean.getGoodsListBeans().get(0).getUpdateTime());
            }
            mBinding.tatal.setText("合计 ￥" + item.orderBean.getOrder_price());
            mBinding.orderStaus.setText(item.orderBean.getOrder_text());

            if (item.orderBean.getOrder_status().contains("10")) {
                type = "1";
                mBinding.cancle.setVisibility(View.VISIBLE);
                mBinding.pay.setVisibility(View.VISIBLE);
                mBinding.wuliu.setVisibility(View.GONE);
            } else if (item.orderBean.getOrder_status().contains("20")) {
                type = "2";
                mBinding.cancle.setVisibility(View.GONE);
                mBinding.pay.setVisibility(View.GONE);
                mBinding.wuliu.setVisibility(View.GONE);
            } else if (item.orderBean.getOrder_status().contains("30")) {
                type = "3";
                mBinding.cancle.setVisibility(View.GONE);
                mBinding.pay.setVisibility(View.GONE);
                mBinding.wuliu.setVisibility(View.VISIBLE);
            } else if (item.orderBean.getOrder_status().contains("40")) {
                type = "4";
                mBinding.cancle.setVisibility(View.GONE);
                mBinding.pay.setVisibility(View.GONE);
                mBinding.del.setVisibility(View.VISIBLE);
            } else {
                mBinding.cancle.setVisibility(View.GONE);
                mBinding.pay.setVisibility(View.GONE);
                mBinding.wuliu.setVisibility(View.GONE);
            }

            mBinding.rv.addItemDecoration(new DividerItemDecoration(itemView.getContext()));
            mBinding.rv.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            InnerAdapter mAdapter = new InnerAdapter(itemView.getContext());
            mBinding.rv.setAdapter(mAdapter);
            mAdapter.setData(item.orderBean.getGoodsListBeans());

            mBinding.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item.orderBean.getOrder_status().contains("10")) {
                        OrderDetailActivity.start(itemView.getContext(), item.orderBean.getOrder_code(), 1);
                    } else if (item.orderBean.getOrder_status().contains("20")) {
                        OrderDetailActivity.start(itemView.getContext(), item.orderBean.getOrder_code(), 2);
                    } else if (item.orderBean.getOrder_status().contains("30")) {
                        OrderDetailActivity.start(itemView.getContext(), item.orderBean.getOrder_code(), 3);
                    } else if (item.orderBean.getOrder_status().contains("40")) {
                        OrderDetailActivity.start(itemView.getContext(), item.orderBean.getOrder_code(), 4);
                    }
                }
            });

            mBinding.cancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBusUtil.post(new OrderCancleEvent(item.orderBean.getOrder_code(), type));
                }
            });

            mBinding.pay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CashierActivity.start(itemView.getContext(), new CreateOrderResultBean(new CreateOrderResultBean.DataBean(item.orderBean.getOrder_code())));
                }
            });

            mBinding.del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBusUtil.post(new OrderCancleEvent(item.orderBean.getOrder_code(), type));
                }
            });

            mBinding.wuliu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new LogisticsDialog(itemView.getContext(), item.orderBean.getShip_channel(), item.orderBean.getShip_sn()).show();
                }
            });
        }
    }

    private class InnerAdapter extends AbsRecyclerViewAdapter<FindOrderBean.GoodsListBean> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<FindOrderBean.GoodsListBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new OrderListViewHolder(mInflater.inflate(R.layout.item_create_order, parent, false));
        }
    }
}


