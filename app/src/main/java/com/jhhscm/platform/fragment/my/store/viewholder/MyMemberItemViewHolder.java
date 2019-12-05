package com.jhhscm.platform.fragment.my.store.viewholder;

import android.view.View;

import com.jhhscm.platform.activity.ServiceRecordActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemStoreMemberBinding;
import com.jhhscm.platform.fragment.invitation.ReqListBean;

public class MyMemberItemViewHolder extends AbsRecyclerViewHolder<ReqListBean.ResultBean.DataBean> {

    private ItemStoreMemberBinding mBinding;

    public MyMemberItemViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemStoreMemberBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final ReqListBean.ResultBean.DataBean item) {
        if (item != null) {
            mBinding.name.setText(item.getNickname());
            mBinding.phone.setText(item.getMobile());
            mBinding.llMember.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ServiceRecordActivity.start(itemView.getContext(), item.getBus_code(), item.getUser_code()
                            , item.getNickname(), item.getMobile());
                }
            });
        }

    }
}
