package com.jhhscm.platform.fragment.labour;

import android.view.View;

import com.jhhscm.platform.activity.LabourDetailActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemLabourBinding;

public class LabourViewHolder extends AbsRecyclerViewHolder<FindLabourReleaseListBean.DataBean> {

    private ItemLabourBinding mBinding;

    public LabourViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemLabourBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final FindLabourReleaseListBean.DataBean item) {
        if (item.getType().equals("0")) {
            mBinding.tvType.setText("招聘");
        } else {
            mBinding.tvType.setText("求职");
        }
        mBinding.tvName.setText(item.getName());
        mBinding.tvLocation.setText(item.getProvince() + " " + item.getCity());
        mBinding.tvJingyan.setText(item.getWork_time());
        mBinding.tvXinzi.setText(item.getSalay_money());
        mBinding.tvGangwei.setText(item.getJob());
        mBinding.tvData.setText(item.getAdd_time());

        mBinding.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LabourDetailActivity.start(itemView.getContext(), 0, item);
            }
        });
    }
}

