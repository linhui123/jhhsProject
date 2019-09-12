package com.jhhscm.platform.fragment.repayment;

import android.view.View;

import com.jhhscm.platform.activity.RepaymentDetailActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemRepayStagesBinding;
import com.jhhscm.platform.databinding.ItemRepaymentBinding;
import com.jhhscm.platform.fragment.lessee.LesseeBean;

public class RepaymentStagesViewHolder  extends AbsRecyclerViewHolder<LesseeBean.WBankLeaseItemsBean> {

    private ItemRepayStagesBinding mBinding;

    public RepaymentStagesViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemRepayStagesBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final LesseeBean.WBankLeaseItemsBean item) {

    }
}
