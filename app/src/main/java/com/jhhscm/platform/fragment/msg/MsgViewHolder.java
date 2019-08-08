package com.jhhscm.platform.fragment.msg;

import android.view.View;

import com.jhhscm.platform.activity.LabourDetailActivity;
import com.jhhscm.platform.activity.MsgDetailActivity;
import com.jhhscm.platform.adater.AbsRecyclerViewHolder;
import com.jhhscm.platform.databinding.ItemLabourBinding;
import com.jhhscm.platform.databinding.ItemMsgBinding;
import com.jhhscm.platform.fragment.labour.FindLabourReleaseListBean;

public class MsgViewHolder extends AbsRecyclerViewHolder<GetPushListBean.DataBean> {

    private ItemMsgBinding mBinding;

    public MsgViewHolder(View itemView) {
        super(itemView);
        mBinding = ItemMsgBinding.bind(itemView);
    }

    @Override
    protected void onBindView(final GetPushListBean.DataBean item) {
        if (item != null) {
            mBinding.title.setText(item.getTitle());
            mBinding.content.setText(item.getContent());
            mBinding.data.setText(item.getAdd_time());
            mBinding.rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MsgDetailActivity.start(itemView.getContext(), item);
                }
            });
        }
    }
}
