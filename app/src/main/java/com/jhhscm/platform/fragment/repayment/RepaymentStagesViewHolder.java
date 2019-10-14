package com.jhhscm.platform.fragment.repayment;

import android.app.Activity;
import android.view.View;

import com.jhhscm.platform.activity.RepaymentDetailActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemRepayStagesBinding;
import com.jhhscm.platform.databinding.ItemRepaymentBinding;
import com.jhhscm.platform.fragment.lessee.LesseeBean;
import com.jhhscm.platform.tool.DataUtil;
import com.jhhscm.platform.views.dialog.PayDialog;

public class RepaymentStagesViewHolder extends AbsRecyclerViewHolder<ContractDetailBean.MoneyPlanBean> {

    private ItemRepayStagesBinding mBinding;
    private int count;
    private Activity activity;

    public RepaymentStagesViewHolder(View itemView, Activity activity, int count) {
        super(itemView);
        this.count = count;
        this.activity = activity;
        mBinding = ItemRepayStagesBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final ContractDetailBean.MoneyPlanBean item) {
        if (item != null) {
            mBinding.tvAccount.setText(item.getRentMoney() + "");
            mBinding.tvStages.setText(item.getNum() + "/" + count + "期 还款日");
            mBinding.tvData.setText(item.getRepayTime().substring(0, 10));
            mBinding.tvBenjin.setText(item.getPrincipal() + "");
            mBinding.tvLixi.setText(item.getInterest() + "");
            mBinding.tvFaxi.setText(item.getDefaultInterest() + "");
            if (DataUtil.TimeCompare(DataUtil.getCurDate("yyyy-MM-dd"),
                    item.getRepayTime(), "yyyy-MM-dd")) {//还款日期大于当前日期
                String days = DataUtil.getLongToDays(DataUtil.getLongTime(DataUtil.getCurDate("yyyy-MM-dd"),
                        item.getRepayTime(), "yyyy-MM-dd"), "yyyy-MM-dd");
                mBinding.tvNum.setText(days + "天后还款");
                mBinding.tvRepay.setVisibility(View.GONE);
            } else {
                String days = DataUtil.getLongToDays(DataUtil.getLongTime(item.getRepayTime(),
                        DataUtil.getCurDate("yyyy-MM-dd"),
                        "yyyy-MM-dd"), "yyyy-MM-dd");
                mBinding.tvNum.setText("逾期" + days + "天");
                mBinding.tvRepay.setVisibility(View.VISIBLE);
            }

            mBinding.tvRepay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new PayDialog(itemView.getContext(), activity, item.getId()).show();
                }
            });
        }
    }
}
