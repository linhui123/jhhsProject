package com.jhhscm.platform.fragment.financing;

import android.view.View;

import com.jhhscm.platform.R;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemFinancProgressBinding;
import com.jhhscm.platform.fragment.my.mechanics.FindGoodsOwnerBean;
import com.jhhscm.platform.fragment.repayment.ContractListBean;
import com.jhhscm.platform.fragment.sale.SaleItem;

public class FinancingProgressViewHolder extends AbsRecyclerViewHolder<LeaseSelBean.ResultBean> {

    private ItemFinancProgressBinding mBinding;
    private String type = "";

    public FinancingProgressViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemFinancProgressBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final LeaseSelBean.ResultBean item) {
        if (item != null) {
            mBinding.name.setText("业务申请号:" + item.getLease_code());
            mBinding.data.setText("申请时间:" + item.getAdd_time());
            mBinding.orderStaus.setText(item.getLease_state_str());
            if (item.getLease_state() == 0) {//审核中
                mBinding.orderStaus.setTextColor(itemView.getContext().getResources().getColor(R.color.a397));
            } else if (item.getLease_state() == 1) {//审核通过
                mBinding.orderStaus.setTextColor(itemView.getContext().getResources().getColor(R.color.a37B));
            } else {//否决
                mBinding.orderStaus.setTextColor(itemView.getContext().getResources().getColor(R.color.ff1a));
            }
            mBinding.tv1.setText(item.getItem_name());
            mBinding.tv2.setText(item.getBrand_name());
            mBinding.tv3.setText(item.getFix_p_17());
            mBinding.tv4.setText(item.getSec_name());
        }
    }
}



