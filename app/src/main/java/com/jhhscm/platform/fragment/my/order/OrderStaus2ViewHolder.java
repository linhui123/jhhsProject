package com.jhhscm.platform.fragment.my.order;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.OrderDetailActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemOrderStatus1Binding;
import com.jhhscm.platform.databinding.ItemOrderStatus2Binding;
import com.jhhscm.platform.event.OrderCancleEvent;
import com.jhhscm.platform.event.OrderConfirmEvent;
import com.jhhscm.platform.event.PayEvent;
import com.jhhscm.platform.fragment.sale.FindOrderBean;
import com.jhhscm.platform.fragment.sale.SaleItem;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.views.dialog.ConfirmOrderDialog;
import com.jhhscm.platform.views.dialog.LogisticsDialog;
import com.jhhscm.platform.views.recyclerview.DividerItemDecoration;

public class OrderStaus2ViewHolder extends AbsRecyclerViewHolder<SaleItem> {

    private ItemOrderStatus2Binding mBinding;
    private String type = "";

    public OrderStaus2ViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemOrderStatus2Binding.bind(itemView);
    }

    @Override
    protected void onBindView(final SaleItem item) {
        if (item.orderBean != null) {
            if (item.orderBean.getBus_name() != null && item.orderBean.getBus_name().length() > 0) {
                mBinding.name.setText(item.orderBean.getBus_name());
            }

            mBinding.data.setText(item.orderBean.getAdd_time());
            mBinding.tatal.setText("合计 ￥" + item.orderBean.getOrder_price());
            mBinding.hoursNum.setText("￥" + item.orderBean.getOther_price());
            mBinding.peijianNum.setText("￥" + item.orderBean.getGoods_price());
            mBinding.orderStaus.setText(item.orderBean.getOrder_text());

            if (item.orderBean.getOrder_status().contains("10")) {
                type = "1";
                mBinding.confirm.setVisibility(View.VISIBLE);
                mBinding.pay.setVisibility(View.VISIBLE);
            } else if (item.orderBean.getOrder_status().contains("20")) {
                type = "2";
                mBinding.pay.setVisibility(View.GONE);
            } else if (item.orderBean.getOrder_status().contains("30")) {
                type = "3";
                mBinding.pay.setVisibility(View.GONE);
            } else if (item.orderBean.getOrder_status().contains("40")) {
                type = "4";
                mBinding.pay.setVisibility(View.GONE);
                mBinding.del.setVisibility(View.VISIBLE);
            } else if (item.orderBean.getOrder_status().contains("50")) {//已确认待付款
                type = "5";
                mBinding.pay.setVisibility(View.VISIBLE);
                mBinding.del.setVisibility(View.GONE);
            } else {
                mBinding.pay.setVisibility(View.GONE);
            }
            mBinding.rv.setHasFixedSize(true);
            mBinding.rv.setNestedScrollingEnabled(false);
            mBinding.rv.addItemDecoration(new DividerItemDecoration(itemView.getContext()));
            mBinding.rv.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            InnerAdapter mAdapter = new InnerAdapter(itemView.getContext());
            mBinding.rv.setAdapter(mAdapter);
            mAdapter.setData(item.orderBean.getGoodsOwnerList());

//            mBinding.rv.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
////                    return mBinding.ll.onTouchEvent(event);
//                    if (event.getAction() == MotionEvent.ACTION_UP) {
//                        mBinding.ll.performClick();
//                    }
//                    return false;
//                }
//            });

            mBinding.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item.orderBean.getOrder_status().contains("10")) {
                        if ("1".equals(item.orderBean.getIs_payframe())) {
                            OrderDetailActivity.start(itemView.getContext(), item.orderBean.getOrder_code(), 1);
                        } else {
                            OrderDetailActivity.start(itemView.getContext(), item.orderBean, 1);
                        }
                    } else if (item.orderBean.getOrder_status().contains("20")) {
                        if ("1".equals(item.orderBean.getIs_payframe())) {
                            OrderDetailActivity.start(itemView.getContext(), item.orderBean.getOrder_code(), 2);
                        } else {
                            OrderDetailActivity.start(itemView.getContext(), item.orderBean, 2);
                        }
                    } else if (item.orderBean.getOrder_status().contains("30")) {
                        if ("1".equals(item.orderBean.getIs_payframe())) {
                            OrderDetailActivity.start(itemView.getContext(), item.orderBean.getOrder_code(), 3);
                        } else {
                            OrderDetailActivity.start(itemView.getContext(), item.orderBean, 3);
                        }
                    } else if (item.orderBean.getOrder_status().contains("40")) {
                        if ("1".equals(item.orderBean.getIs_payframe())) {
                            OrderDetailActivity.start(itemView.getContext(), item.orderBean.getOrder_code(), 4);
                        } else {
                            OrderDetailActivity.start(itemView.getContext(), item.orderBean, 4);
                        }
                    } else {
                        if ("1".equals(item.orderBean.getIs_payframe())) {
                            OrderDetailActivity.start(itemView.getContext(), item.orderBean.getOrder_code(), 1);
                        } else {
                            OrderDetailActivity.start(itemView.getContext(), item.orderBean, 1);
                        }
                    }
                }
            });
            mBinding.confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBusUtil.post(new OrderConfirmEvent(item.orderBean.getOrder_code() + "", type));
                }
            });

            mBinding.pay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBusUtil.post(new PayEvent(item.orderBean.getId() + "", type));
                }
            });

            mBinding.del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBusUtil.post(new OrderCancleEvent(item.orderBean.getOrder_code(), type));
                }
            });
        }
    }

    private class InnerAdapter extends AbsRecyclerViewAdapter<FindOrderListBean.DataBean.GoodsOwnerListBean> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<FindOrderListBean.DataBean.GoodsOwnerListBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new OrderListBusViewHolder(mInflater.inflate(R.layout.item_order_bus, parent, false));
        }
    }
}



