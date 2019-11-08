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
    private void addUnCoupon(CouponListBean.ResultBean dataBean) {
        SaleItem item = new SaleItem(SaleItem.TYPE_SALE_1);
        item.couponResult = dataBean;
        mData.add(item);
    }

    /**
     * 已使用
     */
    private void addUseCoupon(CouponListBean.ResultBean dataBean) {
        SaleItem item = new SaleItem(SaleItem.TYPE_SALE_2);
        item.couponResult = dataBean;
        mData.add(item);
    }

    /**
     * 已过期
     */
    private void addOldCoupon(CouponListBean.ResultBean dataBean) {
        SaleItem item = new SaleItem(SaleItem.TYPE_SALE_3);
        item.couponResult = dataBean;
        mData.add(item);
    }


    public void setDetail(CouponListBean listBean, String type) {
        mData.clear();
        if (listBean.getResult() != null) {
            for (CouponListBean.ResultBean dataBean : listBean.getResult()) {
                if ("0".equals(type)) {
                    if (dataBean.getStatus() == 0) {
                        addUnCoupon(dataBean);
                    }
                } else if ("1".equals(type)) {
                    if (dataBean.getStatus() == 1) {
                        addUseCoupon(dataBean);
                    }
                } else {
                    if (dataBean.getStatus() == 2) {
                        addOldCoupon(dataBean);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    public void setExpend(CouponListBean listBean, String type) {
        if (listBean.getResult() != null) {
            for (int i = 0; i < listBean.getResult().size(); i++) {
                if ("0".equals(type)) {
                    if (listBean.getResult().get(i).getStatus() == 0) {
                        addUnCoupon(listBean.getResult().get(i));
                    }
                } else if ("1".equals(type)) {
                    if (listBean.getResult().get(i).getStatus() == 1) {
                        addUseCoupon(listBean.getResult().get(i));
                    }
                } else {
                    if (listBean.getResult().get(i).getStatus() == 2) {
                        addOldCoupon(listBean.getResult().get(i));
                    }
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



