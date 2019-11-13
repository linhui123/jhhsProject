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

    @Override
    public long getItemId(int position) { return position; }

    /**
     * 待付款
     */
    private void addOederStaus1(FindOrderListBean.DataBean dataBean) {
        SaleItem item = new SaleItem(SaleItem.TYPE_SALE_1);
        item.orderBean = dataBean;
        mData.add(item);
    }

    public void setDetail(FindOrderListBean listBean) {
        mData.clear();
        if (listBean.getData() != null) {
            for (FindOrderListBean.DataBean dataBean : listBean.getData()) {
                addOederStaus1(dataBean);
            }
        }
        notifyDataSetChanged();
    }

    public void setExpend(FindOrderListBean listBean) {
        if (listBean.getData() != null) {
            for (int i=0;i<listBean.getData().size();i++){
                addOederStaus1(listBean.getData().get(i));
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public AbsRecyclerViewHolder<SaleItem> onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case SaleItem.TYPE_SALE_1:
                return new OrderStaus1ViewHolder(mInflater.inflate(R.layout.item_order_status_1, parent, false));
        }
        return null;
    }
}


