package com.jhhscm.platform.fragment.coupon;

import android.content.Context;
import android.view.ViewGroup;

import com.jhhscm.platform.R;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.fragment.my.order.FindOrderListBean;
import com.jhhscm.platform.fragment.sale.SaleItem;

public class MyCouponListAdapter extends AbsRecyclerViewAdapter<SaleItem> {

    public MyCouponListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        return get(position).itemType;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 未使用
     */
    private void addUnCoupon(FindOrderListBean.DataBean dataBean) {
        SaleItem item = new SaleItem(SaleItem.TYPE_SALE_1);
        item.orderBean = dataBean;
        mData.add(item);
    }

    /**
     * 已使用
     */
    private void addUseCoupon(FindOrderListBean.DataBean dataBean) {
        SaleItem item = new SaleItem(SaleItem.TYPE_SALE_2);
        item.orderBean = dataBean;
        mData.add(item);
    }

    /**
     * 已过期
     */
    private void addOldCoupon(FindOrderListBean.DataBean dataBean) {
        SaleItem item = new SaleItem(SaleItem.TYPE_SALE_3);
        item.orderBean = dataBean;
        mData.add(item);
    }


    public void setDetail(FindOrderListBean listBean, String type) {
        mData.clear();
        if (listBean.getData() != null) {
            for (FindOrderListBean.DataBean dataBean : listBean.getData()) {
                if ("1".equals(type)) {
                    addUseCoupon(dataBean);
                } else if ("2".equals(type)) {
                    addOldCoupon(dataBean);
                } else {
                    addUnCoupon(dataBean);
                }

            }
        }
        notifyDataSetChanged();
    }

    public void setExpend(FindOrderListBean listBean, String type) {
        if (listBean.getData() != null) {
            for (int i = 0; i < listBean.getData().size(); i++) {
                if ("1".equals(type)) {
                    addUseCoupon(listBean.getData().get(i));
                } else if ("2".equals(type)) {
                    addOldCoupon(listBean.getData().get(i));
                } else {
                    addUnCoupon(listBean.getData().get(i));
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public AbsRecyclerViewHolder<SaleItem> onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case SaleItem.TYPE_SALE_1:
                return new CouponUnUseItemViewHolder(mInflater.inflate(R.layout.item_coupon_unuse, parent, false));
            case SaleItem.TYPE_SALE_2:
                return new CouponUseItemViewHolder(mInflater.inflate(R.layout.item_coupon_use, parent, false));
            case SaleItem.TYPE_SALE_3:
                return new CouponOldItemViewHolder(mInflater.inflate(R.layout.item_coupon_old, parent, false));
        }
        return null;
    }
}



