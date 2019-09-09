package com.jhhscm.platform.fragment.lessee;

import android.view.View;

import com.jhhscm.platform.activity.LabourDetailActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemLabourBinding;
import com.jhhscm.platform.databinding.ItemLesseeMechanicsBinding;
import com.jhhscm.platform.fragment.labour.FindLabourReleaseListBean;

public class LesseeViewHolder extends AbsRecyclerViewHolder<FindLabourReleaseListBean.DataBean> {

    private ItemLesseeMechanicsBinding mBinding;

    public LesseeViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemLesseeMechanicsBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final FindLabourReleaseListBean.DataBean item) {

    }
}

