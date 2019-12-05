package com.jhhscm.platform.fragment.sale;

import android.content.Context;
import android.view.ViewGroup;

import com.jhhscm.platform.R;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;


public class SaleMachineAdapter extends AbsRecyclerViewAdapter<SaleItem> {

    public SaleMachineAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        return get(position).itemType;
    }

    /**
     * 头部
     */
    private void addTopItemNew() {
        SaleItem item = new SaleItem(SaleItem.TYPE_MECHANICS_TOP);
        mData.add(item);
    }

    /**
     * 历史item
     */
    private void addHistoryItemNew(OldGoodOrderHistoryBean.DataBean dataBean) {
        SaleItem item = new SaleItem(SaleItem.TYPE_MECHANICS_OLD);
        item.dataBean = dataBean;
        mData.add(item);
    }

    public void setData(OldGoodOrderHistoryBean oldGoodOrderHistoryBean) {
        mData.clear();
        addTopItemNew();
        for (OldGoodOrderHistoryBean.DataBean dataBean : oldGoodOrderHistoryBean.getData()) {
            addHistoryItemNew(dataBean);
        }
        notifyDataSetChanged();
    }

    public void setExpend(OldGoodOrderHistoryBean oldGoodOrderHistoryBean) {
        addTopItemNew();
        for (OldGoodOrderHistoryBean.DataBean dataBean : oldGoodOrderHistoryBean.getData()) {
            addHistoryItemNew(dataBean);
        }
        notifyDataSetChanged();
    }

    @Override
    public AbsRecyclerViewHolder<SaleItem> onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case SaleItem.TYPE_MECHANICS_TOP:
                return new SaleMechanicsTopViewHolder(mInflater.inflate(R.layout.item_sale_mechanics_top, parent, false));
            case SaleItem.TYPE_MECHANICS_OLD:
                return new SaleMechanicsHistoryViewHolder(mInflater.inflate(R.layout.item_sale_mechanics_history, parent, false));
        }
        return null;
    }
}


