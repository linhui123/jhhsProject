package com.jhhscm.platform.fragment.repayment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.jhhscm.platform.activity.RepaymentDetailActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemRepaymentBinding;
import com.jhhscm.platform.fragment.lessee.LesseeBean;

public class RepaymentViewHolder extends AbsRecyclerViewHolder<LesseeBean.WBankLeaseItemsBean> {

    private ItemRepaymentBinding mBinding;

    public RepaymentViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemRepaymentBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final LesseeBean.WBankLeaseItemsBean item) {
        mBinding.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RepaymentDetailActivity.start(itemView.getContext());
            }
        });
    }
}


