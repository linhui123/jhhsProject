package com.jhhscm.platform.fragment.my.store.viewholder;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.OrderDetailActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemStoreOrderBinding;
import com.jhhscm.platform.event.OrderCancleEvent;
import com.jhhscm.platform.fragment.my.store.MyServiceGoodsViewHolder;
import com.jhhscm.platform.fragment.my.store.action.FindBusGoodsOwnerOrderListByUserCodeBean;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.tool.ToastUtil;

public class StoreOrderItemViewHolder extends AbsRecyclerViewHolder<FindBusGoodsOwnerOrderListByUserCodeBean.DataBean> {

    private ItemStoreOrderBinding mBinding;
    private int type = 1;
    public StoreOrderItemViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemStoreOrderBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final FindBusGoodsOwnerOrderListByUserCodeBean.DataBean item) {
        if (item != null) {
            mBinding.orderNo.setText("订单编号：" + item.getOrder_code());
            if (item.getAdd_time() != null) {
                mBinding.orderTime.setText("下单时间：" + item.getAdd_time());
            } else {
                mBinding.orderTime.setText("下单时间：--");
            }

            if (item.getOrder_status() == 101) {
                type = 1;
                mBinding.orderType.setText("待付款");
                mBinding.tvFunc1.setVisibility(View.VISIBLE);
                mBinding.tvFunc2.setVisibility(View.GONE);
            } else if (item.getOrder_status() == 102) {
                type = 4;
                mBinding.orderType.setText("已取消");
                mBinding.tvFunc1.setVisibility(View.GONE);
                mBinding.tvFunc2.setVisibility(View.GONE);
            } else if (item.getOrder_status() == 501) {
                type = 1;
                mBinding.orderType.setText("用户已确认订单");
                mBinding.tvFunc1.setVisibility(View.GONE);
                mBinding.tvFunc2.setVisibility(View.GONE);
            } else if (item.getOrder_status() == 201) {
                type = 4;
                mBinding.orderType.setText("已完成");
                mBinding.tvFunc1.setVisibility(View.GONE);
                mBinding.tvFunc2.setVisibility(View.GONE);
            } else {
                type = 4;
                mBinding.orderType.setText(item.getOrder_status_name());
                mBinding.tvFunc1.setVisibility(View.GONE);
                mBinding.tvFunc2.setVisibility(View.GONE);
            }

            mBinding.fee.setText("￥" + item.getOther_price());
            mBinding.total.setText("￥" + item.getGoods_price());
            mBinding.phone.setText(item.getUser_mobile());
            mBinding.name.setText(item.getUser_name());

            mBinding.recyclerview.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            InnerAdapter mAdapter = new InnerAdapter(itemView.getContext());
            mBinding.recyclerview.setAdapter(mAdapter);
            mAdapter.setData(item.getGoodsList());

            mBinding.tvFunc1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item.getOrder_status() == 101) {
                        EventBusUtil.post(new OrderCancleEvent(item.getOrder_code(), "",2));
                    } else {
                        ToastUtil.show(itemView.getContext(), "用户已确认不可取消订单");
                    }
                }
            });


            mBinding.tvFunc2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.show(itemView.getContext(), "修改订单");
                }
            });

            mBinding.recyclerview.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                @Override
                public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                    if (e.getAction() == MotionEvent.ACTION_UP) {
                        mBinding.ll.performClick();
                    }
                    return false;
                }

                @Override
                public void onTouchEvent(RecyclerView rv, MotionEvent e) {

                }

                @Override
                public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

                }
            });
            mBinding.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OrderDetailActivity.start(itemView.getContext(), item.getOrder_code(), "sign", type);
                }
            });
        }
    }

    private class InnerAdapter extends AbsRecyclerViewAdapter<FindBusGoodsOwnerOrderListByUserCodeBean.DataBean.GoodsListBean> {
        public InnerAdapter(Context context) {
            super(context);
        }

        @Override
        public AbsRecyclerViewHolder<FindBusGoodsOwnerOrderListByUserCodeBean.DataBean.GoodsListBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyServiceGoodsViewHolder(mInflater.inflate(R.layout.item_store_order_goods, parent, false));
        }
    }
}
