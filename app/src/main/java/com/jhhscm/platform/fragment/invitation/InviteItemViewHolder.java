package com.jhhscm.platform.fragment.invitation;

import android.view.View;

import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemInviteBinding;

public class InviteItemViewHolder extends AbsRecyclerViewHolder<ReqListBean.ResultBean.DataBean> {

    private ItemInviteBinding mBinding;

    public InviteItemViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemInviteBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final ReqListBean.ResultBean.DataBean item) {
        if (item != null) {
            mBinding.name.setText(item.getNickname());
            mBinding.phone.setText(item.getMobile());
            mBinding.time.setText(item.getAdd_time());
        }
    }
}