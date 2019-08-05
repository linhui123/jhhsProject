package com.jhhscm.platform.fragment.Mechanics.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jhhscm.platform.R;
import com.jhhscm.platform.adater.AbsRecyclerViewAdapter;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.fragment.Mechanics.MechanicsItem;
import com.jhhscm.platform.fragment.Mechanics.bean.GetGoodsPageListBean;
import com.jhhscm.platform.fragment.Mechanics.holder.NewMechanicsViewHolder;
import com.jhhscm.platform.fragment.Mechanics.holder.OldMechanicsViewHolder;
import com.jhhscm.platform.fragment.home.AdBean;

public class MechanicsAdapter extends AbsRecyclerViewAdapter<MechanicsItem> {

    public MechanicsAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        return get(position).itemType;
    }

    /**
     * 新机
     */
    private void addMechanicsItemNew(AdBean adBean) {
        MechanicsItem item = new MechanicsItem(MechanicsItem.TYPE_MECHANICS_NEW);
        mData.add(item);
    }

    /**
     * 二手机
     */
    private void addMechanicsItemOld(AdBean adBean) {
        MechanicsItem item = new MechanicsItem(MechanicsItem.TYPE_MECHANICS_OLD);
        mData.add(item);
    }

    public void setExpend(GetGoodsPageListBean getGoodsPageListBean) {
        //新机
        addMechanicsItemNew(new AdBean());
        addMechanicsItemNew(new AdBean());
        addMechanicsItemNew(new AdBean());
        addMechanicsItemNew(new AdBean());
        addMechanicsItemNew(new AdBean());

        notifyDataSetChanged();
    }

    public void setDetail(MechanicsItem mechanicsItem) {
        if (mechanicsItem.itemType == MechanicsItem.TYPE_MECHANICS_NEW) {//新机
            addMechanicsItemNew(new AdBean());
            addMechanicsItemNew(new AdBean());
            addMechanicsItemNew(new AdBean());
            addMechanicsItemNew(new AdBean());
            addMechanicsItemNew(new AdBean());
        }

        if (mechanicsItem.itemType == MechanicsItem.TYPE_MECHANICS_OLD) {//二手机
            addMechanicsItemOld(new AdBean());
            addMechanicsItemOld(new AdBean());
            addMechanicsItemOld(new AdBean());
            addMechanicsItemOld(new AdBean());
            addMechanicsItemOld(new AdBean());
        }

        notifyDataSetChanged();
    }

    @Override
    public AbsRecyclerViewHolder<MechanicsItem> onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case MechanicsItem.TYPE_MECHANICS_NEW:
//                return new NewMechanicsViewHolder(mInflater.inflate(R.layout.item_mechanics_new, parent, false));
//            case MechanicsItem.TYPE_MECHANICS_OLD:
//                return new OldMechanicsViewHolder(mInflater.inflate(R.layout.item_mechanics_old, parent, false));
        }
        return null;
    }
}

