package com.jhhscm.platform.fragment.my.order;

import android.content.Context;
import android.view.ViewGroup;

import com.jhhscm.platform.R;
import com.jhhscm.platform.activity.OrderDetailActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.fragment.sale.SaleItem;

public class MyPeiJianListAdapter extends AbsRecyclerViewAdapter<SaleItem> {

    public MyPeiJianListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        return get(position).itemType;
    }

    /**
     * 待付款
     */
    private void addOederStaus1(FindOrderListBean.DataBean dataBean) {
        SaleItem item = new SaleItem(SaleItem.TYPE_SALE_1);
        item.orderBean = dataBean;
        mData.add(item);
    }

    /**
     * 代发货
     */
    private void addOederStaus2(FindOrderListBean.DataBean dataBean) {
        SaleItem item = new SaleItem(SaleItem.TYPE_SALE_2);
        item.orderBean = dataBean;
        mData.add(item);
    }

    /**
     * 待收货
     */
    private void addOederStaus3(FindOrderListBean.DataBean dataBean) {
        SaleItem item = new SaleItem(SaleItem.TYPE_SALE_3);
        item.orderBean = dataBean;
        mData.add(item);
    }

    /**
     * 已完成
     */
    private void addOederStaus4(FindOrderListBean.DataBean dataBean) {
        SaleItem item = new SaleItem(SaleItem.TYPE_SALE_4);
        item.orderBean = dataBean;
        mData.add(item);
    }


    public void setDetail(FindOrderListBean listBean) {
        mData.clear();
        if (listBean.getData() != null) {
            for (FindOrderListBean.DataBean dataBean : listBean.getData()) {
                if ("未付款".equals(dataBean.getOrder_text())) {
                    addOederStaus1(dataBean);
                } else if ("未发货".equals(dataBean.getOrder_text())) {
                    addOederStaus1(dataBean);
                } else if ("未收货".equals(dataBean.getOrder_text())) {
                    addOederStaus1(dataBean);
                } else if ("已完成".equals(dataBean.getOrder_text())) {
                    addOederStaus1(dataBean);
                }
            }
        }

        notifyDataSetChanged();
    }

    public void setExpend(FindOrderListBean listBean) {
        if (listBean.getData() != null) {
            for (FindOrderListBean.DataBean dataBean : listBean.getData()) {
                if ("未付款".equals(dataBean.getOrder_text())) {
                    addOederStaus1(dataBean);
                } else if ("未发货".equals(dataBean.getOrder_text())) {
                    addOederStaus1(dataBean);
                } else if ("未收货".equals(dataBean.getOrder_text())) {
                    addOederStaus1(dataBean);
                } else if ("已完成".equals(dataBean.getOrder_text())) {
                    addOederStaus1(dataBean);
                }
            }
        }

        notifyDataSetChanged();
    }

    @Override
    public AbsRecyclerViewHolder<SaleItem> onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case SaleItem.TYPE_SALE_1:
                return new OrderStaus1ViewHolder(mInflater.inflate(R.layout.item_order_status_1, parent, false));
            case SaleItem.TYPE_SALE_2:
                return new OrderStaus1ViewHolder(mInflater.inflate(R.layout.item_order_status_1, parent, false));
            case SaleItem.TYPE_SALE_3:
                return new OrderStaus1ViewHolder(mInflater.inflate(R.layout.item_order_status_1, parent, false));
            case SaleItem.TYPE_SALE_4:
                return new OrderStaus1ViewHolder(mInflater.inflate(R.layout.item_order_status_1, parent, false));
        }
        return null;
    }
}


