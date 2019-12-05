package com.jhhscm.platform.fragment.repayment;

import android.view.View;

import com.jhhscm.platform.activity.RepaymentDetailActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemRepaymentBinding;

public class RepaymentViewHolder extends AbsRecyclerViewHolder<ContractListBean.DataBean> {

    private ItemRepaymentBinding mBinding;

    public RepaymentViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemRepaymentBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final ContractListBean.DataBean item) {
        if (item != null) {
            mBinding.code.setText("合同号：" + item.getCode());
            mBinding.tv1.setText(item.getSchemeName());
            mBinding.tv2.setText(item.getLoanMoney() + "");
            //0是等额本息 1是等额本金 2是平息贷
            if ("0".equals(item.getModeRepay())) {
                mBinding.tv3.setText("等额本息");
            } else if ("1".equals(item.getModeRepay())) {
                mBinding.tv3.setText("等额本金");
            } else {
                mBinding.tv3.setText("平息贷");
            }

        }


        mBinding.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RepaymentDetailActivity.start(itemView.getContext(), item.getCode());
            }
        });
    }
}


