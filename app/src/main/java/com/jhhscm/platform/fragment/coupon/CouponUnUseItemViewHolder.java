package com.jhhscm.platform.fragment.coupon;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.CashierActivity;
import com.jhhscm.platform.activity.OrderDetailActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemCouponUnuseBinding;
import com.jhhscm.platform.databinding.ItemOrderStatus1Binding;
import com.jhhscm.platform.event.OrderCancleEvent;
import com.jhhscm.platform.fragment.GoodsToCarts.CreateOrderResultBean;
import com.jhhscm.platform.fragment.my.order.OrderListViewHolder;
import com.jhhscm.platform.fragment.my.order.OrderStaus1ViewHolder;
import com.jhhscm.platform.fragment.sale.FindOrderBean;
import com.jhhscm.platform.fragment.sale.SaleItem;
import com.jhhscm.platform.tool.EventBusUtil;
import com.jhhscm.platform.views.dialog.LogisticsDialog;
import com.jhhscm.platform.views.dialog.NewCouponDialog;
import com.jhhscm.platform.views.recyclerview.DividerItemDecoration;

public class CouponUnUseItemViewHolder extends AbsRecyclerViewHolder<SaleItem> {

    private ItemCouponUnuseBinding mBinding;

    public CouponUnUseItemViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemCouponUnuseBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final SaleItem item) {
        if (item.orderBean != null) {

            mBinding.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new NewCouponDialog(itemView.getContext(), "", "", "", new NewCouponDialog.CallbackListener() {
                        @Override
                        public void clickYes() {

                        }
                    }).show();

                }
            });
        }
    }
}

